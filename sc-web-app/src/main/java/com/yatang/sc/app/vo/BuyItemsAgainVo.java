package com.yatang.sc.app.vo;

import java.io.Serializable;

public class BuyItemsAgainVo implements Serializable{
    private static final long serialVersionUID = -7750605078443104891L;

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
