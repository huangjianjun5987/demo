package com.yatang.sc.dto;

import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;
import com.yatang.sc.order.states.*;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/8/31.
 */
@ExcelName(name="JMSFK")
public class FranchiseePaymentDto implements Serializable{

    private static final long serialVersionUID = -9158153890868804906L;
    /**
     * 订单编号
     */
    @ExcelFieldName(name="订单编号")
    private String id;
    /**
     * 订单类型
     */
    private String orderType;

    @ExcelFieldName(name="订单类型")
    private String orderTypeDesc;

    private String orderState;
    @ExcelFieldName(name="订单状态")
    private String orderStateDesc;

    /**
     * 支付状态
     */
    private String shippingState;
    @ExcelFieldName(name="物流状态")
    private String shippingStateDesc;
    /**
     * 物流状态
     */
    private String paymentState;
    @ExcelFieldName(name="支付状态")
    private String paymentStateDesc;
    /**
     * 子公司
     */
    private String branchCompanyId;
    @ExcelFieldName(name="子公司")
    private String branchCompanyName;
    /**
     * 加盟商编号
     */
    @ExcelFieldName(name="加盟商编号")
    private String franchiseeId;
    @ExcelFieldName(name="加盟商名称")
    private String franchiseeName;
    /**
     * 出货仓
     */
    @ExcelFieldName(name="出货仓")
    private String branchCompanyArehouseCode;
    @ExcelFieldName(name="出货仓名称")
    private String branchCompanyArehouseName;
    /**
     * 付款时间
     */
    @ExcelFieldName(name="付款时间")
    private String payDate;
    /**
     * 付款金额
     */
    @ExcelFieldName(name="付款金额")
    private String amount;
    /**
     * 付款方式
     */
    private String channel;
    @ExcelFieldName(name="付款方式")
    private String channelDesc;
    private String paymentMethod;
    private String paymentMethodDesc;

    /**
     * 交易流水号
     */
    @ExcelFieldName(name="交易流水号")
    private String transNum;
    @ExcelFieldName(name="付款备注")
    private String comment;
    /**
     * 退款日期
     */
    @ExcelFieldName(name="退款日期")
    private String refundDate;
    /**
     * 退款金额
     */
    @ExcelFieldName(name="退款金额")
    private String refundAmount;
    /**
     * 退款方式
     */
    private String refundMethod;
    @ExcelFieldName(name="退款方式")
    private String refundMethodDesc;
    /**
     * 退款流水号
     */
    @ExcelFieldName(name="退款流水号")
    private String refundTradeNo;

    @ExcelFieldName(name="退款备注")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
    }

    public String getRefundMethodDesc() {

        return refundMethodDesc;
    }


    public String getOrderStateDesc() {
        return OrderStates.orderStateDesc.get(orderState);
    }

    public void setOrderStateDesc(String orderStateDesc) {
        this.orderStateDesc = orderStateDesc;
    }

    public void setRefundMethodDesc(String refundMethodDesc) {
        this.refundMethodDesc = refundMethodDesc;
    }



    public String getPaymentMethodDesc() {
        return PayChannel.payChannelDesc.get(paymentMethod);
    }

    public void setPaymentMethodDesc(String paymentMethodDesc) {
        this.paymentMethodDesc = paymentMethodDesc;
    }

    public String getOrderTypeDesc() {
        return OrderTypes.orderTypeDesc.get(orderType);
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
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

    public String getFranchiseeName() {
        return franchiseeName;
    }

    public void setFranchiseeName(String franchiseeName) {
        this.franchiseeName = franchiseeName;
    }

    public String getBranchCompanyArehouseCode() {
        return branchCompanyArehouseCode;
    }

    public void setBranchCompanyArehouseCode(String branchCompanyArehouseCode) {
        this.branchCompanyArehouseCode = branchCompanyArehouseCode;
    }

    public String getBranchCompanyArehouseName() {
        return branchCompanyArehouseName;
    }

    public void setBranchCompanyArehouseName(String branchCompanyArehouseName) {
        this.branchCompanyArehouseName = branchCompanyArehouseName;
    }

    public String getShippingStateDesc() {
        return ShippingStates.shippingStateDesc.get(shippingState);
    }

    public void setShippingStateDesc(String shippingStateDesc) {
        this.shippingStateDesc = shippingStateDesc;
    }

    public String getPaymentStateDesc() {
        return PaymentStates.paymentStateDesc.get(paymentState);
    }

    public void setPaymentStateDesc(String paymentStateDesc) {
        this.paymentStateDesc = paymentStateDesc;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
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
