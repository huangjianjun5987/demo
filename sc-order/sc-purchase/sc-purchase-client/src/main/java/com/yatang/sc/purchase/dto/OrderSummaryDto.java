package com.yatang.sc.purchase.dto;

import com.yatang.sc.order.states.OrderTotalStates;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
public class OrderSummaryDto implements Serializable{
    private static final long serialVersionUID = -716068493118918737L;

    private String id;

    /**
     * 用户id
     */
    private String profileId;

    /**
     * 购物车状态
     */
    private short state;

    private String stateDesc;
    /**
     * 创建时间
     */
    private Date creationTime;

    private double totalPrice;

    private double post;

    private int productTotal;

    private String paymentState;   //订单的支付状态

    private String paymentStateDetail;  //订单支付状态描述

    private String orderType;
    private short returnState;// 订单退换货状态

    private String shippingModes;//配送模式

    public String getShippingModes() {
        return shippingModes;
    }

    public void setShippingModes(String shippingModes) {
        this.shippingModes = shippingModes;
    }

    private List<OrderProductListDto> productList;

    public short getReturnState() {
        return returnState;
    }

    public void setReturnState(short returnState) {
        this.returnState = returnState;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPost() {
        return post;
    }

    public void setPost(double post) {
        this.post = post;
    }

    public int getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(int productTotal) {
        this.productTotal = productTotal;
    }

    public List<OrderProductListDto> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProductListDto> productList) {
        this.productList = productList;
    }

    public String getStateDesc() {
        return OrderTotalStates.getStateByValue(state).getDescription();
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getPaymentStateDetail() {
        return paymentStateDetail;
    }

    public void setPaymentStateDetail(String paymentStateDetail) {
        this.paymentStateDetail = paymentStateDetail;
    }
}
