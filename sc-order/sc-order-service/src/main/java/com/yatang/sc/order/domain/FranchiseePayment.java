package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiangyonghong on 2017/8/31.
 */
public class FranchiseePayment implements Serializable{

    private static final long serialVersionUID = -4025316951652342474L;
    /**
     * 订单编号
     */
    private String id;
    /**
     * 订单类型
     */
    private String orderType;

    private String orderState;
    /**
     * 支付状态
     */
    private String shippingState;
    /**
     * 物流状态
     */
    private String paymentState;
    /**
     * 子公司
     */
    private String branchCompanyId;
    /**
     * 加盟商编号
     */
    private String franchiseeId;
    /**
     * 出货仓
     */
    private String branchCompanyArehouseCode;
    /**
     * 付款时间
     */
    private Date payDate;
    /**
     * 付款金额
     */
    private Double amount;
    /**
     * 付款方式
     */
    private String paymentMethod;
    /**
     * 交易流水号
     */
    private String transNum;
    /**
     * 退款日期
     */
    private Date refundDate;
    /**
     * 退款金额
     */
    private Double refundAmount;
    /**
     * 退款方式
     */
    private String refundMethod;
    /**
     * 退款流水号
     */
    private String refundTradeNo;

    private String remark;//退款备注

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getBranchCompanyArehouseCode() {
        return branchCompanyArehouseCode;
    }

    public void setBranchCompanyArehouseCode(String branchCompanyArehouseCode) {
        this.branchCompanyArehouseCode = branchCompanyArehouseCode;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundMethod() {
        return paymentMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod;
    }

    public String getRefundTradeNo() {
        return refundTradeNo;
    }

    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }
}
