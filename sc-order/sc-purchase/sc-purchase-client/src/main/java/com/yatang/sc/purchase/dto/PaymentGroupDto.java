package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class PaymentGroupDto implements Serializable{
    private static final long serialVersionUID = 6743470667161333502L;

    /**
     * 主键id
     */
    private String id;

    /**
     *
     */
    private String orderId;

    /**
     * 当前支付方式所需支付总额
     */
    private double amount;

    /**
     * 货币种类编码
     */
    private String currencyCode;

    /**
     * 当前支付方式所授权总额
     */
    private double amountAuthorized;

    /**
     * 当前支付方式(原路)返回的金额
     */
    private double amountCredited;

    /**
     * 当前支付方式已经扣款的总额
     */
    private double amountDebited;

    /**
     * 原路返回的状态履历
     */
    private String creditStatus;


    /**
     * 扣款履历
     */
    private String debitStatus;


    /**
     * 当前支付方式的java对象全路径
     */
    private String type;

    /**
     * 当前支付方式文字描述
     */
    private String paymentMethod;

    /**
     * 交易流水号
     */
    private String transNum;


    /**
     * 当前支付方式的特殊说明
     */
    private String specialInstructions;

    /**
     * 当前支付方式的状态
     */
    private Short state;

    /**
     * 状态细节
     */
    private String stateDetail;

    /**
     * 支付完成时间
     */
    private Date payDate;

    /**
     * 支付渠道
     */
    private String channel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getAmountAuthorized() {
        return amountAuthorized;
    }

    public void setAmountAuthorized(double amountAuthorized) {
        this.amountAuthorized = amountAuthorized;
    }

    public double getAmountCredited() {
        return amountCredited;
    }

    public void setAmountCredited(double amountCredited) {
        this.amountCredited = amountCredited;
    }

    public double getAmountDebited() {
        return amountDebited;
    }

    public void setAmountDebited(double amountDebited) {
        this.amountDebited = amountDebited;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getDebitStatus() {
        return debitStatus;
    }

    public void setDebitStatus(String debitStatus) {
        this.debitStatus = debitStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
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
        this.stateDetail = stateDetail;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
