package com.yatang.sc.order;

import com.busi.common.resp.Response;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.dto.CancelOrderRequestDto;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.dubboservice.CancelOrderDubboService;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.CancelOrderStates;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.vo.UpdateOrderVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/13.
 * 取消订单接口
 */
@Service("cancelOrderDubboService")
public class CancelOrderDubboServiceImpl implements CancelOrderDubboService {
    private Logger mLogger = LoggerFactory.getLogger(CancelOrderDubboServiceImpl.class.getName());
    @Autowired
    private OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response<String> cancelOrder(CancelOrderRequestDto pCancelOrderRequestDto) {
        mLogger.info("cancelOrder request param:{}", pCancelOrderRequestDto);
        Response<String> result = new Response<String>();
        try {
            Order order = orderService.selectByPrimaryKey(pCancelOrderRequestDto.getOrderId());
            ShippingGroup shippingGroup=shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
            //供应商直送订单的取消(目前直送单只能取消未支付的单子) yinyuxin
            if (ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
                if (!PaymentStates.PENDING_PAYMENT.equals(order.getPaymentState())){
                    throw new RuntimeException("供应单直送订单，只允许在未支付状态下取消");
                }
                //todo 调用采购订单取消接口
            }


            if (order == null) {
                result.setSuccess(false);
                result.setErrorMessage("订单不存在:" + pCancelOrderRequestDto.getOrderId());
                result.setCode(CommonsEnum.RESPONSE_10006.getCode());
                return result;
            }

            if (CancelOrderStates.QXZ.getStateValue().equalsIgnoreCase(order.getCancelStatus())) {
                mLogger.info("订单取消中:{}", order.getId());
                result.setSuccess(false);
                result.setErrorMessage("订单已发送取消申请，请勿再次申请取消");
                return result;
            }
            if(CancelOrderStates.QXSB.getStateValue().equals(order.getCancelStatus())){
                mLogger.info("订单的订单，不再取消:{}", order.getId());
                result.setSuccess(false);
                result.setErrorMessage("订单已操作取消，并且取消失败，不能再取消");
                return result;
            }
            if (!canCancelOrder(order)) {
                result.setSuccess(false);
                result.setCode(CommonsEnum.RESPONSE_10022.getCode());
                result.setErrorMessage("当前订单状态不允许取消。");
                return result;
            }

            order.setCancelReason(pCancelOrderRequestDto.getRemark());
            order.setCancelStatus(CancelOrderStates.QXZ.getStateValue());
            order.setDescription(CancelOrderStates.QXZ.getDescription());
            UpdateOrderVO updateOrderVO = new UpdateOrderVO();
            updateOrderVO.setOrder(order);
            updateOrderVO.setPreOrderState(order.getState());
            updateOrderVO.setDescription("取消订单申请中，id：" + order.getId());
            updateOrderVO.setOperator(pCancelOrderRequestDto.getOperatorName());
            orderService.updateAndSaveLog(updateOrderVO);
            mLogger.info("取消订单申请，id：{}", order.getId());

            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.CANCELED_ORDER);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
//            orderMessageSender.sendOrderUpdateMsg(wmsMessage);//发送更新索引消息
            mLogger.info("{}订单取消事件已发送MQ", order.getId());
            result.setSuccess(true);
            result.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            result.setCode(CommonsEnum.RESPONSE_200.getCode());
            return result;

        } catch (Exception pE) {
            mLogger.error("取消订单申请异常", pE);
            result.setCode(CommonsEnum.RESPONSE_500.getCode());
            result.setSuccess(false);
            result.setErrorMessage("取消订单申请异常。");
            orderLogService.saveOrderLog(pCancelOrderRequestDto.getOrderId(), null, "取消订单申请异常!", pCancelOrderRequestDto.getOperatorName());
            return result;
        }
    }

    /**
     * 物流状态为“待处理”、“未传送”、“待出库”、“未送达”、“取消送货”和“采购未到货”状态时
     * 订单状态是已拆单的情况下  不允许取消订单
     * @param pOrder
     * @return
     */
    private boolean canCancelOrder(Order pOrder) {
        mLogger.info("订单{}状态为:{}", pOrder.getId(), pOrder.getState());
        if (OrderStates.CANCELLED.equalsIgnoreCase(pOrder.getOrderState())
                || OrderStates.COMPLETED.equalsIgnoreCase(pOrder.getOrderState())|| OrderStates.SPLITED_ORDER.equalsIgnoreCase(pOrder.getOrderState())) {
            return false;
        }
        mLogger.info("订单:{} 物流状态：{}", pOrder.getId(), pOrder.getShippingState());
        if (ShippingStates.PENDING_RECEIVE.equals(pOrder.getShippingState())
                || ShippingStates.COMPLETED.equals(pOrder.getShippingState())||ShippingStates.USER_REJECT.equals(pOrder.getShippingState())) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response<Map<String, String>> batchCancelOrder(List<CancelOrderRequestDto> pCancelOrderRequestDtos) {
        Response batchResponse = new Response();
        if (CollectionUtils.isEmpty(pCancelOrderRequestDtos)) {
            batchResponse.setCode(CommonsEnum.RESPONSE_400.getCode());
            batchResponse.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            batchResponse.setSuccess(false);
            return batchResponse;
        }
        Map<String, String> cancelResult = new HashMap<String, String>();
        Response<String> response;
        for (CancelOrderRequestDto cancelOrderRequestDto : pCancelOrderRequestDtos) {
            response = cancelOrder(cancelOrderRequestDto);
            if (!response.isSuccess()) {
                cancelResult.put(cancelOrderRequestDto.getOrderId(), response.getErrorMessage());
            }
        }
        batchResponse.setSuccess(true);
        batchResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        batchResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        batchResponse.setResultObject(cancelResult);
        return batchResponse;
    }

}
