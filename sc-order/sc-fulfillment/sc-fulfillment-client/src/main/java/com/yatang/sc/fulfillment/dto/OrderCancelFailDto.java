package com.yatang.sc.fulfillment.dto;

public class OrderCancelFailDto {
    private String orderId;

    private String message;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String pMessage) {
        message = pMessage;
    }
}
