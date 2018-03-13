package com.yatang.sc.facade.dto.prod;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品采购进价变更DTO
 */
@Data
public class ProdPurchasePriceUpdateDto implements Serializable{

    private static final long serialVersionUID = 8986253294723331295L;

    /**上传ID*/
    private String uploadId;

    /**供应商编码*/
    private String supplierNo;

    /**供应商地点编码*/
    private String supplierAddressNo;

    /**商品编码*/
    private String productNo;

    /**最新采购进价*/
    private BigDecimal price;

    /**行号*/
    private Long lineNum;

}
