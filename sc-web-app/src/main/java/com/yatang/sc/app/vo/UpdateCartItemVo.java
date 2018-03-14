package com.yatang.sc.app.vo;

import java.io.Serializable;

public class UpdateCartItemVo implements Serializable {
    private static final long serialVersionUID = 572918518018397891L;

    private String skuId;

    private long quantity;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
