package com.yatang.sc.purchase.dto;

import java.io.Serializable;

/**
 * Created by liusongjie on 2017/8/7.
 */
public class OrderSubmitDto implements Serializable {
    private static final long serialVersionUID = -4867324052289462907L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 购物车状态
     */
    private Short state;

    /**
     * 订单类型
     */
    private String orderState;

    /**
     * 配送状态
     */
    private String shippingState;

    /**
     * 支付状态
     */
    private String paymentState;

    /**
     * 订单实际最终总额
     */
    private double total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
