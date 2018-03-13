package com.yatang.sc.glink.dto;

import java.io.Serializable;

/**
 * create by chensiqiang
 * Glink接口响应DTO
 */
public class GlinkResponseDto<T> implements Serializable{

    private int code;       //接口调用状态码

    private String message; //调用不成功状态描述

    private T data;    //返回结果

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
        return "GlinkResponseDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
