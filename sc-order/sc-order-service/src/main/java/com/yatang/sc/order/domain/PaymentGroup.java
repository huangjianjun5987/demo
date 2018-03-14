package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

public class PaymentGroup implements Serializable {
    private String id;

    private String orderId;

    private Double amount;

    private String currencyCode;

    private Double amountAuthorized;

    private Double amountCredited;

    private Double amountDebited;

    private String creditStatus;

    private String debitStatus;

    private String paymentMethod;

    private String transNum;

    private String payRecordNo;

    private String specialInstructions;

    private Short state;

    private String stateDetail;

    private String channel;

    private Date payDate;

    private String comment;
    private String lastOperator;

    private String payAccount;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public Double getAmountAuthorized() {
        return amountAuthorized;
    }

    public void setAmountAuthorized(Double amountAuthorized) {
        this.amountAuthorized = amountAuthorized;
    }

    public Double getAmountCredited() {
        return amountCredited;
    }

    public void setAmountCredited(Double amountCredited) {
        this.amountCredited = amountCredited;
    }

    public Double getAmountDebited() {
        return amountDebited;
    }

    public void setAmountDebited(Double amountDebited) {
        this.amountDebited = amountDebited;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus == null ? null : creditStatus.trim();
    }

    public String getDebitStatus() {
        return debitStatus;
    }

    public void setDebitStatus(String debitStatus) {
        this.debitStatus = debitStatus == null ? null : debitStatus.trim();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod == null ? null : paymentMethod.trim();
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum == null ? null : transNum.trim();
    }

    public String getPayRecordNo() {
        return payRecordNo;
    }

    public void setPayRecordNo(String pPayRecordNo) {
        payRecordNo = pPayRecordNo;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions == null ? null : specialInstructions.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail == null ? null : stateDetail.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }
}