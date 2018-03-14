package com.yatang.sc.settlement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 付款方式
 * @作者: tankejia
 * @创建时间: 2017/8/31-16:28 .
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum PayType {
    ONLINE_BANK("网银"),
    TRANSFER("银行转账"),
    CASH("现金"),
    CHECK("支票"),
    ;

    private final String description;
}
