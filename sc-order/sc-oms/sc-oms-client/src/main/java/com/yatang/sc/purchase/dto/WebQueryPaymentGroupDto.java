package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.Date;

public class WebQueryPaymentGroupDto implements Serializable {
    private String id;
    /**
     * 1，付款,2，退款
     */
    private Integer type;

    private String orderId;

    private Double amount;

    private String currencyCode;

    private String transNum;

    private String payRecordNo;

    private String state;

    private String stateDetail;

    private String channel;

    private Date createTime;

    private String operatorName;

    private String remark;

    private Date operationTime;

    private String refundReason;

    private boolean shouldConfirm = false;

    private String payAccount;

    private String comment;

    private String lastOperator;

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer pType) {
        type = pType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double pAmount) {
        amount = pAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String pCurrencyCode) {
        currencyCode = pCurrencyCode;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String pTransNum) {
        transNum = pTransNum;
    }

    public String getPayRecordNo() {
        return payRecordNo;
    }

    public void setPayRecordNo(String pPayRecordNo) {
        payRecordNo = pPayRecordNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String pState) {
        state = pState;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String pStateDetail) {
        stateDetail = pStateDetail;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String pChannel) {
        channel = pChannel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date pCreateTime) {
        createTime = pCreateTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String pOperatorName) {
        operatorName = pOperatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String pRemark) {
        remark = pRemark;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date pOperationTime) {
        operationTime = pOperationTime;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String pRefundReason) {
        refundReason = pRefundReason;
    }

    public boolean isShouldConfirm() {
        return shouldConfirm;
    }

    public void setShouldConfirm(boolean shouldConfirm) {
        this.shouldConfirm = shouldConfirm;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    @Override
    public String toString() {
        return "WebQueryPaymentGroupDto{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", transNum='" + transNum + '\'' +
                ", payRecordNo='" + payRecordNo + '\'' +
                ", state='" + state + '\'' +
                ", stateDetail='" + stateDetail + '\'' +
                ", channel='" + channel + '\'' +
                ", createTime=" + createTime +
                ", operatorName='" + operatorName + '\'' +
                ", remark='" + remark + '\'' +
                ", operationTime=" + operationTime +
                ", refundReason='" + refundReason + '\'' +
                ", shouldConfirm=" + shouldConfirm +
                '}';
    }
}
