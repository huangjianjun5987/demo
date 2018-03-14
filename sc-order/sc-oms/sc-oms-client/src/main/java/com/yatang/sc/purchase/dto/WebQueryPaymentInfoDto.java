package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.List;

public class WebQueryPaymentInfoDto implements Serializable{
    private Double mTotalAmount;
    private Double mTotalPaidAmount;
    private Double mTotalRefundedAmount;
    private List<WebQueryPaymentGroupDto> paymentGroups;

    public Double getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(Double pTotalAmount) {
        mTotalAmount = pTotalAmount;
    }

    public Double getTotalPaidAmount() {
        return mTotalPaidAmount;
    }

    public void setTotalPaidAmount(Double pTotalPaidAmount) {
        mTotalPaidAmount = pTotalPaidAmount;
    }

    public Double getTotalRefundedAmount() {
        return mTotalRefundedAmount;
    }

    public void setTotalRefundedAmount(Double pTotalRefundedAmount) {
        mTotalRefundedAmount = pTotalRefundedAmount;
    }

    public List<WebQueryPaymentGroupDto> getPaymentGroups() {
        return paymentGroups;
    }

    public void setPaymentGroups(List<WebQueryPaymentGroupDto> pPaymentGroups) {
        paymentGroups = pPaymentGroups;
    }
}
