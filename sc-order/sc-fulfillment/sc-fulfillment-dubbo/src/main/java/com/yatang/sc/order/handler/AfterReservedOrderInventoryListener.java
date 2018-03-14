package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.inventory.dto.ReservedInventoryResult;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.vo.UpdateOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("afterReservedOrderInventoryListener")
public class AfterReservedOrderInventoryListener extends OrderMsgListener {

    protected Logger log = LoggerFactory.getLogger(AfterReservedOrderInventoryListener.class);
    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    OrderInventoryHelper orderInventoryHelper;

    @Autowired
    OrderItemsService orderItemsService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    OrderPriceService orderPriceService;

    @Override
    public void handle(OrderMessage msg) throws MqBizFailureException {
        log.info("handle msg order id: " + msg.getOrderId());
        String body = msg.getBody();
        if (StringUtils.isEmpty(body)) {
            return;
        }
        ReservedInventoryResult reservedInventoryResult = JSON.parseObject(body, ReservedInventoryResult.class);
        Order order = orderService.selectByPrimaryKey(reservedInventoryResult.getOrderId());
        if (order == null) {
            log.error("订单不存在:{}", reservedInventoryResult.getOrderId());
            return;
        }

        UpdateOrderVO updateOrderVO = new UpdateOrderVO();
        updateOrderVO.setOrder(order);
        updateOrderVO.setPreOrderState(order.getState());

        List<Long> itemIds = new ArrayList<Long>();
        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId(order.getId());
        for (OrderItems orderItem : orderItems) {
            itemIds.add(orderItem.getCommerceItemId());
        }
        if (OrderStates.PENDING_APPROVE.equals(order.getOrderState())) {
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.AutoApproveOrder);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
        }

        switch (reservedInventoryResult.getStaus()) {
            case RESERVED_ALL: {
                order.setShippingState(ShippingStates.PENDING_TRANSFER);
                order.setLastModifiedTime(new Date());
                updateOrderVO.setDescription("检查库存完成！");
                updateOrderVO.setOperator("系统");
                orderService.updateAndSaveLog(updateOrderVO);
                commerceItemService.updateState(itemIds, null);

                if (OrderStates.APPROVED.equals(order.getOrderState())
                        && (order.getPaymentState().equals(PaymentStates.DEBITED) || order.getPaymentState().equals(PaymentStates.GSN))) {
                    OrderMessage wmsMessage = new OrderMessage();
                    wmsMessage.setMssageType(OrderMessageType.SendWMSDeliveryMessageCheck);
                    wmsMessage.setOrderId(order.getId());
                    orderMessageSender.sendMsg(wmsMessage);
                }
                return;
            }
            case RESERVED_FAILURE: {
                order.setShippingState(ShippingStates.NO_INVENTORY);
                order.setLastModifiedTime(new Date());
                updateOrderVO.setDescription("检查库存不足！");
                updateOrderVO.setOperator("系统");
                orderService.updateAndSaveLog(updateOrderVO);
                commerceItemService.updateState(null, itemIds);
                return;
            }
            case RESERVED_PORTION_SUCCESS: {
                OrderMessage orderMessage = new OrderMessage();
                orderMessage.setOrderId(reservedInventoryResult.getOrderId());
                orderMessage.setMssageType(OrderMessageType.SPLIT_ORDER_BY_INVENTORY);
                orderMessage.setBody(reservedInventoryResult.getMsg());
                orderMessageSender.sendMsg(orderMessage);
                return;
            }
            case RESERVED_PORTION_FAIL: {
                OrderMessage orderMessage = new OrderMessage();
                orderMessage.setOrderId(reservedInventoryResult.getOrderId());
                orderMessage.setMssageType(OrderMessageType.SPLIT_ORDER_BY_INVENTORY_ONLY_UPDATE_STATE);
                orderMessage.setBody(reservedInventoryResult.getMsg());
                orderMessageSender.sendMsg(orderMessage);
                return;
            }
            default: {

            }

        }
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.After_Reserved_Order_Inventory.equals(msg.getMssageType());
    }
}
