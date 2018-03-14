package com.yatang.sc.purchase.enums;


import java.util.HashMap;
import java.util.Map;

public class SaleChannel {

    public static final String DEFAULT = "DEFAULT";//供应链
    public static final String SCM = "SCM";//供应链后台系统
    public static final String DS = "DS";//电商
    public static final String XCC = "XCC";//小超

    private final static Map<String,String> saleChannel = new HashMap<>();

    static {
        saleChannel.put(DS,"电商");
        saleChannel.put(XCC,"小超");
        saleChannel.put(DEFAULT,"供应链");
        saleChannel.put(SCM,"供应链后台系统");
    }
}
