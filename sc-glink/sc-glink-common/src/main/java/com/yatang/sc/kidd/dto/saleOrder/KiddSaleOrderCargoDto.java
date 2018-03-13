package com.yatang.sc.kidd.dto.saleOrder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 销售单商品信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 11:52
 * @版本: v1.0
 */
@Getter
@Setter
public class KiddSaleOrderCargoDto implements Serializable{

    private static final long serialVersionUID = -2792502235508112174L;
    private String orderLineNo;////商品行号
    private String ownerCode;//商品名称 货主编码 子公司ID

    private String itemCode;//商品编码

    private String itemName;//商品名称

    private Integer planQty;//应发商品数量

    private String inventoryType;//库存类型


    /*******************************glink字段****************************************/
    private String unit;//规格单位

    private double retailPrice;//零售价

    private double actualPrice;//实际成交价

    private double discountAmount;//单件商品折扣金额

    private String prodPrice;//优惠前商品行小计

}
