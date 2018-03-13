package com.yatang.sc.facade.dto;

import com.yatang.sc.facade.common.BaseDto;

import java.io.Serializable;

public class StorePermissionDto implements Serializable {

    private static final long serialVersionUID = 4983768611513702719L;

    private Integer id;

    private String franchiseeId;

    private String storeId;

    private Integer accessibled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getAccessibled() {
        return accessibled;
    }

    public void setAccessibled(Integer accessibled) {
        this.accessibled = accessibled;
    }

    public boolean isAccessible(){
        if (this.accessibled!=null && this.accessibled == 0){
            return true;
        }else{
            return false;
        }
    }
}
