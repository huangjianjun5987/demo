package com.yatang.sc.order.states;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiugang on 7/24/2017.
 */
public class ShippingStates {

    /**
     * 待处理
     */
    public static final String PENDING_PROCESS = "DCL";
    /**
     * 未传送
     */
    public static final String PENDING_TRANSFER = "WCS";
    /**
     * 待出库
     */
    public static final String PENDING_DELIVERY = "DCK";
    /**
     * 仓库拒收
     */
    public static final String WMS_REJECT = "WJS";
    /**
     * 待收货
     */
    public static final String PENDING_RECEIVE = "DSH";


    /**
     * 用户已拒签
     */
    public static final String USER_REJECT = "UJS";


    /**
     * 已签收待确认
     */
    public static final String PENDING_COMPLETED = "YQSDQR";
    /**
     * 已签收
     */
    public static final String COMPLETED = "YQS";
    /**
     * 未送达
     */
    public static final String NO_ARRIVED = "WSD";

    /**
     * 取消中
     */
    public static final String CANCELING = "QXZ";
    /**
     * 取消送货
     */
    public static final String CANNCELD = "QX";
    /**
     * 库存不足
     */
    public static final String NO_INVENTORY = "CGWDH";


    public static Map<String, String> shippingStateDesc = new HashMap<>();

    static {
        shippingStateDesc.put( PENDING_PROCESS, "待处理" );
        shippingStateDesc.put( PENDING_TRANSFER, "未传送" );
        shippingStateDesc.put( PENDING_DELIVERY, "待出库" );
        shippingStateDesc.put( WMS_REJECT, "仓库拒收" );
        shippingStateDesc.put( PENDING_RECEIVE, "待收货" );
        shippingStateDesc.put( COMPLETED, "已签收" );
        shippingStateDesc.put( PENDING_COMPLETED, "已签收待确认" );
        shippingStateDesc.put( USER_REJECT, "已拒签" );
        shippingStateDesc.put( NO_ARRIVED, "未送达" );
        shippingStateDesc.put( CANNCELD, "取消送货" );
        shippingStateDesc.put( NO_INVENTORY, "库存不足" );
    }

}
