package com.yatang.sc.order.states;

/**
 * @描述: 商品收货状态
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/23 11:06
 * @版本: v1.0
 */
public enum OrderReturnedItemStates {


    PART_COMPLETE((short) 1, "部分收货"),
    FULL_COMPLETE((short) 2, "全部收货"),
    UN_COMPLETE((short) 3, "待收货");

    private final short stateValue;
    private final String description;

    OrderReturnedItemStates(short stateValue, String description) {
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
