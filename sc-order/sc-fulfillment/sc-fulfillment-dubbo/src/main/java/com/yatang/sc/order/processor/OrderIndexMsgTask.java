package com.yatang.sc.order.processor;

import com.yatang.sc.service.OrderIndexHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OrderIndexMsgTask implements Runnable{

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private OrderIndexHelper orderIndexHelper;
    private String orderId;

    public OrderIndexMsgTask(String orderId,OrderIndexHelper orderIndexHelper){
        this.orderIndexHelper = orderIndexHelper;
        this.orderId = orderId;
    }
    @Override
    public void run() {
        this.orderIndexHelper.sendOrderIndexMessage(orderId);
    }
}
