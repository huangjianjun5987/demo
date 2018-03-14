package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;

/**
 * Created by qiugang on 7/27/2017.
 */
public class OrderTransferDto implements Serializable {


    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
