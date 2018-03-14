package com.yatang.sc.order.states;

public enum CancelOrderStates {
    QXZ("QXZ", "取消中"),

    QXSB("QXSB", "取消失败"),

    QXCG("QXCG", "取消成功");

    private final String stateValue;

    private final String description;

    CancelOrderStates(String stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    public String getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }
}
