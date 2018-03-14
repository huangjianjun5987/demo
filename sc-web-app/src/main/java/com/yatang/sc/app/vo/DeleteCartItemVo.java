package com.yatang.sc.app.vo;

import java.io.Serializable;

public class DeleteCartItemVo implements Serializable {
    private static final long serialVersionUID = -2415610016656760146L;
    private  String skuId;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
