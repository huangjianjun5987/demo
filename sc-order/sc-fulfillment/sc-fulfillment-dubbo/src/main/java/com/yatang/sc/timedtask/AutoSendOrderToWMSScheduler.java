package com.yatang.sc.timedtask;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusongjie on 2017/7/27.
 *
 * 由“订单库存处理服务”定时检查订单状态为“已审核”，库存状态为“未传送”，付款状态为“已付款”或“公司内”的销售订单，
 * 向 WMS /供应商发送出库通知，数量依照订单行中“待出库数量”执行；同时变更订单物流状态为“待出库”；
 * (前提与WMS系统完成出库通知接口对接，如果对接未完成，则由人工在界面点击“已传送到物流”的按钮，完成相关动作)；
 */
@Component("autoSendOrderToWMSScheduler")
public class AutoSendOrderToWMSScheduler implements Scheduler{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    boolean enable = false;

    public void execute() {
        if(!enable){
            return;
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderState", OrderStates.APPROVED);
        condition.put("shippingState", ShippingStates.PENDING_TRANSFER);
        condition.put("interactivePendingRes", new Integer(0));
        log.info("depend on order state as " + ShippingStates.NO_ARRIVED + " to get order");
        List<Order> noArrivedOrders = orderService.getOrdersByState(condition);
        log.info("On " + ShippingStates.NO_ARRIVED + " state, order quantities are " + noArrivedOrders.size());

        if(CollectionUtils.isEmpty(noArrivedOrders)){
            return;
        }

        /*
        *   向 WMS /供应商发送出库通知，数量依照订单行中“待出库数量”执行
        * */
        for(Order order : noArrivedOrders){
            this.sendMessageToWMS(order);
        }

    }

    private void sendMessageToWMS(Order order){
        if(PaymentStates.DEBITED.equals(order.getPaymentState())
                || PaymentStates.GSN.equals(order.getPaymentState())) {
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.SendWMSDeliveryMessageCheck);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
            log.info("send to WMS, order:{}", JSON.toJSONString(order));
        }
    }
}
