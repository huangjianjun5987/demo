package com.yatang.sc.facade.dto.prod;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProdSellPriceUpdateDto implements Serializable {

    private static final long serialVersionUID = -3554750664711381812L;

    /**上传ID*/
    private String uploadId;

    /**子公司ID*/
    private String branchCompanyId;

    /**商品编码*/
    private String productNo;

    /**销售数量区间*/
    private String interval;

    /**最新售价*/
    private BigDecimal price;

    /**行号*/
    private Long lineNum;

}
