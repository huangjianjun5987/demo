package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;

public class OrderCancelConfirmDto implements Serializable {

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
