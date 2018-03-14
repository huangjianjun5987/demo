package com.yatang.sc.payment.dto.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RefundConfirmDto implements Serializable {
    @NotNull
    private String mRefundNo;
    @NotNull(message = "操作ID不能为空")
    private String mOperatorId;
    @NotNull(message = "操作人名不能为空")
    private String mOperatorName;
    @NotNull
    private Boolean mPassed;
    private String mRemark;

    public String getRefundNo() {
        return mRefundNo;
    }

    public void setRefundNo(String pRefundNo) {
        mRefundNo = pRefundNo;
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

    public Boolean getPassed() {
        return mPassed;
    }

    public void setPassed(Boolean pPassed) {
        mPassed = pPassed;
    }

    @Override
    public String toString() {
        return "RefundConfirmDto{" +
                "mRefundNo='" + mRefundNo + '\'' +
                ", mOperatorId='" + mOperatorId + '\'' +
                ", mOperatorName='" + mOperatorName + '\'' +
                ", mPassed=" + mPassed +
                ", mRemark='" + mRemark + '\'' +
                '}';
    }
}
