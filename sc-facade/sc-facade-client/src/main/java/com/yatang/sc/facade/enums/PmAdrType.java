package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>@description:采购单地点类型</p>
 *
 * @author yangshuang
 * @version v1.0
 * @date 2018/1/8 14:19
 */
@Getter
@AllArgsConstructor
public enum PmAdrType {

    // 地点类型:0:仓库;1:门店

    ADR_WAREHOUSE(0, "仓库"),
    ADR_DIRECT_STORE(1, "门店"),
    UNKNOWN(-1, "未知");

    private final Integer code;

    private final String desc;


    public static PmAdrType parse(Integer code) {
        for (PmAdrType status : PmAdrType.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return UNKNOWN;
    }


}
