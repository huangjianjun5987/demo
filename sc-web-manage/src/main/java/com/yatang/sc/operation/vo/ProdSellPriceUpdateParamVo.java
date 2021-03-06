package com.yatang.sc.operation.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ProdSellPriceUpdateParamVo {

    /**上传ID*/
    private Long importsId;

    /**子公司ID*/
    private String branchCompanyId;

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
