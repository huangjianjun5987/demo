package com.yatang.sc.inventory.dubboservice.exception;

/**
 * @描述: 库存调整异常类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 下午2:42
 * @版本: v1.0
 */
public class ImAdjustmentException  extends RuntimeException{
    private static final long serialVersionUID = 4410000738288820958L;

    public ImAdjustmentException(String message) {
        super(message);
    }
}
