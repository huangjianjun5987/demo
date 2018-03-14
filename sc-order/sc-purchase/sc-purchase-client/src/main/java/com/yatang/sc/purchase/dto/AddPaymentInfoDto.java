package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.Date;

public class AddPaymentInfoDto implements Serializable{
    private static final long serialVersionUID = 315518946051716969L;
    private String orderId;
    private Double amount;
    private String payAccount;
    private String tranNum;
    private String cashier;
    private String channel;
    private Date payDate;
    private String comment;
    private String operator;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getTranNum() {
        return tranNum;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setTranNum(String tranNum) {
        this.tranNum = tranNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
