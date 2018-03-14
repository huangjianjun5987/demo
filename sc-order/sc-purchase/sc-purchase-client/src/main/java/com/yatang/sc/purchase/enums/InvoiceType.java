package com.yatang.sc.purchase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by liusongjie on 2017/7/15.
 */
@Getter
@AllArgsConstructor
public enum InvoiceType {
    none("none"),
    valueAdded("value-added"),
    common("common");

    private final String value;
}
