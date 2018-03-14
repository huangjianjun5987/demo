package com.yatang.sc.payment.dto.request;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundReason;

import java.io.Serializable;
import java.util.Date;

public class AddRefundRecordDto implements Serializable {
    private String orderId;//订单
    private Double amount;//退款金额
    private String tradeNo;//退款交易号
    private Date refundTime;//退款时间
    private PayType paytype;//支付方式
    private RefundReason reason;//退款原因
    private String operatorId;//操作人id
    private String operatorName;//操作人名称
    private String remark;//备注

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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String pOperatorId) {
        operatorId = pOperatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String pOperatorName) {
        operatorName = pOperatorName;
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
        return "AddRefundRecordDto{" +
                "orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", tradeNo='" + tradeNo + '\'' +
                ", refundTime=" + refundTime +
                ", paytype=" + paytype +
                ", reason='" + reason + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
