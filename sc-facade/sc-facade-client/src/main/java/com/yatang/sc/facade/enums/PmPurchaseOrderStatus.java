package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>@description:采购订单状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭</p>
 *
 * @author yangshuang
 * @version v1.0
 * @date 2018/1/9 11:28
 */
@Getter
@AllArgsConstructor
public enum PmPurchaseOrderStatus {

    /**
     * 采购订单流转状态
     */
    DRAFT_STATUS(0, "草稿状态"),
    UN_AUDIT_STATUS(1, "待审核"),
    AUDITED_STATUS(2, "已审核"),
    REJECT_STATUS(3, "已拒绝"),
    Close_STATUS(4, "已关闭"),
    UNKNOWN(-1, "未知");
    private final Integer code;

    private final String desc;

    public static PmPurchaseOrderStatus parse(Integer code) {
        for (PmPurchaseOrderStatus status : PmPurchaseOrderStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
