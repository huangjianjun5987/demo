package com.yatang.sc.payment.exception;

/**
 * Created by yuwei on 2017/7/15.
 */
public class RefundException extends BuzException {
    public RefundException(String pMsg) {
        super(pMsg);
    }

    public RefundException(String pMsg, Throwable cause) {
        super(pMsg, cause);
    }

    public RefundException(String pCode, String pMsg) {
        super(pCode, pMsg);
    }

    public RefundException(String pCode, String pMsg, Throwable cause) {
        super(pCode, pMsg, cause);
    }
}
