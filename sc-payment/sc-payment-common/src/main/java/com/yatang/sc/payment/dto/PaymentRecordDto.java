package com.yatang.sc.payment.dto;

import com.yatang.sc.payment.enums.PayStatus;

import java.io.Serializable;
import java.util.Date;

public class PaymentRecordDto implements Serializable {
    private String mPayNo;//支付流水
    private String mOrderNo;//订单号
    private Double mTotalFee;//订单金额（元）
    private PayStatus mPayStatus;//支付状态
    private String mRequestFrom;//请求来源
    private String mPayTypeCode;//支付类型
    private String mPayTypeName;//支付类型名称
    private String mPayWayCode;//支付方式
    private String mPayWayName;//支付方式名
    private String mPayTradeNo;//支付流水号
    private String mNonceStr;//32随机字符串
    private String mPayAccount;//支付账号
    private String mRemark;//备注
    private Date mPayTime;//支付时间

    public String getPayNo() {
        return mPayNo;
    }

    public void setPayNo(String mPayNo) {
        this.mPayNo = mPayNo;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String mOrderNo) {
        this.mOrderNo = mOrderNo;
    }

    public Double getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Double mTotalFee) {
        this.mTotalFee = mTotalFee;
    }

    public PayStatus getPayStatus() {
        return mPayStatus;
    }

    public void setPayStatus(PayStatus mPayStatus) {
        this.mPayStatus = mPayStatus;
    }

    public String getRequestFrom() {
        return mRequestFrom;
    }

    public void setRequestFrom(String mRequestFrom) {
        this.mRequestFrom = mRequestFrom;
    }

    public String getPayTypeCode() {
        return mPayTypeCode;
    }

    public void setPayTypeCode(String mPayTypeCode) {
        this.mPayTypeCode = mPayTypeCode;
    }

    public String getPayTypeName() {
        return mPayTypeName;
    }

    public void setPayTypeName(String mPayTypeName) {
        this.mPayTypeName = mPayTypeName;
    }

    public String getPayWayCode() {
        return mPayWayCode;
    }

    public void setPayWayCode(String mPayWayCode) {
        this.mPayWayCode = mPayWayCode;
    }

    public String getPayWayName() {
        return mPayWayName;
    }

    public void setPayWayName(String mPayWayName) {
        this.mPayWayName = mPayWayName;
    }

    public String getPayTradeNo() {
        return mPayTradeNo;
    }

    public void setPayTradeNo(String mPayTradeNo) {
        this.mPayTradeNo = mPayTradeNo;
    }

    public String getNonceStr() {
        return mNonceStr;
    }

    public void setNonceStr(String mNonceStr) {
        this.mNonceStr = mNonceStr;
    }

    public String getPayAccount() {
        return mPayAccount;
    }

    public void setPayAccount(String mPayAccount) {
        this.mPayAccount = mPayAccount;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String mRemark) {
        this.mRemark = mRemark;
    }

    public Date getPayTime() {
        return mPayTime;
    }

    public void setPayTime(Date mPayTime) {
        this.mPayTime = mPayTime;
    }
}
