package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/8.
 * <p>
 * 退款状态
 */
public enum RefundStatus implements BaseEnum<PayWayStatus, String> {

    REQUEST("pending_audit", "待审核"),

    APPROVED("approved", "审核通过"),
    REJECT("audit_reject", "审核拒绝"),

    REFUNDING("refunding", "退款中"),
    REFUNDED("has_refunded", "已退款"),
    REFUNDED_CONFIRM("refund_confirm", "退款确认"),
    REFUNDED_CANCELED("refund_canceled", "退款取消"),
    REFUND_FAILED("refund_fail", "退款失败"),

    CLOSED("closed", "请求关闭"),

    UNKNOWN("error", "未知状态");

    private String mCode;
    private String mName;

    RefundStatus(String pCode, String pName) {
        this.mCode = pCode;
        this.mName = pName;

    }

    public static RefundStatus parse(String pCode) {
        if (REQUEST.getCode().equals(pCode)) {
            return REQUEST;
        }
        if (APPROVED.getCode().equals(pCode)) {
            return APPROVED;
        }
        if (REJECT.getCode().equals(pCode)) {
            return REJECT;
        }
        if (REFUNDING.getCode().equals(pCode)) {
            return REFUNDING;
        }
        if (REFUNDED.getCode().equals(pCode)) {
            return REFUNDED;
        }
        if (REFUND_FAILED.getCode().equals(pCode)) {
            return REFUND_FAILED;
        }
        if (CLOSED.getCode().equals(pCode)) {
            return CLOSED;
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
