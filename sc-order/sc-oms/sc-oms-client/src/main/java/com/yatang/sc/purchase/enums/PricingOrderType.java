package com.yatang.sc.purchase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by qiugang on 7/10/2017.
 */

public enum PricingOrderType {
    AMOUNT("AMOUNT"),
    TOTAL("TOTAL"),
            ;

    private final String type;

    PricingOrderType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
