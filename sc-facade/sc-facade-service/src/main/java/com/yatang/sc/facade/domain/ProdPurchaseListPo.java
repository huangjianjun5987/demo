package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 商品采购关系 productId和List<ProdPurchaseExtPo>集合
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/24 17:59
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdPurchaseListPo implements Serializable{

    private static final long serialVersionUID = -6144793605276359440L;
    private String productId;//商品id

    private List<ProdPurchaseExtPo> prodPurchaseExtPos; //商品采购关系
}
