package com.yatang.sc.order.states;

/**
 * 配送状态
 */

public enum ShippingGroupStates {
    INITIAL((short) 0, "初始"),
    ;
    ShippingGroupStates(Short stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    private final Short stateValue;
    private final String description;

    public Short getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }
}
