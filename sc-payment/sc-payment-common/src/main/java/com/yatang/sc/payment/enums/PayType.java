package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/8.
 * 支付类型枚举
 */
public enum PayType implements BaseEnum<PayType, String> {

    weixin("weixin", "微信支付"),
    alipay("alipay", "支付宝"),
    jd("jd", "京东支付"),
    cmb("cmb", "招商银行"),
    ytpay("ytpay","雅堂余额支付"),
    unionpay("unionpay","银联支付"),
    other("other","其他"),
    unknown("unknown", "未知支付类型");

    private String mCode;
    private String mName;

    PayType(String pCode, String pName) {
        this.mCode = pCode;
        this.mName = pName;

    }

    public static PayType parse(String pCode) {
        if (weixin.getCode().equals(pCode)) {
            return weixin;
        }
        if (alipay.getCode().equals(pCode)) {
            return alipay;
        }
        if (ytpay.getCode().equals(pCode)) {
            return ytpay;
        }
        if (jd.getCode().equals(pCode)) {
            return jd;
        }
        if (cmb.getCode().equals(pCode)) {
            return cmb;
        }
        if (other.getCode().equals(pCode)) {
            return other;
        }
        if (unionpay.getCode().equals(pCode)) {
            return unionpay;
        }
        return unknown;
    }

    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return "PayType{" +
                "mCode='" + mCode + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
