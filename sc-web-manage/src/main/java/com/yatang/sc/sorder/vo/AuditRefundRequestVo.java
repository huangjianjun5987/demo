package com.yatang.sc.sorder.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AuditRefundRequestVo implements Serializable {
    @NotNull
    private String refundNo;
    @NotNull
    private Boolean passed;
    @NotNull
    private Double amount;
    private String remark;

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String pRefundNo) {
        refundNo = pRefundNo;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean pPassed) {
        passed = pPassed;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double pAmount) {
        amount = pAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String pRemark) {
        remark = pRemark;
    }

    @Override
    public String toString() {
        return "AuditRefundRequestVo{" +
                "refundNo='" + refundNo + '\'' +
                ", passed=" + passed +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                '}';
    }
}

