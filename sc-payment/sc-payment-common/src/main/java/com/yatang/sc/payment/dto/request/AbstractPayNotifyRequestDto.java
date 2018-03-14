package com.yatang.sc.payment.dto.request;

import com.yatang.sc.payment.dto.BaseDto;
import com.yatang.sc.payment.enums.NotifyType;
import com.yatang.sc.payment.enums.PayType;

import java.util.Date;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/11.
 */
public abstract class AbstractPayNotifyRequestDto extends BaseDto {
    private PayType mPayType;

    private NotifyType mNotifyType; //通知方式

    private String mOutTradeNo;//商户订单号

    private String mTradeNo;//交易流水号

    private Double mTotalAmount;//交易金额(元)

    private String mPayAccount;//支付账号

    private Map<String, String> mOriginalMaps;//原始回调参数

    private Date mGmtPayment;//交易付款时间

    public Map<String, String> getOriginalMaps() {
        return mOriginalMaps;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String pOutTradeNo) {
        mOutTradeNo = pOutTradeNo;
    }

    public void setOriginalMaps(Map<String, String> pOriginalMaps) {
        mOriginalMaps = pOriginalMaps;
    }

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

    public String getTradeNo() {
        return mTradeNo;
    }

    public void setTradeNo(String pTradeNo) {
        mTradeNo = pTradeNo;
    }

    public Double getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(Double pTotalAmount) {
        mTotalAmount = pTotalAmount;
    }

    public NotifyType getNotifyType() {
        return mNotifyType;
    }

    public void setNotifyType(NotifyType pNotifyType) {
        mNotifyType = pNotifyType;
    }

    public String getPayAccount() {
        return mPayAccount;
    }

    public void setPayAccount(String pPayAccount) {
        mPayAccount = pPayAccount;
    }

    public Date getGmtPayment() {
        return mGmtPayment;
    }

    public void setGmtPayment(Date pGmtPayment) {
        mGmtPayment = pGmtPayment;
    }

    @Override
    public String toString() {
        return "AbstractPayNotifyRequestDto{" +
                "mPayType=" + mPayType +
                ", mNotifyType=" + mNotifyType +
                ", mOutTradeNo='" + mOutTradeNo + '\'' +
                ", mTradeNo='" + mTradeNo + '\'' +
                ", mTotalAmount=" + mTotalAmount +
                ", mPayAccount='" + mPayAccount + '\'' +
                ", mOriginalMaps=" + mOriginalMaps +
                ", mGmtPayment='" + mGmtPayment + '\'' +
                '}';
    }
}
