package com.yatang.sc.order.states;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiugang on 7/24/2017.
 */
public class OrderStates {

    /**
     * 待审核
     */
    public static final String PENDING_APPROVE = "W";

    /**
     * 待人工审核
     */
    public static final String PENDING_MANUAL_APPROVE = "M";

    /**
     * 已审核
     */
    public static final String APPROVED = "A";

    /**
     * 已取消
     */
    public static final String CANCELLED = "Q";

    /**
     * 已完成
     */
    public static final String COMPLETED = "C";

    /**
     * 已拆单
     */
    public static final String SPLITED_ORDER = "SP";

    public static Map<String, String> orderStateDesc = new HashMap<>();

    static {
        orderStateDesc = new HashMap<>();
        orderStateDesc.put( PENDING_APPROVE, "待审核" );
        orderStateDesc.put( PENDING_MANUAL_APPROVE, "待人工审核" );
        orderStateDesc.put( APPROVED, "已审核" );
        orderStateDesc.put( CANCELLED, "已取消" );
        orderStateDesc.put( COMPLETED, "已完成" );
        orderStateDesc.put( SPLITED_ORDER, "已拆单" );
    }

}
