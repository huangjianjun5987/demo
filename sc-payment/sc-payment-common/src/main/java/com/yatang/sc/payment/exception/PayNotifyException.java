package com.yatang.sc.payment.exception;

/**
 * Created by yuwei on 2017/7/15.
 */
public class PayNotifyException extends BuzException {
    public PayNotifyException(String pMsg) {
        super(pMsg);
    }

    public PayNotifyException(String pMsg, Throwable cause) {
        super(pMsg, cause);
    }

    public PayNotifyException(String pCode, String pMsg) {
        super(pCode, pMsg);
    }

    public PayNotifyException(String pCode, String pMsg, Throwable cause) {
        super(pCode, pMsg, cause);
    }
}
