package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @描述: 商品采购/销售审核状态
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 10:57
 * @版本: v1.0
 */
@Getter
@AllArgsConstructor
public enum ProdAuditStatusType {


    //1:已提交;2:已审核;3:已拒绝
    AUDIT_SUBMIT(1, "已提交"),
    AUDIT_SUCCESS(2, "已审核"),
    AUDIT_REJECT(3, "已拒绝"),
    UNKNOWN(-1, "未知");


    private final Integer code;

    private final String desc;


    public static ProdAuditStatusType parse(Integer code) {
        for (ProdAuditStatusType type : ProdAuditStatusType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }

}
