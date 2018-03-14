package com.yatang.sc.payment.dto.response;

import com.yatang.sc.payment.enums.PayType;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/13.
 */
public class PrePayResponseDto<T> implements Serializable {
    private PayType mPayType;
    private T mPrePayInfo;
    private String mPayNo;//交易流水号

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

    public T getPrePayInfo() {
        return mPrePayInfo;
    }

    public void setPrePayInfo(T pPrePayInfo) {
        mPrePayInfo = pPrePayInfo;
    }

    public String getPayNo() {
        return mPayNo;
    }

    public void setPayNo(String pPayNo) {
        mPayNo = pPayNo;
    }

    @Override
    public String toString() {
        return "PrePayResponseDto{" +
                "mPayType=" + mPayType +
                ", mPrePayInfo=" + mPrePayInfo +
                ", mPayNo='" + mPayNo + '\'' +
                '}';
    }
}
