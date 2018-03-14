package com.yatang.sc.purchase.dto;

import java.io.Serializable;

public class DirectStoreCommerItemDto implements Serializable{
    private static final long serialVersionUID = -993119128061672680L;

    private String productId;

    private long quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
