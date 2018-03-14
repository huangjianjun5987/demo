package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 9:22
 * @版本: v1.0
 */

public class OrderReturnRequestItemDto implements Serializable {


    private static final long serialVersionUID = -3117152188268974929L;
    private Long returnQuantity;//发生了退换货的数量

    private String productId;//产品id

    public Long getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Long returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
