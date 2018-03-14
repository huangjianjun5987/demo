package com.yatang.sc.order.states;

import java.util.HashMap;
import java.util.Map;

public enum ReturnReasonTypes {
    PACKAGE_BREAKAGE((short) 1, "包装破损"),
    PRODUCT_BREAKAGE((short) 2, "商品破损"),
    EXPIRATION_DATE((short) 3, "保质期临期货过期"),
    PRODUCT_WRONG((short) 4, "商品错发或漏发"),
    BOOKING_WRONG((short) 5, "订错货"),
    PRODUCT_QUALITY_PROBLEM((short) 6, "商品质量问题"),
    OTHER((short) 7, "其他");

    private final short stateValue;

    private final String description;

    ReturnReasonTypes(short stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    public short getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }

    public final static Map<Short, String> returnReasonTypeDesc = new HashMap<>();

    static {
        for (ReturnReasonTypes returnReasonTypes : ReturnReasonTypes.values()) {
            returnReasonTypeDesc.put(returnReasonTypes.getStateValue(), returnReasonTypes.getDescription());
        }
    }
}
