package com.yatang.sc.exception;

/**
 * @描述: 定义退换货单的异常类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/12 15:16
 * @版本: v1.0
 */
public class OrderReturnedException extends RuntimeException {
    private static final long serialVersionUID = -7970415343933363804L;

    private String message;//信息

    public OrderReturnedException() {
        super();
    }


    /**
     * @param message 错误信息
     */
    public OrderReturnedException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * @param message,e
     */
    public OrderReturnedException(String message, Throwable e) {
        super(message, e);

    }

    @Override
    public String getMessage() {
        return message;
    }

}
