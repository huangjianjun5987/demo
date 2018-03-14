package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;

public class OrderSendCarDto implements Serializable {
    private String orderId;
    private String driverName;
    private String driverPhone;
    private String deliveryTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String pDriverName) {
        driverName = pDriverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String pDriverPhone) {
        driverPhone = pDriverPhone;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String pDeliveryTime) {
        deliveryTime = pDeliveryTime;
    }

    @Override
    public String toString() {
        return "OrderSendCarDto{" +
                "orderId='" + orderId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                '}';
    }
}
