package com.yatang.sc.timedtask;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderStates;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusongjie on 2017/7/28.
 *
 * 由“订单自动审核服务”定时检查所有订单状态 为“待审核”的销售订单，进行自动审核
 */
@Component("autoApproveScheduler")
public class AutoApproveScheduler implements Scheduler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Override
    public void execute() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderState", OrderStates.PENDING_APPROVE);

        log.info("depend on order state as " +  OrderStates.PENDING_APPROVE + " to get order");
        List<Order> pendingApproveOrder = orderService.getOrdersByState(condition);
        log.info("On " + OrderStates.PENDING_APPROVE + " state, order quantities are " + pendingApproveOrder.size());
        if(CollectionUtils.isEmpty(pendingApproveOrder)){
            return;
        }

        for(Order order : pendingApproveOrder){
            this.sendSubmitOrderMsg(order);
        }
    }

    private void sendSubmitOrderMsg(Order order) {
        OrderMessage wmsMessage = new OrderMessage();
        wmsMessage.setMssageType(OrderMessageType.AutoApproveOrder);
        wmsMessage.setOrderId(order.getId());
        orderMessageSender.sendMsg(wmsMessage);
        log.info("auto approve, order:{}", JSON.toJSONString(order));
    }
}
