package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 9:22
 * @版本: v1.0
 */
public class OrderReturnRequestDto implements Serializable {


    private static final long serialVersionUID = -1363431087529381741L;
    private String orderId;//当前订单附属的主订单id

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
