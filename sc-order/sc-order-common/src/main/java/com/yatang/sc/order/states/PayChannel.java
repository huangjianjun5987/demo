package com.yatang.sc.order.states;

import java.util.HashMap;
import java.util.Map;

public class PayChannel {


    /**
     * 支付宝支付
     */
    public static final String ALIPAY = "alipay";

    /**
     * 支付宝支付
     */
    public static final String WEI_XIN = "weixin";

    public static Map<String,String> payChannelDesc = new HashMap<>();

    static{
        payChannelDesc.put(ALIPAY,"支付宝支付");
        payChannelDesc.put(WEI_XIN,"微信支付");
    }
}
