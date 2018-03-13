package com.yatang.sc.glink.dto.im;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @描述:库存查询Dto
 * @类名:ImAdjuestmentParamentDto
 * @作者: yipeng
 * @创建时间: 2017-09-21 16:34:29
 * @版本: v1.0
 */
@Setter
@Getter
@ToString
public class InventoryQueryRequestDto implements java.io.Serializable {
    private static final long serialVersionUID = -6638727707669964246L;

    private String itemCode;                                    // 查询开始时间

}
