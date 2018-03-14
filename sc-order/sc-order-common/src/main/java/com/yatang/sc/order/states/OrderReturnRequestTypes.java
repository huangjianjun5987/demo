package com.yatang.sc.order.states;

import org.apache.commons.lang3.StringUtils;

/**
 * @描述: 订单退换货单类型
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/18 14:05
 * @版本: v1.0
 */
public enum OrderReturnRequestTypes {


    //退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)
//    ORDER_RETURNED("THRK", "退货"),

    NORMAL_RETURN( "ZCTH", "正常退货" ),

    NORMAL_EXCHANGE( "ZCHH", "正常换货" ),

    REJECT_RETURN( "JSTH", "拒收退货" ),

    ORDER_UNKNOWN( "UNKNOWN", "未知状态" );

    private final String stateValue;

    private final String description;

    OrderReturnRequestTypes(String stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    public String getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }

    public static OrderReturnRequestTypes parse(String stateValue) {
        if (StringUtils.isEmpty( stateValue )) {
            return ORDER_UNKNOWN;
        }
        if (NORMAL_RETURN.stateValue.equals( stateValue )) {
            return NORMAL_RETURN;
        }
        if (NORMAL_EXCHANGE.getStateValue().equals( stateValue )) {
            return NORMAL_EXCHANGE;
        }
        if (REJECT_RETURN.getStateValue().equals( stateValue )) {
            return REJECT_RETURN;
        }
        return ORDER_UNKNOWN;
    }


}
