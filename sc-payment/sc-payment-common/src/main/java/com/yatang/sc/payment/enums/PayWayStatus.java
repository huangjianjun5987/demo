package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/8.
 * 支付方式状态
 */
public enum PayWayStatus implements BaseEnum<PayWayStatus, String> {

    NORMAL("normal", "正常"),
    DISABLE("disabled", "禁用"),
    DELETED("deleted", "删除");

    private String mCode;
    private String mName;

    PayWayStatus(String pCode, String pName) {
        this.mCode = pCode;
        this.mName = pName;

    }

    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }
}
