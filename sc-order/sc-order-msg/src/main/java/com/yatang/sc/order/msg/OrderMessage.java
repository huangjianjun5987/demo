package com.yatang.sc.order.msg;

import java.io.Serializable;

/**
 * Created by qiugang on 7/24/2017.
 */

public class OrderMessage implements Serializable {

    private OrderMessageType mssageType;

    private String orderId;

    private String body;

    public OrderMessageType getMssageType() {
        return mssageType;
    }

    public void setMssageType(OrderMessageType mssageType) {
        this.mssageType = mssageType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String pBody) {
        body = pBody;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "mssageType=" + mssageType +
                ", orderId='" + orderId + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
