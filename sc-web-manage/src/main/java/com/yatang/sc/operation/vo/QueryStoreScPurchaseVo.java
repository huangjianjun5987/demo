package com.yatang.sc.operation.vo;

import java.io.Serializable;

public class QueryStoreScPurchaseVo implements Serializable {

    private static final long serialVersionUID = -201802168445646075L;

    /** 门店编号 */
    private String				storeId;

    /** 门店名称 */
    private String				storeName;

    /** 加盟商编号 */
    private String				franchiseeId;

    /** 加盟商名称 */
    private String				franchinessName;

    /** 所属子公司 */
    private String				branchCompanyId;

    /** 是否支持供应链采购(0 不支持 1 支持) */
    private Integer				scPurchaseFlag;

    private Integer				pageNo;

    private Integer				pageSize;

    private Integer				totalCount;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getFranchinessName() {
        return franchinessName;
    }

    public void setFranchinessName(String franchinessName) {
        this.franchinessName = franchinessName;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public Integer getScPurchaseFlag() {
        return scPurchaseFlag;
    }

    public void setScPurchaseFlag(Integer scPurchaseFlag) {
        this.scPurchaseFlag = scPurchaseFlag;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
