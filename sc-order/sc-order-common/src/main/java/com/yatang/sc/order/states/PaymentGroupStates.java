package com.yatang.sc.order.states;

/**
 * 付款状态
 */
public enum PaymentGroupStates {
    INITIAL((short)0, "初始"),
    AUTHORIZED((short)1,"已授权"),
    SETTLED((short)2,"已付款"),
    GSN((short)3,"公司内"),
    ;

    PaymentGroupStates(Short stateValue, String description) {
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
