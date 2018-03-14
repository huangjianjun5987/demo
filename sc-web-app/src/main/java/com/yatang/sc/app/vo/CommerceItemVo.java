package com.yatang.sc.app.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class CommerceItemVo implements Serializable {

    private static final long serialVersionUID = -4196526815573622898L;

    /**
     * 产品id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 属性(颜色、尺寸等)
     */
    private Map<String, String> properties;

    /**
     *
     */
    private String skuId;
    /**
     * 下单时的数量
     */
    private long quantity;

    /**
     * 购物车商品售价
     */
    private double salePrice;

    /**
     * 是否选中
     */
    private boolean selected;


}
