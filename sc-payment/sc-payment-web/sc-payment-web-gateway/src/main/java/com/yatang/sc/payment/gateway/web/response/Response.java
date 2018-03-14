package com.yatang.sc.payment.gateway.web.response;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 4435099346743782566L;
    private boolean isSuccess;
    private String errorMessage;
    private int code = 200;
    private T resultObject;
    private Integer pageNum;

    public Response() {
    }

    public Response(boolean isSuccess, T resultObject) {
        this.isSuccess = isSuccess;
        this.resultObject = resultObject;
    }

    public Response(boolean isSuccess, String errorMessage, int code) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
        this.code = code;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int pCode) {
        code = pCode;
    }

    public T getResultObject() {
        return this.resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pPageNum) {
        this.pageNum = pPageNum;
    }
}
