package com.yatang.sc.settlement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 收货地点类型
 * @作者: tankejia
 * @创建时间: 2017/8/31-16:19 .
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum AdrType {
    STOCK("仓库"),
    STORE("门店"),
    ;

    private final String description;
}
