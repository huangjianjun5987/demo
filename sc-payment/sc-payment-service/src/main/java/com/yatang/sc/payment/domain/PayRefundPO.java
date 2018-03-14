package com.yatang.sc.payment.domain;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundReason;
import com.yatang.sc.payment.enums.RefundStatus;

import java.util.Date;

/**
 * Created by yuwei on 2017/7/11.
 */
public class PayRefundPO extends BaseDomain {
    private String mPayTradeNo;//支付流水
    private String mPayNo;//支付订单号
    private String mOrderNo;//订单号
    private String mRefundNo;//退款单号
    private PayType mPayType;//支付类型
    private Double mTotalPaiedAmount;//总共支付金额
    private String mRefundTradeNo;//退款流水号
    private Double mRefundAmount;//退款金额
    private RefundReason mReason;//退款原因
    private RefundStatus mStatus;//状态
    private String mRequestUserId;//申请退款人ID
    private String mRequestUserName;//申请退款人名字
    private Date mApprovedTime;//审核通过时间
    private String mApprovedOperatorId;//审核通过操作人ID
    private String mApprovedOperatorName;//审核通过操作人名字
    private Date mRefundTime;//退款完成时间
    private String mRefundOperatorId;//退款操作人ID
    private String mRefundOperatorName;//退款操作人名字
    private Integer mTryRefundCount;//尝试退款请求次数
    private String mRemark; //备注

    public String getPayTradeNo() {
        return mPayTradeNo;
    }

    public void setPayTradeNo(String pPayTradeNo) {
        mPayTradeNo = pPayTradeNo;
    }

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

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

    public Double getTotalPaiedAmount() {
        return mTotalPaiedAmount;
    }

    public void setTotalPaiedAmount(Double pTotalPaiedAmount) {
        mTotalPaiedAmount = pTotalPaiedAmount;
    }

    public String getRefundNo() {
        return mRefundNo;
    }

    public void setRefundNo(String pRefundNo) {
        mRefundNo = pRefundNo;
    }

    public String getRefundTradeNo() {
        return mRefundTradeNo;
    }

    public void setRefundTradeNo(String pRefundTradeNo) {
        mRefundTradeNo = pRefundTradeNo;
    }

    public Double getRefundAmount() {
        return mRefundAmount;
    }

    public void setRefundAmount(Double pRefundAmount) {
        mRefundAmount = pRefundAmount;
    }

    public RefundReason getReason() {
        return mReason;
    }

    public void setReason(RefundReason pReason) {
        mReason = pReason;
    }

    public RefundStatus getStatus() {
        return mStatus;
    }

    public void setStatus(RefundStatus pStatus) {
        mStatus = pStatus;
    }

    public String getRequestUserId() {
        return mRequestUserId;
    }

    public void setRequestUserId(String pRequestUserId) {
        mRequestUserId = pRequestUserId;
    }

    public String getRequestUserName() {
        return mRequestUserName;
    }

    public void setRequestUserName(String pRequestUserName) {
        mRequestUserName = pRequestUserName;
    }

    public Date getApprovedTime() {
        return mApprovedTime;
    }

    public void setApprovedTime(Date pApprovedTime) {
        mApprovedTime = pApprovedTime;
    }

    public String getApprovedOperatorId() {
        return mApprovedOperatorId;
    }

    public void setApprovedOperatorId(String pApprovedOperatorId) {
        mApprovedOperatorId = pApprovedOperatorId;
    }

    public String getApprovedOperatorName() {
        return mApprovedOperatorName;
    }

    public void setApprovedOperatorName(String pApprovedOperatorName) {
        mApprovedOperatorName = pApprovedOperatorName;
    }

    public Date getRefundTime() {
        return mRefundTime;
    }

    public void setRefundTime(Date pRefundTime) {
        mRefundTime = pRefundTime;
    }

    public String getRefundOperatorId() {
        return mRefundOperatorId;
    }

    public void setRefundOperatorId(String pRefundOperatorId) {
        mRefundOperatorId = pRefundOperatorId;
    }

    public String getRefundOperatorName() {
        return mRefundOperatorName;
    }

    public void setRefundOperatorName(String pRefundOperatorName) {
        mRefundOperatorName = pRefundOperatorName;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String pRemark) {
        mRemark = pRemark;
    }

    public Integer getTryRefundCount() {
        return mTryRefundCount;
    }

    public void setTryRefundCount(Integer pTryRefundCount) {
        mTryRefundCount = pTryRefundCount;
    }

    @Override
    public String toString() {
        return "PayRefundPO{" +
                "mPayTradeNo='" + mPayTradeNo + '\'' +
                ", mPayNo='" + mPayNo + '\'' +
                ", mOrderNo='" + mOrderNo + '\'' +
                ", mRefundNo='" + mRefundNo + '\'' +
                ", mRefundTradeNo='" + mRefundTradeNo + '\'' +
                ", mRefundAmount=" + mRefundAmount +
                ", mReason='" + mReason + '\'' +
                ", mStatus=" + mStatus +
                ", mRequestUserId='" + mRequestUserId + '\'' +
                ", mRequestUserName='" + mRequestUserName + '\'' +
                ", mApprovedTime=" + mApprovedTime +
                ", mApprovedOperatorId='" + mApprovedOperatorId + '\'' +
                ", mApprovedOperatorName='" + mApprovedOperatorName + '\'' +
                ", mRefundTime=" + mRefundTime +
                ", mRefundOperatorId='" + mRefundOperatorId + '\'' +
                ", mRefundOperatorName='" + mRefundOperatorName + '\'' +
                ", mTryRefundCount=" + mTryRefundCount +
                ", mRemark='" + mRemark + '\'' +
                '}';
    }
}
