package com.yatang.sc.glink.web.bean;

/**
 * 返回结果
 *
 * @author yangqingsong
 */
public class Response<T> {
    private String ownerCode;
    private int code;
    private String message;
    private T data;

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [ownerCode=" + ownerCode + ", code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}
