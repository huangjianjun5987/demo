package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述:  价格变动类型(销售,采购)
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 15:59
 * @版本: v1.0
 */

@Getter
@AllArgsConstructor
public enum ProdPriceChangeType {


    PURCHASE_PRICE_TYPE(0,"采购进价变更"),

    SALE_PRICE_TYPE(1,"售价变更");


    private final Integer code;

    private final String desc;
}
