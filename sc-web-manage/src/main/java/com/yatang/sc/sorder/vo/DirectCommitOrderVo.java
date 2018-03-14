package com.yatang.sc.sorder.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

public class DirectCommitOrderVo implements Serializable{
    private static final long serialVersionUID = -9112248766315540676L;

    @NotBlank(message = "{msg.notEmpty.message}")
    private String storeId;
    private List<DirectCommitItemVO> directStoreCommerItemList;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<DirectCommitItemVO> getDirectStoreCommerItemList() {
        return directStoreCommerItemList;
    }

    public void setDirectStoreCommerItemList(List<DirectCommitItemVO> directStoreCommerItemList) {
        this.directStoreCommerItemList = directStoreCommerItemList;
    }
}
