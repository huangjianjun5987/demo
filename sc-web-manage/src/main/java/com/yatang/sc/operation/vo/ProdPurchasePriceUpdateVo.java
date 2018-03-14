package com.yatang.sc.operation.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品采购进价变更导入列表VO
 */
@Data
public class ProdPurchasePriceUpdateVo {

    /**上传ID*/
    private String importsId;

    /**行号*/
    private Integer lineNumber;

    /**处理结果*/
    private Integer handleResult;

    /**处理信息*/
    private String handleInformation;

    /**上传日期*/
    private Date uploadDate;

    /**商品编码*/
    private String productCode;

    /**商品信息*/
    private String productInformation;

    /**最新进价*/
    private BigDecimal newestPrice;

    /**供应商编码*/
    private String spNo;

    /**供应商名称*/
    private String spName;

    /**供应商地点编码*/
    private String spAdrNo;

    /**供应商地点名称*/
    private String spAdrName;

}
