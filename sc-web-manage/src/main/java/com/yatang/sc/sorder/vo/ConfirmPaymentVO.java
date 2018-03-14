package com.yatang.sc.sorder.vo;

import com.esotericsoftware.kryo.NotNull;

import java.io.Serializable;

public class ConfirmPaymentVO implements Serializable {
    @NotNull
    private String orderId;
    @NotNull
    private String paymentId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
