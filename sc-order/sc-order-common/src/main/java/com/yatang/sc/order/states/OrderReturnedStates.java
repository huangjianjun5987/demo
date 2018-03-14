package com.yatang.sc.order.states;

/**
 * @描述: 退换货状态
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/18 13:32
 * @版本: v1.0
 */
public enum OrderReturnedStates {
    UNCONFIRMED((short) 1, "待确认"),

    CONFIRMED((short) 2, "已确认"),

    COMPLETED((short) 3, "已完成"),
    CANCELLED((short) 4, "已取消"),
    UNKNOWN((short) 0,"未知");

    private final short stateValue;
    private final String description;


    OrderReturnedStates(short stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    public short getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }




    public static OrderReturnedStates parse(short stateValue) {
        if (UNKNOWN.getStateValue()==stateValue) {
            return UNKNOWN;
        }
        if (UNCONFIRMED.stateValue== stateValue ) {
            return UNCONFIRMED;
        }

        if (CONFIRMED.stateValue== stateValue ) {
            return CONFIRMED;
        }
        if (COMPLETED.stateValue== stateValue ) {
            return COMPLETED;
        }
        if (CANCELLED.stateValue== stateValue ) {
            return CANCELLED;
        }
        return UNKNOWN;
    }

}
