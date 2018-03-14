package com.yatang.sc.payment.gateway.web.vo;

import java.io.Serializable;
import java.util.Date;

public class PrePayResponseVO<T> implements Serializable {
    private Date timestamp;
    private boolean isSuccess;
    private int code;
    private String message;
    private T data;

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setTimestamp(Date pTimestamp) {
        timestamp = pTimestamp;
    }

    public void setSuccess(boolean pSuccess) {
        isSuccess = pSuccess;
    }

    public void setCode(int pCode) {
        code = pCode;
    }

    public void setMessage(String pMessage) {
        message = pMessage;
    }

    public void setData(T pData) {
        data = pData;
    }
}
