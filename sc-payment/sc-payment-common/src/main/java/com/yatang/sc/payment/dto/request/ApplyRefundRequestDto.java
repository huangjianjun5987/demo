package com.yatang.sc.payment.dto.request;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundReason;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by yuwei on 2017/7/14.
 */
public class ApplyRefundRequestDto extends NoneStrRequestDto {
    @NotNull
    private PayType mPayType;
    @NotNull
    private String mOrderNo;
    @NotNull(message = "平台支付交易流水不能为空")
    private String mPayNo;
    @NotNull(message = "第三方支付平台支付交易流水不能为空")
    private String mPayTradeNo;
    @NotNull
    @Digits(integer = 10, fraction = 4)
    private Double mRefundAmount;
    @NotNull
    @Digits(integer = 10, fraction = 4)
    private Double mTotalAmount;
    @NotNull(message = "退款申请人ID不能为空")
    private String mRefundUserId;
    @NotNull(message = "退款申请人名不能为空")
    private String mRefundUserName;
    private String mRemark;
    @NotNull(message = "退款原因不能为空")
    private RefundReason mRefundReason;

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

    public String getPayTradeNo() {
        return mPayTradeNo;
    }

    public String getPayNo() {
        return mPayNo;
    }

    public void setPayNo(String pPayNo) {
        mPayNo = pPayNo;
    }

    public void setPayTradeNo(String pPayTradeNo) {
        mPayTradeNo = pPayTradeNo;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String pOrderNo) {
        mOrderNo = pOrderNo;
    }

    public Double getRefundAmount() {
        return mRefundAmount;
    }

    public void setRefundAmount(Double pRefundAmount) {
        mRefundAmount = pRefundAmount;
    }

    public Double getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(Double pTotalAmount) {
        mTotalAmount = pTotalAmount;
    }

    public String getRefundUserId() {
        return mRefundUserId;
    }

    public void setRefundUserId(String pRefundUserId) {
        mRefundUserId = pRefundUserId;
    }

    public String getRefundUserName() {
        return mRefundUserName;
    }

    public void setRefundUserName(String pRefundUserName) {
        mRefundUserName = pRefundUserName;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String pRemark) {
        mRemark = pRemark;
    }

    public RefundReason getRefundReason() {
        return mRefundReason;
    }

    public void setRefundReason(RefundReason pRefundReason) {
        mRefundReason = pRefundReason;
    }

    @Override
    public String toString() {
        return "RefundRequestDto{" +
                "mPayType=" + mPayType +
                ", mPayTradeNo='" + mPayTradeNo + '\'' +
                ", mRefundAmount=" + mRefundAmount +
                ", mTotalAmount=" + mTotalAmount +
                ", mRefundUserId='" + mRefundUserId + '\'' +
                ", mRefundUserName='" + mRefundUserName + '\'' +
                ", mRemark='" + mRemark + '\'' +
                ", mRefundReason=" + mRefundReason +
                '}';
    }
}
