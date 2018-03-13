package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @描述: 商品采购关系ExtPo实体
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/17 11:19
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdPurchaseExtPo implements Serializable{


    private static final long serialVersionUID = -8028335001874774481L;
    private Long id;//pk

    private String spId;//供应商id

    private String spName;//供应商名称

    private String spAdrId;//供应商地点id

    private String spAdrCode;//供应商编码

    private String spAdrName;//供应商地点名称

    private String spNo;//供应编码

    private String productId;//产品id

    private String branchCompanyId;//子公司code(平台)

    private Integer supplierType;//供应商类型:0：一般供应商,1:主供应商

    private Integer purchaseInsideNumber;//采购内装数(默认箱规)

    private BigDecimal purchasePrice;//采购价格

    private String internationalCode;//条码(国际码)

    private Long distributeWarehouseId;//配送仓库id

    private String distributeWarehouseName;//配送仓库名称

    private Integer status;//采购关系的状态:0,,失效,1启用

    private Integer deleteStatus;//删除状态:0,,未删除,1启删除

    private Date createTime;

    private String createUserId;

    private Date modifyTime;

    private String modifyUserId;


    private String failedReason;

    private String auditUserId;//审核人ID

    private Date auditTime;//审核日期

    private Integer auditStatus;//审核状态:1:已提交;2:已审核;3:已拒绝


    private BigDecimal newestPrice;//最新价格

    private BigDecimal percentage;//调价百分比

    private Integer firstCreated;//第一次创建使用:1:是;0:否



    private Integer supportReturn;//采购退货 1支持,0不支持



}
