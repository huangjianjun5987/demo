package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 采购价格审批属性封装
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/26 16:42
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdPurchasePriceAuditPo implements Serializable {


    private static final long serialVersionUID = 6819972184928689591L;
    private Long id;//pk


    private String spName;//供应商名称


    private String spAdrCode;//供应商地点编码

    private String spAdrName;//供应商地点名称

    private String spNo;//供应编码

    private String productId;//产品id

    private String productCode;//商品编码

    private String productName;//商品名称



    private Date createTime;


    private Date modifyTime;

    private Date auditTime;//审核日期

    private Integer auditStatus;//审核状态:1:已提交;2:已审核;3:已拒绝


    private Integer firstCreated;//第一次创建使用:1:是;0:否


    private Integer supportReturn;//采购退货 1支持,0不支持


}
