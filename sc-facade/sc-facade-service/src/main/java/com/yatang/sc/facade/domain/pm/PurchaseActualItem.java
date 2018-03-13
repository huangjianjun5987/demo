package com.yatang.sc.facade.domain.pm;

import com.busi.mq.message.MQMsg;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 采购订单实际收货商品或采购退货实际退货商品
 * @作者: tankejia
 * @创建时间: 2017/12/8-11:05 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class PurchaseActualItem implements Serializable, MQMsg {

    private static final long serialVersionUID = -3436415252044267793L;

    /**
     * 商品类目编码（第四级分类）
     * */
    private String      goodsTypeNo;

    /**
     * 商品类目名称（第四级分类）
     * */
    private String      goodsType;

    /**
     * 商品编码
     * */
    private String      goodsNo;

    /**
     * 商品名称
     * */
    private String      goodsName;

    /**
     * 出入库数量(按斤卖的商品数量可能为小数)（退货时为负数）
     * */
    private String      inQty;

    /**
     * 进货价
     * */
    private Double      dealerprice;

    /**
     * 进货金额（含税）（退货时为负数）
     * */
    private Double      purchaseAmt;

    /**
     * 税率
     * */
    private Double      taxRate;

    /**
     * 税额 （退货时为负数）
     * */
    private Double      taxAmt;

    /**
     * 不含税金额 （退货时为负数）
     * */
    private Double      outTaxAmt;

}
