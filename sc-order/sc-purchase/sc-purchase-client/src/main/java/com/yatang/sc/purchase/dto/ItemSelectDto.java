package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemSelectDto implements Serializable {

    private static final long serialVersionUID = 7155505040668848031L;

    private  String skuId;

    private  Integer select;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getSelect() {
        return select;
    }

    public void setSelect(Integer select) {
        this.select = select;
    }
}
