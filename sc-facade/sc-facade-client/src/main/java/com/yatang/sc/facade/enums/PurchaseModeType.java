package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 采购模式(统采,地采)
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/28 13:57
 * @版本: v1.0
 */
@Getter
@AllArgsConstructor
public enum PurchaseModeType {

    UNIFY_PUARHCASE(0, "地采"),

    LOCAL_PUARCHASE(1, "统采");


    private final Integer code;

    private final String desc;


}
