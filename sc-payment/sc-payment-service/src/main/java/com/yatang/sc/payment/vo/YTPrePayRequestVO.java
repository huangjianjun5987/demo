package com.yatang.sc.payment.vo;

/**
 * Created by yuwei on 2017/7/14.
 */
public class YTPrePayRequestVO {

    private String mToken;
    private String mShopUserName;
    private String mUserName;
    private String mOrderNo;
    private double mOrderAmount;
    private String mGoodsName;
    private String mSyncUrl;
    private String mAsyncUrl;

    public String getToken() {
        return mToken;
    }

    public void setToken(String pToken) {
        mToken = pToken;
    }

    public String getShopUserName() {
        return mShopUserName;
    }

    public void setShopUserName(String pShopUserName) {
        mShopUserName = pShopUserName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String pUserName) {
        mUserName = pUserName;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String pOrderNo) {
        mOrderNo = pOrderNo;
    }

    public double getOrderAmount() {
        return mOrderAmount;
    }

    public void setOrderAmount(double pOrderAmount) {
        mOrderAmount = pOrderAmount;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String pGoodsName) {
        mGoodsName = pGoodsName;
    }

    public String getSyncUrl() {
        return mSyncUrl;
    }

    public void setSyncUrl(String pSyncUrl) {
        mSyncUrl = pSyncUrl;
    }

    public String getAsyncUrl() {
        return mAsyncUrl;
    }

    public void setAsyncUrl(String pAsyncUrl) {
        mAsyncUrl = pAsyncUrl;
    }
}
