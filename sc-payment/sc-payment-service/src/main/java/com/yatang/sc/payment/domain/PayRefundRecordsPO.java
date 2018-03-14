package com.yatang.sc.payment.domain;

import com.yatang.sc.payment.dto.BaseDto;
import com.yatang.sc.payment.enums.RefundStatus;

/**
 * Created by yuwei on 2017/7/15.
 */
public class PayRefundRecordsPO extends BaseDto {
    private Long mRefundId;
    private String mOperatorId;
    private String mOperatorName;
    private RefundStatus mPreStatus;
    private RefundStatus mCurrentStatus;
    private String mRemark;

    public Long getRefundId() {
        return mRefundId;
    }

    public void setRefundId(Long pRefundId) {
        mRefundId = pRefundId;
    }

    public String getOperatorId() {
        return mOperatorId;
    }

    public void setOperatorId(String pOperatorId) {
        mOperatorId = pOperatorId;
    }

    public String getOperatorName() {
        return mOperatorName;
    }

    public void setOperatorName(String pOperatorName) {
        mOperatorName = pOperatorName;
    }

    public RefundStatus getPreStatus() {
        return mPreStatus;
    }

    public void setPreStatus(RefundStatus pPreStatus) {
        mPreStatus = pPreStatus;
    }

    public RefundStatus getCurrentStatus() {
        return mCurrentStatus;
    }

    public void setCurrentStatus(RefundStatus pCurrentStatus) {
        mCurrentStatus = pCurrentStatus;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String pRemark) {
        mRemark = pRemark;
    }

    @Override
    public String toString() {
        return "PayRefundRecordsPO{" +
                "mRefundId=" + mRefundId +
                ", mOperatorId='" + mOperatorId + '\'' +
                ", mOperatorName='" + mOperatorName + '\'' +
                ", mPreStatus=" + mPreStatus +
                ", mCurrentStatus=" + mCurrentStatus +
                ", mRemark='" + mRemark + '\'' +
                '}';
    }
}
