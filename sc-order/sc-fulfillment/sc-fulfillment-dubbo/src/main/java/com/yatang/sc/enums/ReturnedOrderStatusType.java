package com.yatang.sc.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @描述: 退换货单状态流枚举
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/20 17:19
 * @版本: v1.0
 */
public enum ReturnedOrderStatusType {


    RETURN_COMPLETED( "FULFILLED", "退货已完成" ),
    EXCHANGE_DELIVERED( "DELIVERED", "仓库已发货" ),
    EXCHANGE_COMPLETED( "SIGNED", "换货已完成" ),//todo
    UNKNOWN( "UNKNOWN", "未知状态" );


    private String code;
    private String desc;

    ReturnedOrderStatusType(String pCode, String pDesc) {
        code = pCode;
        desc = pDesc;
    }

    public static ReturnedOrderStatusType parse(String code) {
        if (StringUtils.isEmpty( code )) {
            return UNKNOWN;
        }
        if (RETURN_COMPLETED.code.equals( code )) {
            return RETURN_COMPLETED;
        }
        if (EXCHANGE_DELIVERED.code.equals( code )) {
            return EXCHANGE_DELIVERED;
        }
        if (EXCHANGE_COMPLETED.code.equals( code )) {
            return EXCHANGE_COMPLETED;
        }
        return UNKNOWN;
    }


}
