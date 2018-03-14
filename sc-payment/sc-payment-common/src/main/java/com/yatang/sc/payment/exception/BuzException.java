package com.yatang.sc.payment.exception;

/**
 * Created by yuwei on 2017/7/17.
 */
public class BuzException extends Exception {
    protected String mCode;

    public BuzException(String pMsg) {
        super(pMsg);
    }

    public BuzException(String pMsg, Throwable cause) {
        super(pMsg);
    }

    public BuzException(String pCode, String pMsg) {
        super(pMsg);
        this.mCode = pCode;
    }

    public BuzException(String pCode, String pMsg, Throwable cause) {
        super(pMsg, cause);
        this.mCode = pCode;
    }

    public String getCode() {
        return mCode;
    }

}
