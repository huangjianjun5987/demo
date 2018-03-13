package com.yatang.sc.xinyi.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求dto<br>
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class ProductSynchronizeRequestDto implements java.io.Serializable {

    private String actionType;                  //操作类型 add|update
    private String warehouseCode;               //仓库编码
    private String ownerCode;                   //货主编码
    private String supplierCode;                //供应商编码
    private String supplierName;                //供应商名称
    private ProductSynchronizeItemDto item;     //商品项
}
