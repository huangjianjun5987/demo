package com.yatang.sc.glink.web.bean;

import java.io.Serializable;

/**
 * @描述: Glink推送库存调整单
 * @作者: yangshuang
 * @创建时间:2017年09月04日15:07
 */

public class GLinkImAdjustmentReceiptVo implements Serializable{

    private static final long serialVersionUID = 4501266981577554646L;
    private String method;      //API接口名称
    private String timestamp;   //时间戳
    private String app_key;      //客户授权码
    private String sign;        //输入参数签名摘要

    // private GLinkImAdjustmentDataDto data;
    private String data;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }



    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }
}
