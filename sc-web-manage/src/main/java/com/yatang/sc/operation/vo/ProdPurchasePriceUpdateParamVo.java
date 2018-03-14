package com.yatang.sc.operation.vo;

import lombok.Data;

import java.util.Date;

/**
 * 商品采购进价变更导入列表查询条件
 */
@Data
public class ProdPurchasePriceUpdateParamVo {

    /**上传ID*/
    private Long importsId;

    /**供应商ID**/
    private String spId;

    /**供应商地点ID*/
    private String spAdrId;

    /**商品ID*/
    private String productId;

    /**处理结果*/
    private Integer handleResult;

    /**上传开始日期*/
    private Date uploadStartDate;

    /**上传结束日期*/
    private Date uploadEndDate;

    /**每页显示条数*/
    private Integer	pageSize;

    /**页码*/
    private Integer	pageNum;


}
