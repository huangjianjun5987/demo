package com.yatang.sc.order.states;

/**
 * 配送模式
 */
public enum ShippingModes {
    UNIFIED_SHIPPING_MODE("unified", "统配"), PROVIDER_SHIPPING_MODE("provider", "直送");

    private String code;
    private String desc;

    ShippingModes(String pCode, String pDesc) {
        this.code = pCode;
        this.desc = pDesc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
