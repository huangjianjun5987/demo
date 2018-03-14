package com.yatang.sc.payment.vo;

/**
 * Created by yuwei on 2017/7/8.
 */
public class WeiXinRefundRequestVO {
    private String mAppId;
    private String mMchId;
    private String mNonceStr;
    private String mTransactionId;
    private String mOutRefundNo;
    private String mTotalFee;
    private String mRefundFee;
    private String mOpUserId;

    public String getAppId() {
        return mAppId;
    }

    public void setAppId(String pAppId) {
        mAppId = pAppId;
    }

    public String getMchId() {
        return mMchId;
    }

    public void setMchId(String pMchId) {
        mMchId = pMchId;
    }

    public String getNonceStr() {
        return mNonceStr;
    }

    public void setNonceStr(String pNonceStr) {
        mNonceStr = pNonceStr;
    }

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String pTransactionId) {
        mTransactionId = pTransactionId;
    }

    public String getOutRefundNo() {
        return mOutRefundNo;
    }

    public void setOutRefundNo(String pOutRefundNo) {
        mOutRefundNo = pOutRefundNo;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String pTotalFee) {
        mTotalFee = pTotalFee;
    }

    public String getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(String pRefundFee) {
        mRefundFee = pRefundFee;
    }

    public String getOpUserId() {
        return mOpUserId;
    }

    public void setOpUserId(String pOpUserId) {
        mOpUserId = pOpUserId;
    }

    @Override
    public String toString() {
        return "WeiXinRefundRequestVO{" +
                "mAppId='" + mAppId + '\'' +
                ", mMchId='" + mMchId + '\'' +
                ", mNonceStr='" + mNonceStr + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mOutRefundNo='" + mOutRefundNo + '\'' +
                ", mTotalFee='" + mTotalFee + '\'' +
                ", mRefundFee='" + mRefundFee + '\'' +
                ", mOpUserId='" + mOpUserId + '\'' +
                '}';
    }
}
