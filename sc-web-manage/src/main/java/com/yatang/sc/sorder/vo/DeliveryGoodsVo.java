package com.yatang.sc.sorder.vo;

import java.io.Serializable;

/**
 * Created by liusongjie on 2017/7/28.
 */
public class DeliveryGoodsVo implements Serializable {

    private static final long serialVersionUID = 8205007627379458975L;
    /**
     * 商品id
     */
    private String id;

    /**
     * 配送数量
     */
    private Long shippedQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(Long shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }
}
