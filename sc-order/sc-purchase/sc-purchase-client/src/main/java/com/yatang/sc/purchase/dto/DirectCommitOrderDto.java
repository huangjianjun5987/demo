package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.List;

public class DirectCommitOrderDto implements Serializable{

    private static final long serialVersionUID = 2467566085790392697L;

    private String storeId;
    private List<DirectStoreCommerItemDto> directStoreCommerItemList;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<DirectStoreCommerItemDto> getDirectStoreCommerItemList() {
        return directStoreCommerItemList;
    }

    public void setDirectStoreCommerItemList(List<DirectStoreCommerItemDto> directStoreCommerItemList) {
        this.directStoreCommerItemList = directStoreCommerItemList;
    }
}
