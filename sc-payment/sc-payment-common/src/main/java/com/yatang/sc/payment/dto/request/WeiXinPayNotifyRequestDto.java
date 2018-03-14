package com.yatang.sc.payment.dto.request;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/11.
 */
public class WeiXinPayNotifyRequestDto extends AbstractPayNotifyRequestDto implements Serializable {

    private String mAppId;//应用ID
    private String mMchId;//商户号
    private String mResultCode;//业务结果 SUCCESS/FAIL
    private String mReturnCode;//返回状态码
    private String mReturnMsg;//返回信息
    private String mErrCode;//错误代码
    private String mErrCodeDes;//错误代码描述
    private String mTradeType;//交易类型 APP
    private String mTimeEnd;//格式为yyyyMMddHHmmss

    public String getReturnCode() {
        return mReturnCode;
    }

    public void setReturnCode(String pReturnCode) {
        mReturnCode = pReturnCode;
    }

    public String getReturnMsg() {
        return mReturnMsg;
    }

    public void setReturnMsg(String pReturnMsg) {
        mReturnMsg = pReturnMsg;
    }

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

    public String getResultCode() {
        return mResultCode;
    }

    public void setResultCode(String pResultCode) {
        mResultCode = pResultCode;
    }

    public String getErrCode() {
        return mErrCode;
    }

    public void setErrCode(String pErrCode) {
        mErrCode = pErrCode;
    }

    public String getErrCodeDes() {
        return mErrCodeDes;
    }

    public void setErrCodeDes(String pErrCodeDes) {
        mErrCodeDes = pErrCodeDes;
    }

    public String getTradeType() {
        return mTradeType;
    }

    public void setTradeType(String pTradeType) {
        mTradeType = pTradeType;
    }


    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String pTimeEnd) {
        mTimeEnd = pTimeEnd;
    }

    @Override
    public String toString() {
        return "WeiXinPayNotifyRequestDto{" +
                "mAppId='" + mAppId + '\'' +
                ", mMchId='" + mMchId + '\'' +
                ", mResultCode='" + mResultCode + '\'' +
                ", mReturnCode='" + mReturnCode + '\'' +
                ", mReturnMsg='" + mReturnMsg + '\'' +
                ", mErrCode='" + mErrCode + '\'' +
                ", mErrCodeDes='" + mErrCodeDes + '\'' +
                ", mTradeType='" + mTradeType + '\'' +
                ", mTimeEnd='" + mTimeEnd + '\'' +
                '}';
    }
}
