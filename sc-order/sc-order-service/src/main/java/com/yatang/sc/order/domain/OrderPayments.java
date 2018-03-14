package com.yatang.sc.order.domain;

import java.io.Serializable;

public class OrderPayments implements Serializable {
    private Long id;

    private String orderId;

    private String paymentGroupId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPaymentGroupId() {
        return paymentGroupId;
    }

    public void setPaymentGroupId(String paymentGroupId) {
        this.paymentGroupId = paymentGroupId == null ? null : paymentGroupId.trim();
    }
}