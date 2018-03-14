package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.Date;

public class ConfirmPaymentDto implements Serializable {
    private static final long serialVersionUID = 1252575206309725284L;
    private String orderId;
    private String paymentId;
    private String operator;

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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
