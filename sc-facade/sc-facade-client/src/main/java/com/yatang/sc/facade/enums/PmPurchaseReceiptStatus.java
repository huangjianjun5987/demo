package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>@description:收货单状态流转</p>
 *
 * @author yangshuang
 * @version v1.0
 * @date 2018/1/11 15:01
 */
@Getter
@AllArgsConstructor
public enum PmPurchaseReceiptStatus {

    //状态（0:待下发，1：已下发，2:已收货，3:已取消，4：异常）
    RECEIPT_FOR_DELIVERY_STATUS(0, "待下发"),
    RECEIPT_DELIVERED_STATUS(1, "已下发"),
    RECEIPT_RECEIVED_STATUS(2, "已收货"),
    RECEIPT_CANCELED_STATUS(3, "已取消"),
    RECEIPT_EXCEPTION_STATUS(4, "异常"),
    UNKNOWN(-1, "未知");
    private final Integer code;

    private final String desc;

    public static PmPurchaseReceiptStatus parse(Integer code) {
        for (PmPurchaseReceiptStatus status : PmPurchaseReceiptStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
