package com.yatang.sc.operation.vo.pm;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @描述:商品采购item查询vo
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 14:55
 * @版本: v1.0
 */
@Setter
@Getter
public class PmPurchaseOrderItemQueryParamVo implements Serializable {
    private static final long serialVersionUID = -119835281890053458L;

    @NotBlank(message = "{msg.notEmpty.message}")
    private String productId;//商品id

    @NotBlank(message = "{msg.notEmpty.message}")
    private String spAdrId;//供应商地点id

    private String spId;//供应商id
}
