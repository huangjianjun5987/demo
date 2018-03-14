package com.yatang.sc.order.states;

public enum CommerceItemStatus {

    INIT((short) 10, "初始化"),
    STOCK_ENOUGH((short) 20, "库存充足"),
    STOCK_NOT_ENOUGH((short) 30, "库存不足");

    private final short stateValue;

    private final String description;

    CommerceItemStatus(short stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    public short getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }
}
