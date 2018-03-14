package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.ProviderShippingGroup;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.ProviderShippingGroupService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.order.states.ShippingStates;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiugang on 7/26/2017.
 */
@Service("approvedOrderListener")
public class ApprovedOrderListener extends OrderMsgListener {
    protected Logger log = LoggerFactory.getLogger(ApprovedOrderListener.class);

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
    ShippingGroupService mShippingGroupService;

    @Override
    public void handle(OrderMessage msg) throws Exception {

        log.info("handle msg order id: " + msg.getOrderId());
        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("can't find order by id: " + msg.getOrderId());
            return;
        }

        if (!OrderStates.APPROVED.equals(order.getOrderState())) {
            log.info("{}订单状态不正确：{},{}", order.getId(), order.getOrderState(), order.getShippingState());
            return;
        }

        ShippingGroup shippingGroup = mShippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        if (OrderTypes.ELECTRONIC_ORDER.equals(order.getOrderType())) {
            log.info("订单:{} 是虚拟订单", order.getId());
            handleElectronicOrder(order);
        } else if (ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())
                && OrderStates.APPROVED.equals(order.getOrderState())
                && (order.getPaymentState().equals(PaymentStates.DEBITED) || order.getPaymentState().equals(PaymentStates.GSN))) {
            log.info("直配订单:{}", order.getId());

            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.SendWMSDeliveryMessageCheck);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);

        } else if (ShippingStates.PENDING_TRANSFER.equals(order.getShippingState())
                && (order.getPaymentState().equals(PaymentStates.DEBITED) || order.getPaymentState().equals(PaymentStates.GSN))) {
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.SendWMSDeliveryMessageCheck);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
        }
    }

    public void handleElectronicOrder(Order order) {

        if (order.getPaymentState().equals(PaymentStates.DEBITED)
                || order.getPaymentState().equals(PaymentStates.GSN)) {
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.DeliveryElectronicOrder);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
        }
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.ApprovedOrder.equals(msg.getMssageType()) || OrderMessageType.ApprovedOrder_Manual.equals(msg.getMssageType());
    }
}
