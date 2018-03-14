package com.yatang.sc.app.vo;

import lombok.Data;

import java.io.Serializable;


public class ItemSelectVo implements Serializable {

    private static final long serialVersionUID = 4895092590137109444L;

    private String skuId;

    private Integer select;

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
