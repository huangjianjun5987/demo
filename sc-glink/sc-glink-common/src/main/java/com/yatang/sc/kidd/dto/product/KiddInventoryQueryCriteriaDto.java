package com.yatang.sc.kidd.dto.product;

import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @描述:库存查询Dto
 * @作者: yipeng
 * @创建时间: 2017-09-21 16:34:29
 * @版本: v1.0
 */
@Setter
@Getter
@ToString
@XStreamAliasType("criteria")
public class KiddInventoryQueryCriteriaDto implements java.io.Serializable {

    private static final long serialVersionUID = -6638727707669964246L;

    private String warehouseCode;       // 仓库编码
    private String ownerCode;           // 货主编码
    private String itemCode;            // 商品编码

}
