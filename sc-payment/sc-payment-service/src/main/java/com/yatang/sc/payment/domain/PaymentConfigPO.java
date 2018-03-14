package com.yatang.sc.payment.domain;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.PayWayStatus;

/**
 * Created by yuwei on 2017/7/8.
 */
public class PaymentConfigPO extends BaseDomain {
    private PayType mPayType;
    private String mAppId;
    private String mMercId;
    private String mSignKey;
    private String mPublicKey;
    private String mPrivateKey;
    private String mSignType;
    private String mPayServer;
    private String mPrePayApi;
    private String mRefundApi;
    private String mAsynNotifyUrl;
    private String mQueryPayStatusApi;
    private String mQueryRefundStatusApi;
    private String mCertPassword;
    private String mCertPath;
    private Integer mMaxTryRefundCount = 1;
    private PayWayStatus mStatus;

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

    public String getAppId() {
        return mAppId;
    }

    public void setAppId(String pAppId) {
        mAppId = pAppId;
    }

    public String getMercId() {
        return mMercId;
    }

    public void setMercId(String pMercId) {
        mMercId = pMercId;
    }

    public String getSignKey() {
        return mSignKey;
    }

    public void setSignKey(String pSignKey) {
        mSignKey = pSignKey;
    }

    public String getPublicKey() {
        return mPublicKey;
    }

    public void setPublicKey(String pPublicKey) {
        mPublicKey = pPublicKey;
    }

    public String getPrivateKey() {
        return mPrivateKey;
    }

    public void setPrivateKey(String pPrivateKey) {
        mPrivateKey = pPrivateKey;
    }

    public String getSignType() {
        return mSignType;
    }

    public void setSignType(String pSignType) {
        mSignType = pSignType;
    }

    public String getPrePayApi() {
        return mPrePayApi;
    }

    public void setPrePayApi(String pPrePayApi) {
        mPrePayApi = pPrePayApi;
    }

    public String getRefundApi() {
        return mRefundApi;
    }

    public void setRefundApi(String pRefundApi) {
        mRefundApi = pRefundApi;
    }

    public String getAsynNotifyUrl() {
        return mAsynNotifyUrl;
    }

    public void setAsynNotifyUrl(String pAsynNotifyUrl) {
        mAsynNotifyUrl = pAsynNotifyUrl;
    }

    public String getQueryPayStatusApi() {
        return mQueryPayStatusApi;
    }

    public void setQueryPayStatusApi(String pQueryPayStatusApi) {
        mQueryPayStatusApi = pQueryPayStatusApi;
    }

    public String getQueryRefundStatusApi() {
        return mQueryRefundStatusApi;
    }

    public void setQueryRefundStatusApi(String pQueryRefundStatusApi) {
        mQueryRefundStatusApi = pQueryRefundStatusApi;
    }

    public String getPayServer() {
        if (mPayServer == null) {
            return "";
        }
        return mPayServer;
    }

    public void setPayServer(String pPayServer) {
        mPayServer = pPayServer;
    }

    public String getCertPassword() {
        return mCertPassword;
    }

    public void setCertPassword(String pCertPassword) {
        mCertPassword = pCertPassword;
    }

    public String getCertPath() {
        return mCertPath;
    }

    public void setCertPath(String pCertPath) {
        mCertPath = pCertPath;
    }

    public Integer getMaxTryRefundCount() {
        return mMaxTryRefundCount;
    }

    public void setMaxTryRefundCount(Integer pMaxTryRefundCount) {
        mMaxTryRefundCount = pMaxTryRefundCount;
    }

    public PayWayStatus getStatus() {
        return mStatus;
    }

    public void setStatus(PayWayStatus pStatus) {
        mStatus = pStatus;
    }
}
