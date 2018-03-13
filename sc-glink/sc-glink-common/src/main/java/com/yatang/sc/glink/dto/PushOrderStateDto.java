package com.yatang.sc.glink.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * create by chensiqiang
 * 推送订单状态DTO
 */
public class PushOrderStateDto implements Serializable {

    private String method;      //API接口名称
    private String timestamp;   //时间戳
    @JSONField(name = "app_key")
    private String appKey;      //客户授权码
    private String sign;        //输入参数签名摘要
    //List<PushOrdersDto>
    private String orders; //订单信息

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

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
