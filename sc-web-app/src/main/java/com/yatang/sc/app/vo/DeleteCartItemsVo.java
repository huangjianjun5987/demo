package com.yatang.sc.app.vo;

import java.io.Serializable;

public class DeleteCartItemsVo implements Serializable {

    private static final long serialVersionUID = 3291358326205803828L;

    private String skuIds;

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }
}
