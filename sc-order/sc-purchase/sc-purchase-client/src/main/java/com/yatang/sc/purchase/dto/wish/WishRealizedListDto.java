package com.yatang.sc.purchase.dto.wish;

import java.io.Serializable;

public class WishRealizedListDto implements Serializable{

    private static final long serialVersionUID = -1329220113981267590L;

    private String branchCompanyId;
    private String wareHouseCode;
    private String franchiserId;

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getFranchiserId() {
        return franchiserId;
    }

    public void setFranchiserId(String franchiserId) {
        this.franchiserId = franchiserId;
    }

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }
}
