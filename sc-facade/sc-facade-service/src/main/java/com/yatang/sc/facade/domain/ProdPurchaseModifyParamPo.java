package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 商品采购关系变更参数(修改启禁用状态, 删除)
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/18 9:38
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdPurchaseModifyParamPo implements Serializable {


    private static final long serialVersionUID = -3558692666559693227L;
    private Long id;//pk

    private String productId;//商品id
    private String modifyUserId;// 修改人id

    private Date modifyDateTime;//修改时间

    private Integer status;//采购关系的状态:0,,失效,1启用

    private Integer deleteStatus;//删除状态:0,,未删除,1启删除

    private Integer supplierType;//供应商类型:0：一般供应商,1:主供应商

    private String branchCompanyId;//分公司编号

    private List<String> productIdList;//商品idList
}
