package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/8.
 * 支付方式枚举
 */
public enum PayWayCode implements BaseEnum<PayWayCode, String> {

    SCAN_QR_CODE("SCAN_QR", "扫描支付"),
    APP_PAY("APP", "APP支付"),
    WAP_PAY("WAP", "Wap支付"),
    PC_PAY("PC", "PC支付");

    private String mCode;
    private String mName;

    PayWayCode(String pCode, String pName) {
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
