package com.yatang.sc.glink.web.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 请求对象<br>
 * 使用统一格式
 *
 * @author yangqingsong
 */
public class Request<T> {
    private String method;
    private String timestamp;
    private String appkey;
    private String sign;
    @JSONField(name = "orders")
    private T data;

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

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request [method=" + method + ", timestamp=" + timestamp + ", appkey=" + appkey + ", sign=" + sign + ", data=" + data + "]";
    }
}
