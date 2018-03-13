package com.yatang.sc.facade.exception;

/**
 * @描述: 商品采购关系异常定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/26 14:32
 * @版本: v1.0
 */
public class ProdPurchaseException extends RuntimeException {

    private String message;//信息
    private String code;//验证码

    public ProdPurchaseException() {
        super();
    }


    /**
     * @param message 错误信息
     * @param code    响应码
     */
    public ProdPurchaseException(String message, String code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * @param message,e
     */
    public ProdPurchaseException(String message, Throwable e) {
        super(message, e);

    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
