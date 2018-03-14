package com.yatang.sc.dto;

import java.io.Serializable;

public class CancelOrderRequestDto implements Serializable {
    private String mOrderId;
    private String mOperatorId;
    private String mOperatorName;
    private String mRemark;

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String pOrderId) {
        mOrderId = pOrderId;
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

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String pRemark) {
        mRemark = pRemark;
    }

    @Override
    public String toString() {
        return "CancelOrderRequestDto{" +
                "mOrderId='" + mOrderId + '\'' +
                ", mOperatorId='" + mOperatorId + '\'' +
                ", mOperatorName='" + mOperatorName + '\'' +
                ", mRemark='" + mRemark + '\'' +
                '}';
    }
}
