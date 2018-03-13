package com.yatang.sc.facade.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品采购进价变更导入列表查询条件
 */
@Data
public class ProdPurchasePriceUpdateParamPo {

    /**上传ID*/
    private Long importsId;

    /**供应商ID**/
    private String spId;

    /**供应商编码*/
    private String spNo;

    /**供应商地点ID*/
    private String spAdrId;

    /**供应商地点编码*/
    private String spAdrNo;

    /**商品ID*/
    private String productId;

    /**商品编码*/
    private String productNo;

    /**处理结果*/
    private Integer handleResult;

    /**上传开始日期*/
    private Date uploadStartDate;

    /**上传结束日期*/
    private Date uploadEndDate;

    /**用户ID*/
    private String userId;

    /**每页显示条数*/
    private Integer	pageSize;

    /**页码*/
    private Integer	pageNum;


}
