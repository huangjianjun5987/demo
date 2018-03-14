package com.yatang.sc.dto;

import java.io.Serializable;
import java.util.Date;

public class PaymentResultDto implements Serializable {
    //订单号
    private String orderId;
    //支付渠道
    private String payChannel;
    //交易流水
    private String transNum;
    //支付金额
    private double payAmount;
    //支付时间
    private Date payDate;
    //内部支付流水号
    private String payRecordNo;
    private String operator;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String pPayChannel) {
        payChannel = pPayChannel;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String pTransNum) {
        transNum = pTransNum;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double pPayAmount) {
        payAmount = pPayAmount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date pPayDate) {
        payDate = pPayDate;
    }

    public String getPayRecordNo() {
        return payRecordNo;
    }

    public void setPayRecordNo(String pPayRecordNo) {
        payRecordNo = pPayRecordNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
