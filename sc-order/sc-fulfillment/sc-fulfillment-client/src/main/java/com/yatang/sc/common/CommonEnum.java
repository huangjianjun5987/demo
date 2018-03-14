package com.yatang.sc.common;

public enum CommonEnum {
    ORDER_NONEXIST("60001", "订单不存在"), ORDER_STATUS_INVALID("60002", "订单状态不正确");
    private String code;
    private String desc;

    CommonEnum(String pCode, String pDesc) {
        code = pCode;
        desc = pDesc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
