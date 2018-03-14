package com.yatang.sc.exception;

/**
 * mq消息处理失败异常
 */
public class MqBizFailureException extends RuntimeException {
    public MqBizFailureException() {
        super();
    }

    public MqBizFailureException(String message) {
        super(message);
    }
}
