package com.yatang.sc.sorder.vo;

import java.io.Serializable;

public class OrderApprovalVo implements Serializable {

    private static final long serialVersionUID = -32211775091867721L;

    private String orderId;

    private String userId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
