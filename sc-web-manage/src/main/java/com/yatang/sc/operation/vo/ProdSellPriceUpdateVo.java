package com.yatang.sc.operation.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 售价变更导入列表VO
 */
@Data
public class ProdSellPriceUpdateVo {

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

    /**区间起始数量*/
    private Integer	startNumber;

    /**区间结束数量*/
    private Integer endNumber;

    /**最新售价*/
    private BigDecimal newestPrice;

    /**子公司名称*/
    private String	branchCompanyName;

}
