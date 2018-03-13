package com.yatang.sc.facade.domain;

import java.io.Serializable;

public class StorePermissionPo implements Serializable {
    private Integer id;

    private String franchiseeId;

    private String storeId;

    private Integer accessibled;

    private static final long serialVersionUID = 1L;

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
        this.franchiseeId = franchiseeId == null ? null : franchiseeId.trim();
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public Integer getAccessibled() {
        return accessibled;
    }

    public void setAccessibled(Integer accessibled) {
        this.accessibled = accessibled;
    }
}