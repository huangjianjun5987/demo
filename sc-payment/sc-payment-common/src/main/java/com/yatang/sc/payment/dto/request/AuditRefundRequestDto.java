package com.yatang.sc.payment.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/14.
 */
public class AuditRefundRequestDto extends NoneStrRequestDto implements Serializable {
    @NotNull
    private String mRefundNo;
    @NotNull(message = "审核退款人ID不能为空")
    private String mAuditorId;
    @NotNull(message = "审核退款人名不能为空")
    private String mAuditorName;
    @NotNull(message = "审核退款是否通过")
    private Boolean mPassed;
    @NotNull(message = "退款金额不能为空")
    private Double mFinalRefundAmount;
    private String mRemark;

    public String getRefundNo() {
        return mRefundNo;
    }

    public void setRefundNo(String pRefundNo) {
        mRefundNo = pRefundNo;
    }

    public String getAuditorId() {
        return mAuditorId;
    }

    public void setAuditorId(String pAuditorId) {
        mAuditorId = pAuditorId;
    }

    public String getAuditorName() {
        return mAuditorName;
    }

    public void setAuditorName(String pAuditorName) {
        mAuditorName = pAuditorName;
    }

    public Boolean getPassed() {
        return mPassed;
    }

    public void setPassed(Boolean pPassed) {
        mPassed = pPassed;
    }

    public Double getFinalRefundAmount() {
        return mFinalRefundAmount;
    }

    public void setFinalRefundAmount(Double pFinalRefundAmount) {
        mFinalRefundAmount = pFinalRefundAmount;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String pRemark) {
        mRemark = pRemark;
    }
}
