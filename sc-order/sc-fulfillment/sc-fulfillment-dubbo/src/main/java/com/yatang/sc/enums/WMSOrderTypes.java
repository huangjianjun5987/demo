package com.yatang.sc.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by xiangyonghong on 2017/8/7.
 */
public enum WMSOrderTypes {
    FHCK("FHCK","发货出库"),
    XSCK("XSCK","销售出库"),
    UNKNOWN("UNKNOWN", "未知状态")
    ;

    private String code;
    private String desc;


    WMSOrderTypes(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WMSOrderTypes parse(String code){
        if (StringUtils.isEmpty(code)) {
            return UNKNOWN;
        }
        if (FHCK.code.equals(code)) {
            return FHCK;
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
