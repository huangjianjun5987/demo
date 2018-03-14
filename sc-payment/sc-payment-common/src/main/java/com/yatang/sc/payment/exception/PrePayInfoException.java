package com.yatang.sc.payment.exception;

/**
 * Created by yuwei on 2017/7/15.
 */
public class PrePayInfoException extends BuzException {
    public PrePayInfoException(String pMsg) {
        super(pMsg);
    }

    public PrePayInfoException(String pMsg, Throwable cause) {
        super(pMsg, cause);
    }

    public PrePayInfoException(String pCode, String pMsg) {
        super(pCode, pMsg);
    }

    public PrePayInfoException(String pCode, String pMsg, Throwable cause) {
        super(pCode, pMsg, cause);
    }
}
