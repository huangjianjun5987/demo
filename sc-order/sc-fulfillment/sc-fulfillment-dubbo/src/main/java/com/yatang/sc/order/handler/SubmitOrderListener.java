
package com.yatang.sc.order.handler;

import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.purchase.dubboservice.SplitOrderService;
import com.yatang.sc.service.ReserveOrderInventoryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qiugang on 7/25/2017.
 */


@Service("submitOrderListener")
public class SubmitOrderListener extends OrderMsgListener {

    protected Logger log = LoggerFactory.getLogger(SubmitOrderListener.class);
    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    private SplitOrderService mSplitOrderService;

    @Override
    public void handle(OrderMessage msg) {
        log.info("SubmitOrderHandler handle order:" + msg.getOrderId());
        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("can't find order by id: " + msg.getOrderId());
            return;
        }
        if (!OrderStates.PENDING_APPROVE.equals(order.getOrderState())) {
            log.info("{}-->订单状态不正确:{}", order.getId(), order.getOrderState());
            return;
        }

        mSplitOrderService.splitOrderByShippingModes(order.getId());
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.SubmitOrder.equals(msg.getMssageType());
    }
}

