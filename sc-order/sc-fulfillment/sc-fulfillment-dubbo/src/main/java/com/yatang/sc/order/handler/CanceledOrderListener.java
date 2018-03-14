package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.fulfillment.dto.OrderCancelConfirmDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelOrdersDto;
import com.yatang.sc.kidd.service.KiddOrderCancelService;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.CancelOrderStates;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import com.yatang.sc.vo.UpdateOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 取消订单mq中间层服务
 */
@Service("canceledOrderListener")
public class CanceledOrderListener extends OrderMsgListener {
    protected Logger log = LoggerFactory.getLogger(CanceledOrderListener.class);

    @Autowired
    ItemLocInventoryDubboService itemLocInventoryDubboService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemsService orderItemsService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    KiddOrderCancelService kiddOrderCancelService;

    @Autowired
    OrderInventoryHelper orderInventoryHelper;

    @Autowired
    WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Autowired
    private OrderFulfillerDubboService mOrderFulfillerDubboService;

    @Override
    public void handle(OrderMessage msg) {
        log.info("handle msg order id: " + msg.getOrderId());
        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("can't find order by id: " + msg.getOrderId());
            return;
        }
        if (order.getOrderState().equals(OrderStates.CANCELLED)) {
            log.warn("{}订单状态【{}】不正确，取消事件处理失败", msg.getOrderId(), order.getOrderState());
            return;
        }
        if (OrderStates.CANCELLED.equalsIgnoreCase(order.getOrderState())) {
            log.info("订单已被取消，不再拆单:{}", order.getOrderState());
            return;
        }
        if (order.getInteractivePendingRes()) {
            log.warn("{}订单状态【{}】,等待GLink处理结果通知", msg.getOrderId(), order.getOrderState());
            throw new RuntimeException("等待GLink处理结果通知，暂不允许取消操作.");
        }
        /**
         * 1. 如果物流状态为“待出库”，则向WMS发送取消出库通知
         * 2. 订单未送到 取消时，向WMS发送取消出库通知
         */
        if (ShippingStates.PENDING_DELIVERY.equals(order.getShippingState())
            //|| ShippingStates.NO_ARRIVED.equals(order.getShippingState())//TODO 暂不对“未送达”订单调用WMS接口取消订单
                ) {
            // 调用Kidd中间件服务取消订单

            KiddCancelAllOrdersDto kiddCancelAllOrdersDto = new KiddCancelAllOrdersDto();

            List<KiddCancelOrdersDto> cancelOrdersDtos = new ArrayList<KiddCancelOrdersDto>();
            KiddCancelOrdersDto kiddCancelOrdersDto = new KiddCancelOrdersDto();
            kiddCancelOrdersDto.setOrderCode(order.getId());//订单编号
            kiddCancelOrdersDto.setWarehouseCode(order.getBranchCompanyArehouse());
            cancelOrdersDtos.add(kiddCancelOrdersDto);
            kiddCancelAllOrdersDto.setOrders(cancelOrdersDtos);
            kiddCancelAllOrdersDto.setOrderType(KiddOrderLogisticsType.KIDD_XSCK);//发货出库 自定义类型 kidd销售出库
            Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(order.getBranchCompanyArehouse());
            if (CommonsEnum.RESPONSE_200.getCode().equalsIgnoreCase(logicWarehouseDtoResponse.getCode())) {
                kiddCancelOrdersDto.setOrgCode(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode());
                // cancelOrders.setOrgCode(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode());
            } else {
                log.warn("出货仓dubbo服务调用失败:{}", order.getBranchCompanyArehouse());
                throw new RuntimeException("出货仓dubbo服务调用失败。");
            }

            Response<String> response = kiddOrderCancelService.cancel(kiddCancelAllOrdersDto);//取消订单发送信息
            log.info("处理订单取消操作返回结果:{}", JSON.toJSONString(response));
            if (CommonsEnum.RESPONSE_20051.getCode().equals(response.getCode())) {//调用取消操作出错
                log.error("send cancel order message  failed:{}", JSON.toJSONString(response));
                order.setDescription("取消申请接口调用失败:" + subErrorMsg(response.getErrorMessage()));
                order.setCancelStatus(CancelOrderStates.QXSB.getStateValue());
                UpdateOrderVO updateOrderVO = new UpdateOrderVO();
                updateOrderVO.setOrder(order);
                updateOrderVO.setPreOrderState(order.getState());
                updateOrderVO.setDescription("取消申请接口调用失败:" + subErrorMsg(response.getErrorMessage()));
                updateOrderVO.setOperator("系统");
                orderService.updateAndSaveLog(updateOrderVO);
                return;
            } else if (CommonsEnum.RESPONSE_500.getCode().equals(response.getCode())) {
                log.error("取消申请接口调用失败", JSON.toJSONString(response));
                return;
            }
        } else {
            //订单还停留在我们系统中的处理
            OrderCancelConfirmDto orderCancelConfirmDto = new OrderCancelConfirmDto();
            orderCancelConfirmDto.setOrderId(order.getId());
            Response<Boolean> response = mOrderFulfillerDubboService.orderCancelConfirm(orderCancelConfirmDto);
            log.info("订单取消接口调用返回结果:{}", JSON.toJSONString(response));
            if (response != null && !response.isSuccess()) {
                throw new MqBizFailureException(response.getErrorMessage());
            }
        }
    }

    private String subErrorMsg(String errorMsg) {
        if (StringUtils.isEmpty(errorMsg)) {
            return "";
        }
        if (errorMsg.length() < 160) {
            return errorMsg;
        }
        return errorMsg.substring(0, 160);
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.CANCELED_ORDER.equals(msg.getMssageType());
    }
}
