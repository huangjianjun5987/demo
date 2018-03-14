package com.yatang.sc.dto;

import java.io.Serializable;

/**
 * Created by liusongjie on 2017/7/28.
 */
public class DeliveryGoodsDto implements Serializable {

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
