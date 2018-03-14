package com.yatang.sc.order.states;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiugang on 7/24/2017.
 */
public class PaymentStates {

    /**
     * 未支付
     */
    public static final String PENDING_PAYMENT = "WZF";
    /**
     * 已支付
     */
    public static final String DEBITED = "YZF";

    /**
     * 退款待审核
     */
    public static final String RF_PENDING_APPROVE = "TKD";
    /**
     * 退款待确认
     */
    public static final String RF_PENDING_CONFIRM = "TKQ";
    /**
     * 已退款
     */
    public static final String RF_COMPLETED = "YTK";

    /**
     * 取消付款
     */
    public static final String CANCELLED = "QXFK";

    /**
     * 公司内
     */
    public static final String GSN = "GSN";



    public static Map<String,String> paymentStateDesc = new HashMap<>();

    static{
        paymentStateDesc.put(PENDING_PAYMENT,"未支付");
        paymentStateDesc.put(DEBITED,"已支付");
        paymentStateDesc.put(CANCELLED,"取消付款");
        paymentStateDesc.put(RF_PENDING_APPROVE,"退款待审核");
        paymentStateDesc.put(RF_PENDING_CONFIRM,"退款待确认");
        paymentStateDesc.put(RF_COMPLETED,"已退款");
        paymentStateDesc.put(GSN,"公司内");
    }
}
