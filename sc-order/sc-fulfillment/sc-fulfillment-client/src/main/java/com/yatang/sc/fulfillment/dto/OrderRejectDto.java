package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;

/**
 * @描述: 拒签dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 16:41
 * @版本: v1.0
 */
public class OrderRejectDto implements Serializable {
    private static final long serialVersionUID = -633134231510753425L;


    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
