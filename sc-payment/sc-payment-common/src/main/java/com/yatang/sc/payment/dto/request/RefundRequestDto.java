package com.yatang.sc.payment.dto.request;

import com.yatang.sc.payment.enums.PayType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by yuwei on 2017/7/14.
 */
public class RefundRequestDto extends NoneStrRequestDto {
    private PayType mPayType;
    @NotNull
    private String mRefundNo;
    @NotNull(message = "操作ID不能为空")
    private String mOperatorId;
    @NotNull(message = "操作人名不能为空")
    private String mOperatorName;
    private String mRemark;

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

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

    @Override
    public String toString() {
        return "RefundRequestDto{" +
                "mPayType=" + mPayType +
                ", mRefundNo='" + mRefundNo + '\'' +
                ", mOperatorId='" + mOperatorId + '\'' +
                ", mOperatorName='" + mOperatorName + '\'' +
                ", mRemark='" + mRemark + '\'' +
                '}';
    }
}
