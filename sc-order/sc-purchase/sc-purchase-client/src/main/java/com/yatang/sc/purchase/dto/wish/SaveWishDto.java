package com.yatang.sc.purchase.dto.wish;

import java.io.Serializable;
import java.util.List;

public class SaveWishDto implements Serializable{


    private static final long serialVersionUID = -2159136385447572977L;
    private String branchCompanyId;
    private String storeId;
    private String franchiserId;
    private String storeName;

    private List<SaveWishListsDto> saveWishListsDtoList;

    public List<SaveWishListsDto> getSaveWishListsDtoList() {
        return saveWishListsDtoList;
    }

    public void setSaveWishListsDtoList(List<SaveWishListsDto> saveWishListsDtoList) {
        this.saveWishListsDtoList = saveWishListsDtoList;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getFranchiserId() {
        return franchiserId;
    }

    public void setFranchiserId(String franchiserId) {
        this.franchiserId = franchiserId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}
