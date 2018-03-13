package com.yatang.sc.facade.domain;


import com.yatang.sc.facade.common.BasePo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 商品采购关系查询条件Dto类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/17 10:56
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdPurchaseQueryParamPo extends BasePo implements Serializable{

    private static final long serialVersionUID = -1705812234334610093L;
    private String spId;//供应商id

    private String spAdrId;//供应商地点id

    private String branchCompanyId;//子公司code(平台)

    private Integer supplierType;//供应商类型:0：一般供应商,1:主供应商

    private Integer status;//采购关系的状态:0,,失效,1启用

    private String productId;//商品id


    private Integer auditStatus;//审核状态 1:已提交;2:已审核;3:已拒绝

    /**
     * 权限修改：子公司账号也可以有多个子公司的权限:yinyuxin
     */
    private List<String> branchCompanyIds;

}
