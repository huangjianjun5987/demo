package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;

/**
 * Created by qiugang on 7/27/2017.
 */
public class OrderTransferBackDto implements Serializable {

    private String orderId;

    public boolean success;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
