package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/10.
 * 支付状态
 */
public enum PayStatus implements BaseEnum<PayStatus, String> {

    REQUEST("init", "初始化"),

    PENDING("pending_pay", "待支付"),

    SUCCESS("pay_success", "支付成功"),

    FAILURE("pay_fail", "支付失败"),

    CANCELED("canceled", "取消支付"),

    ABNORMAL("abnormal", "异常状态"),


    UNKNOWN("error", "未知状态");

    private String mCode;
    private String mName;

    PayStatus(String pCode, String pName) {
        this.mCode = pCode;
        this.mName = pName;

    }

    public static PayStatus parse(String pCode) {
        if (pCode.equals(REQUEST.mCode)) {
            return REQUEST;
        }
        if (pCode.equals(REQUEST.mCode)) {
            return PENDING;
        }
        if (pCode.equals(REQUEST.mCode)) {
            return SUCCESS;
        }
        if (pCode.equals(REQUEST.mCode)) {
            return FAILURE;
        }
        if (pCode.equals(REQUEST.mCode)) {
            return CANCELED;
        }
        return UNKNOWN;

    }

    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }
}
