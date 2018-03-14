package com.yatang.sc.purchase.dto.wish;

import java.io.Serializable;

public class WishListConditionDto implements Serializable{
    private static final long serialVersionUID = 2525630149278039533L;

    private int page;

    private int pageSize;

    private String franchiserId;

    private String branchCompanyId;
    private String wareHouseCode;



    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getFranchiserId() {
        return franchiserId;
    }

    public void setFranchiserId(String franchiserId) {
        this.franchiserId = franchiserId;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }
}
