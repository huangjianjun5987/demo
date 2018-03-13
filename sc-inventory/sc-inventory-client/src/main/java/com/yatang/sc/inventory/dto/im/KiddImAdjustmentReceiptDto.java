package com.yatang.sc.inventory.dto.im;

import java.io.Serializable;

/**
 * @描述: kidd推送库存调整单
 * @作者: yangshuang
 * @创建时间:2017年09月04日15:07
 */

public class KiddImAdjustmentReceiptDto implements Serializable {

    private static final long serialVersionUID = 4501266981577554646L;
    private String method;      //API接口名称
    private String timestamp;   //时间戳
    private String app_key;      //客户授权码
    private String sign;        //输入参数签名摘要

    private KiddImAdjustmentDataDto data;

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

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public KiddImAdjustmentDataDto getData() {
        return data;
    }

    public void setData(KiddImAdjustmentDataDto data) {
        this.data = data;
    }
}
