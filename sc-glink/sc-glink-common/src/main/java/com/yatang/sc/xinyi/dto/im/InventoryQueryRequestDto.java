package com.yatang.sc.xinyi.dto.im;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * @描述:库存查询Dto
 * @作者: yipeng
 * @创建时间: 2017-09-21 16:34:29
 * @版本: v1.0
 */
@Setter
@Getter
@ToString
public class InventoryQueryRequestDto implements java.io.Serializable {

    private List<InventoryQueryCriteriaDto> criteriaList;

}
