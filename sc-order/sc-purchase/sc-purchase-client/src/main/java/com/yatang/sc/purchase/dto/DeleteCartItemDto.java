package com.yatang.sc.purchase.dto;

import java.io.Serializable;

public class DeleteCartItemDto implements Serializable {

    private static final long serialVersionUID = 2877037232351192504L;

    private  String skuId;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
