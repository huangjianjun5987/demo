package com.yatang.sc.sorder.vo;

import java.io.Serializable;

public class SplitOrderByInventoryVo implements Serializable {

    private String orderId;  //订单编号

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
