package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>@描述: 采购订单接单状态</p>
 *
 * @author yangshuang
 * @version v1.0
 * @date 2018/1/8 14:08
 */
@Getter
@AllArgsConstructor
public enum PmPurchaseOrderAcceptStatus {

    ORDER_UN_ACCEPT(0, "未接单"),
    ORDER_ACCEPT(1, "已接单"),
    UNKNOWN(-1, "未知");


    private final Integer code;

    private final String desc;


    public static PmPurchaseOrderAcceptStatus parse(Integer code) {

        for (PmPurchaseOrderAcceptStatus status : PmPurchaseOrderAcceptStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return UNKNOWN;
    }


}
