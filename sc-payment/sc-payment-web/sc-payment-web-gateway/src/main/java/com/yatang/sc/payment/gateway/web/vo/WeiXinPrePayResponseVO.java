package com.yatang.sc.payment.gateway.web.vo;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/11.
 */
public class WeiXinPrePayResponseVO implements Serializable {
    private String mPrepayid;
    private String mNnoncestr;
    private String mAppid;
    private String mPartnerid;
    private String mPackage;
    private String mTimestamp;
    private String mSign;

    public String getPrepayid() {
        return mPrepayid;
    }

    public void setPrepayid(String pPrepayid) {
        mPrepayid = pPrepayid;
    }

    public String getNnoncestr() {
        return mNnoncestr;
    }

    public void setNnoncestr(String pNnoncestr) {
        mNnoncestr = pNnoncestr;
    }

    public String getAppid() {
        return mAppid;
    }

    public void setAppid(String pAppid) {
        mAppid = pAppid;
    }

    public String getPartnerid() {
        return mPartnerid;
    }

    public void setPartnerid(String pPartnerid) {
        mPartnerid = pPartnerid;
    }

    public String getPackage() {
        return mPackage;
    }

    public void setPackage(String pPackage) {
        mPackage = pPackage;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String pTimestamp) {
        mTimestamp = pTimestamp;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String pSign) {
        mSign = pSign;
    }
}
