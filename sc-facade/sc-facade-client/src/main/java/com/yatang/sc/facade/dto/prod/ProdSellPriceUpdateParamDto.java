package com.yatang.sc.facade.dto.prod;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProdSellPriceUpdateParamDto implements Serializable{

    private static final long serialVersionUID = 7151728923952620454L;

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

    /**用户ID*/
    private String userId;

    /**每页显示条数*/
    private Integer	pageSize;

    /**页码*/
    private Integer	pageNum;

}
