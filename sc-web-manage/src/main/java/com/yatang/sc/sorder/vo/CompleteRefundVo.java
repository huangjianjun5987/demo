package com.yatang.sc.sorder.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CompleteRefundVo implements Serializable {
    @NotNull
    private String mRefundNo;
    @NotNull
    private Boolean mPassed;
    private String mRemark;

    public String getRefundNo() {
        return mRefundNo;
    }

    public void setRefundNo(String pRefundNo) {
        mRefundNo = pRefundNo;
    }

    public Boolean getPassed() {
        return mPassed;
    }

    public void setPassed(Boolean pPassed) {
        mPassed = pPassed;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String pRemark) {
        mRemark = pRemark;
    }

    @Override
    public String toString() {
        return "CompleteRefundVo{" +
                "mRefundNo='" + mRefundNo + '\'' +
                ", mPassed=" + mPassed +
                ", mRemark='" + mRemark + '\'' +
                '}';
    }
}
