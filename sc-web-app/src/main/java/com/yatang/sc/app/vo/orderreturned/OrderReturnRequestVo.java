package com.yatang.sc.app.vo.orderreturned;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 9:22
 * @版本: v1.0
 */
public class OrderReturnRequestVo implements Serializable {
    private static final long serialVersionUID = 6624295250712539473L;
    @NotBlank(message = "{msg.notEmpty.message}")
    private String orderId;//当前订单附属的主订单id

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
