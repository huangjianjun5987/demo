package com.yatang.sc.payment.dto.response;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/13.
 */
public class PayStatusQueryResponseDto implements Serializable {
    private boolean mIsSuccess;
    private String mTradeNo;
    private String mPayNo;
    private Double mTotalAmount;
    private String mPayEndTime;
    private String mErrorMsg;

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setSuccess(boolean pSuccess) {
        mIsSuccess = pSuccess;
    }

    public String getTradeNo() {
        return mTradeNo;
    }

    public void setTradeNo(String pTradeNo) {
        mTradeNo = pTradeNo;
    }

    public String getPayNo() {
        return mPayNo;
    }

    public void setPayNo(String pPayNo) {
        mPayNo = pPayNo;
    }

    public Double getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(Double pTotalAmount) {
        mTotalAmount = pTotalAmount;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String pErrorMsg) {
        mErrorMsg = pErrorMsg;
    }

    public String getPayEndTime() {
        return mPayEndTime;
    }

    public void setPayEndTime(String pPayEndTime) {
        mPayEndTime = pPayEndTime;
    }
}
