package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/8.
 * 退款原因
 */
public enum RefundReason implements BaseEnum<RefundReason, String> {

    CANCEL_REFUND("cancelRefund", "订单取消"),

    RETURN_REFUND("returnRefund", "退货退款"),

    LESS_PAY_REFUND("lessPayRefund", "少付退款"),

    OVER_PAY_REFUND("overPayRefund", "超付退款"),

    PAY_STATUS_INCORRECT_REFUND("payOrderIncorrect", "待支付订单状态错误退款"),

    PAY_ORDER_NOT_EXIST_REFUND("payOrderNotExist", "支付订单不存在"),

    DUPLICATION_REFUND("duplicateRefund", "重复支付退款"),

    SYSTEM_ERROR_REFUND("systemError", "系统错误退款"),

    DELIVERY_GAP_REFUND("deliveryGapReturn", "配送差额返现"),

    REJECTION_REFUND("rejectionReturn", "拒收退款"),

    OTHRE_REFUDND("otherRefund", "其他原因退款");

    private String mCode;
    private String mName;

    RefundReason(String pCode, String pName) {
        this.mCode = pCode;
        this.mName = pName;

    }

    @Override
    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }
}
