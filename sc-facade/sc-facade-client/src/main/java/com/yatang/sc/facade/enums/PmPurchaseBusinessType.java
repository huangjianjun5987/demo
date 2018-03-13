package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 经营模式:0:经销;1:代销
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/28 11:09
 * @版本: v1.0
 */
@Getter
@AllArgsConstructor
public enum PmPurchaseBusinessType {

    distribution_TYPE(0, "经销"),

    DEPUTE_SALE_TYPE(1, "代销"),
    CONSIGNMENT_SALE_TYPE(2, "寄售");


    private final Integer code;

    private final String desc;
}
