package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @描述: 采购类型枚举类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/28 10:14
 * @版本: v1.0
 */
@Getter
@AllArgsConstructor
public enum PmPurchaseOrderType {

    NORMAL_PURCHASE(0, "普通采购"),
    GIFT_PURCHASE(1, "赠品采购"),
    PROMOTE_PURCHASE(2, "促销采购"),
    UNKNOWN(-1, "未知");


    private final Integer code;

    private final String desc;


    public static PmPurchaseOrderType parse(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }
        if (NORMAL_PURCHASE.code.equals(code)) {
            return NORMAL_PURCHASE;
        }
        if (GIFT_PURCHASE.code.equals(code)) {
            return GIFT_PURCHASE;
        }
        if (PROMOTE_PURCHASE.code.equals(code)) {
            return PROMOTE_PURCHASE;
        }
        return UNKNOWN;
    }


}
