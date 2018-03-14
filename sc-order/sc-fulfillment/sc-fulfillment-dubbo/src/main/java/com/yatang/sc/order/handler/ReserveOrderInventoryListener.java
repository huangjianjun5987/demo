package com.yatang.sc.order.handler;

import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.service.ReserveOrderInventoryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reserveOrderInventoryListener")
public class ReserveOrderInventoryListener extends OrderMsgListener {

    protected Logger log = LoggerFactory.getLogger(ReserveOrderInventoryListener.class);

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

    @Autowired
    ShippingGroupService mShippingGroupService;

    @Autowired
    private ReserveOrderInventoryHelper mReserveOrderInventoryHelper;

    @Override
    public void handle(OrderMessage msg) throws MqBizFailureException {
        log.info("handle msg order id: " + msg.getOrderId());
        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("can't find order by id: " + msg.getOrderId());
            return;
        }
        ShippingGroup shippingGroup = mShippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        if (shippingGroup == null) {
            log.error("{}:订单ShippingGroup 为空:{}", order.getId(), order.getShippingGroup());
            return;
        }
        if(ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
            judgeOrderStateAndRoute(order);
            return;
        }
        mReserveOrderInventoryHelper.reserve(order, OrderMessageType.Reserve_Order_Inventory_SPLIT.equals(msg.getMssageType()),msg.getMssageType().getType());
    }

    protected void judgeOrderStateAndRoute(Order order){
        log.info("直送订单不预留库存，发生自动审核消息:{}", order.getId(), order.getOrderState());
        if (OrderStates.PENDING_APPROVE.equals(order.getOrderState())) {
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.AutoApproveOrder);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
        }
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.Reserve_Order_Inventory.equals(msg.getMssageType()) || OrderMessageType.Reserve_Order_Inventory_SPLIT.equals(msg.getMssageType());
    }
}
