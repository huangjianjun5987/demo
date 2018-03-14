package com.yatang.sc.payment.exception;

/**
 * Created by yuwei on 2017/7/10.
 */
public class ValidationSignException extends BuzException {
    public ValidationSignException(String pMsg) {
        super(pMsg);
    }

    public ValidationSignException(String pMsg, Throwable cause) {
        super(pMsg, cause);
    }

    public ValidationSignException(String pCode, String pMsg) {
        super(pCode, pMsg);
    }

    public ValidationSignException(String pCode, String pMsg, Throwable cause) {
        super(pCode, pMsg, cause);
    }
}
