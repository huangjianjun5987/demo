package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述:商品 采购，销售价格是否首次创建
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/7 10:37
 * @版本: v1.0
 */

@Getter
@AllArgsConstructor
public enum ProdPriceCreateStatus {

    FIRST_CREATE_YES(1, "首次创建"),

    FIRST_CREATE_NO(0, "更新");


    private final Integer code;

    private final String desc;
}
