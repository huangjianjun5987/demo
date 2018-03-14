package com.yatang.sc.order.handler;

import com.yatang.sc.order.msg.OrderMessage;
import org.springframework.stereotype.Service;

@Service("refundCompletedOrderListener")
public class RefundCompletedOrderListener extends OrderMsgListener {
    @Override
    public void handle(OrderMessage msg) {

    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return false;
    }
}
