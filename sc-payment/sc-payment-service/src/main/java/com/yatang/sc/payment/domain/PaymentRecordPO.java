package com.yatang.sc.payment.domain;

import com.yatang.sc.payment.enums.PayStatus;

import java.util.Date;

/**
 * Created by yuwei on 2017/7/10.
 */
public class PaymentRecordPO extends BaseDomain {

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

    public void setPayNo(String pPayNo) {
        mPayNo = pPayNo;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String pOrderNo) {
        mOrderNo = pOrderNo;
    }

    public Double getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Double pTotalFee) {
        mTotalFee = pTotalFee;
    }

    public PayStatus getPayStatus() {
        return mPayStatus;
    }

    public void setPayStatus(PayStatus pPayStatus) {
        mPayStatus = pPayStatus;
    }

    public String getRequestFrom() {
        return mRequestFrom;
    }

    public void setRequestFrom(String pRequestFrom) {
        mRequestFrom = pRequestFrom;
    }

    public String getPayTypeCode() {
        return mPayTypeCode;
    }

    public void setPayTypeCode(String pPayTypeCode) {
        mPayTypeCode = pPayTypeCode;
    }

    public String getPayTypeName() {
        return mPayTypeName;
    }

    public void setPayTypeName(String pPayTypeName) {
        mPayTypeName = pPayTypeName;
    }

    public String getPayWayCode() {
        return mPayWayCode;
    }

    public void setPayWayCode(String pPayWayCode) {
        mPayWayCode = pPayWayCode;
    }

    public String getPayWayName() {
        return mPayWayName;
    }

    public void setPayWayName(String pPayWayName) {
        mPayWayName = pPayWayName;
    }

    public String getPayTradeNo() {
        return mPayTradeNo;
    }

    public void setPayTradeNo(String pPayTradeNo) {
        mPayTradeNo = pPayTradeNo;
    }

    public String getNonceStr() {
        return mNonceStr;
    }

    public void setNonceStr(String pNonceStr) {
        mNonceStr = pNonceStr;
    }

    public String getPayAccount() {
        return mPayAccount;
    }

    public void setPayAccount(String pPayAccount) {
        mPayAccount = pPayAccount;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String pRemark) {
        mRemark = pRemark;
    }

    public Date getPayTime() {
        return mPayTime;
    }

    public void setPayTime(Date pPayTime) {
        mPayTime = pPayTime;
    }
}
