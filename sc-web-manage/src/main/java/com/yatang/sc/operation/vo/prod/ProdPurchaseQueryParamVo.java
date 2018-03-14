package com.yatang.sc.operation.vo.prod;

import com.yatang.sc.operation.common.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * @描述: 商品采购关系查询条件Vo类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/17 10:56
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdPurchaseQueryParamVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = -2641803326698836572L;
    private String spId;//供应商id

    private String spAdrId;//供应商地点id

    private String branchCompanyId;//子公司code(平台)

    @Range(min = 0,max = 1,message = "{msg.range.message}")
    private Integer supplierType;//供应商类型:0：一般供应商,1:主供应商

    @Range(min = 0,max = 1,message = "{msg.range.message}")
    private Integer status;//采购关系的状态:0,,失效,1启用
    @NotBlank(message = "{msg.notEmpty.message}")
    private String productId;//商品id

    @Range(min = 1,max = 3,message = "{msg.range.message}")
    private Integer auditStatus;//审核状态 1:已提交;2:已审核;3:已拒绝
}
