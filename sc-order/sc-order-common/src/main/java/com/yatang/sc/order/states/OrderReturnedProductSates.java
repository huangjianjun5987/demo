package com.yatang.sc.order.states;

/**
 * @描述: 换货单商品状态
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/23 15:15
 * @版本: v1.0
 */
public enum OrderReturnedProductSates {

    UN_PICKED((short) 1, "待取货"),

    PICKED((short) 2, "已取货"),

    DELIVERED((short) 3, "已发货");

    private final short stateValue;
    private final String description;

    OrderReturnedProductSates(short stateValue, String description) {
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
