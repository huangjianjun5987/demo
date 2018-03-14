package com.yatang.sc.payment.dto.response;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/14.
 */
public class RefundResponseDto implements Serializable{
    private Double mRefundAmount;
    private String mRefundTradeNo;
    private String mRefundNo;
    private boolean mSuccess;
    private String mMsg;

    public Double getRefundAmount() {
        return mRefundAmount;
    }

    public void setRefundAmount(Double pRefundAmount) {
        mRefundAmount = pRefundAmount;
    }

    public String getRefundTradeNo() {
        return mRefundTradeNo;
    }

    public void setRefundTradeNo(String pRefundTradeNo) {
        mRefundTradeNo = pRefundTradeNo;
    }

    public String getRefundNo() {
        return mRefundNo;
    }

    public void setRefundNo(String pRefundNo) {
        mRefundNo = pRefundNo;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean pSuccess) {
        mSuccess = pSuccess;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String pMsg) {
        mMsg = pMsg;
    }

    @Override
    public String toString() {
        return "RefundResponseDto{" +
                "mRefundAmount=" + mRefundAmount +
                ", mRefundTradeNo='" + mRefundTradeNo + '\'' +
                ", mRefundNo='" + mRefundNo + '\'' +
                ", mSuccess=" + mSuccess +
                ", mMsg='" + mMsg + '\'' +
                '}';
    }
}
