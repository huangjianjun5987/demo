package com.yatang.sc.facade.enums;

import com.busi.common.utils.StringUtils;


/**
 * 直销 销售订单通知notify
 */
public enum PmOrderMessageType {

    KIDD_ORDER_STATUS_NOTIFY("KiddOrderStatusNotify"),
    UNKNOWN("UNKNOWN");

    PmOrderMessageType(String type) {
        this.type = type;
    }

    public boolean equals(PmOrderMessageType type) {
        return this.type.equals(type.getType());
    }

    private String type;

    public String getType() {
        return this.type;
    }

    public static PmOrderMessageType parse(String msgVal) {
        if (StringUtils.isEmpty(msgVal)) {
            return UNKNOWN;
        }
        for (PmOrderMessageType type : PmOrderMessageType.values()) {
            if (type.type.equalsIgnoreCase(msgVal)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
