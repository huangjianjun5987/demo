package com.yatang.sc.payment.enums;

/**
 * Created by yuwei on 2017/7/14.
 */
public enum YTAccountType implements BaseEnum<YTAccountType, Integer> {
    STORE(1, "电商"), XC(2, "小超"), HOME(3, "到家");

    private Integer mCode;
    private String mName;

    YTAccountType(Integer pCode, String pName) {
        this.mCode = pCode;
        this.mName = pName;

    }

    public Integer getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }
}
