package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/12.
 * 枚举实现该接口可以实现mybatis自动转换
 */
public interface BaseEnum<E extends Enum<E>, R> {
    R getCode();
}
