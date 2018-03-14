package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/13.
 */
public enum NotifyType implements BaseEnum<NotifyType, String> {

    SYNC("sync", "同步通知"),
    ASYNC("async", "异步通知"),
    UNKNOWN("unknown", "未知类型");

    private String mCode;

    private String mName;

    NotifyType(String pCode, String pName) {
        this.mCode = pCode;
        this.mName = pName;

    }

    public String getName() {
        return mName;
    }

    @Override
    public String getCode() {
        return mCode;
    }

    public static NotifyType parse(String pCode) {
        if (SYNC.getCode().equals(pCode)) {
            return SYNC;
        }
        if (ASYNC.getCode().equals(pCode)) {
            return ASYNC;
        }
        return UNKNOWN;
    }
}
