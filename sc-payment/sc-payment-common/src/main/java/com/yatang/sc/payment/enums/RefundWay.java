package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/8.
 * 退款方式
 */
public enum RefundWay implements BaseEnum<PayWayStatus, String> {
    ORIGINAL_REFUND("ORIGINAL_REFUND", "原路返回"),
    OFFLINE_REFUND("OFFLINE_REFUND", "线下转账");

    private String mCode;
    private String mName;

    RefundWay(String pCode, String pName) {
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
