package com.yatang.sc.facade.dto.pm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @描述: 商品信息prodDto类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/1 20:58
 * @版本: v1.0
 */
@Setter
@Getter
@ToString
public class PmProductDto implements Serializable {


    private static final long serialVersionUID = 420662603169433859L;
    private String id;                        // 商品id
    private String name;                    // 商品名称
    private Integer invoiceLimit;            // 是否限制开增值税发票(0,不限制,1.限制)

    private String secondLevelCategoryName;//二级商品分类名

    private String saleName;

    private BigDecimal inputTaxRate;            // 进项税率

    private String brandName;                // 品牌名

    private Integer purchaseInsideNumber;//采购内装数

    private String productCode;  // 商品编码


    private String packingSpecifications;//规格

    private String producePlace;//产地 todo 产地不清楚


    private String unitExplanation;//包装单位参考值

    // 最小销售单位
    private String							minUnit;


    private BigDecimal purchasePrice;//采购价格（含税）

    //新加字段----吕和平
    // 第四级类别
    private String fourthCategoryId;

    // 一级商品分类id
    private String firstLevelCategoryId;

    // 二级商品分类id
    private String secondLevelCategoryId;

    // 三级商品分类id
    private String thirdLevelCategoryId;






}
