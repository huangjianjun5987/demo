package com.yatang.sc.sorder.vo;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundReason;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class AddRefundRecordVo implements Serializable {
    @NotNull
    private String orderId;
    @NotNull
    private Double amount;
    private String tradeNo;
    private Date refundTime;
    private RefundReason reason;
    private PayType paytype;
    private String remark;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double pAmount) {
        amount = pAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String pTradeNo) {
        tradeNo = pTradeNo;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date pRefundTime) {
        refundTime = pRefundTime;
    }

    public PayType getPaytype() {
        return paytype;
    }

    public void setPaytype(PayType pPaytype) {
        paytype = pPaytype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String pRemark) {
        remark = pRemark;
    }

    public RefundReason getReason() {
        return reason;
    }

    public void setReason(RefundReason pReason) {
        reason = pReason;
    }

    @Override
    public String toString() {
        return "AddRefundRecordVo{" +
                "orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", tradeNo='" + tradeNo + '\'' +
                ", refundTime=" + refundTime +
                ", reason=" + reason +
                ", paytype=" + paytype +
                ", remark='" + remark + '\'' +
                '}';
    }
}
