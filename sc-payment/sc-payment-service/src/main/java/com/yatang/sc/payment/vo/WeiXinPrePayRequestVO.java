package com.yatang.sc.payment.vo;

/**
 * Created by yuwei on 2017/7/8.
 */
public class WeiXinPrePayRequestVO {
    private String mAppId;//应用ID
    private String mMchId;//商户号
    private String mNotifyUrl;//通知地址
    private String mBody;//商品描述
    private String mOutTradeNo;//商户订单号
    private String mSpbillCreateIp;//用户端实际ip
    private Double mTotalFee;//总金额
    private String mTradeType;//交易类型

    public String getAppId() {
        return mAppId;
    }

    public void setAppId(String pAppId) {
        mAppId = pAppId;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String pBody) {
        mBody = pBody;
    }

    public String getMchId() {
        return mMchId;
    }

    public void setMchId(String pMchId) {
        mMchId = pMchId;
    }

    public String getNotifyUrl() {
        return mNotifyUrl;
    }

    public void setNotifyUrl(String pNotifyUrl) {
        mNotifyUrl = pNotifyUrl;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String pOutTradeNo) {
        mOutTradeNo = pOutTradeNo;
    }

    public String getSpbillCreateIp() {
        return mSpbillCreateIp;
    }

    public void setSpbillCreateIp(String pSpbillCreateIp) {
        mSpbillCreateIp = pSpbillCreateIp;
    }

    public Double getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Double pTotalFee) {
        mTotalFee = pTotalFee;
    }

    public String getTradeType() {
        return mTradeType;
    }

    public void setTradeType(String pTradeType) {
        mTradeType = pTradeType;
    }

    @Override
    public String toString() {
        return "WeiXinPrePayRequestVO{" +
                "mAppId='" + mAppId + '\'' +
                ", mBody='" + mBody + '\'' +
                ", mMchId='" + mMchId + '\'' +
                ", mNotifyUrl='" + mNotifyUrl + '\'' +
                ", mOutTradeNo='" + mOutTradeNo + '\'' +
                ", mSpbillCreateIp='" + mSpbillCreateIp + '\'' +
                ", mTotalFee='" + mTotalFee + '\'' +
                ", mTradeType='" + mTradeType + '\'' +
                '}';
    }
}
