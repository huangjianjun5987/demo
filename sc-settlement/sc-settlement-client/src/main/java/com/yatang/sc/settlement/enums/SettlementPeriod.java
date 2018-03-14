package com.yatang.sc.settlement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 账期
 * @作者: tankejia
 * @创建时间: 2017/8/31-16:23 .
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum SettlementPeriod {
    WEEEKLY_SETTLED("周结"),
    HALF_MONTH_SETTLED("半月结"),
    MONTH_SETTLED("月结"),
    ;

    private final String description;
}
