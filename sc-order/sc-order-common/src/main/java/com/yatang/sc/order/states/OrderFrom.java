package com.yatang.sc.order.states;

/**
 * 销售订单产生来源
 */
public enum OrderFrom {
    DEFAULT("default", "下单"),
    SHIPPING_MODE_SPLIT("modeSp", "配送模式拆单"),
    ASN_SPLIT("asnSp", "ASN拆单"),
    MANUAL_SPLIT("manualSp", "手动拆单拆单"),
    INVENTORY_SPLIT("inventorySp", "库存拆单");

    private String value;
    private String description;

    OrderFrom(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
