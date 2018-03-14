package com.yatang.sc.order.states;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/8/1.
 */
public class OrderTypes {

    /**
     * 正常销售
     */
    public static final String NORMAL_SALE = "ZCXS";


    /**
     * 直营店要货
     */
    public static final String DIRECT_STORE = "ZYYH";

    /**
     * 虚拟商品销售
     */
    public static final String ELECTRONIC_ORDER = "XNSP";

    /**
     * 电商销售
     */
    public static final String ONLINE_RETAILERS_SALE = "DSXS";

    /**
     * 小超C端
     */
    public static final String XC_SALE = "XCC";

    public static final Map<String,String> orderTypeDesc = new HashMap<>();

    static{
        orderTypeDesc.put(NORMAL_SALE,"正常销售");
        orderTypeDesc.put(DIRECT_STORE,"直营店要货");
        orderTypeDesc.put(ELECTRONIC_ORDER,"虚拟商品销售");
        orderTypeDesc.put(ONLINE_RETAILERS_SALE,"电商销售");
        orderTypeDesc.put(XC_SALE,"小超C端");
    }
}
