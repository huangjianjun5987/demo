package com.yatang.sc.order.handler;

import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.order.msg.OrderMessage;

/**
 * Created by qiugang on 7/25/2017.
 */
public abstract class OrderMsgListener {

    public abstract void handle(OrderMessage msg) throws Exception;

    /**
     * 判断消息是否是自己关心并需要处理的
     * @param msg
     * @return
     */
    public abstract boolean isCare(OrderMessage msg);
}
