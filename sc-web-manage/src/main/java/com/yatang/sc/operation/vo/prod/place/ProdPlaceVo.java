package com.yatang.sc.operation.vo.prod.place;

/*
*@Author tangqi
*@Date 2018/1/15 14:58
*@Desc
*/
public class ProdPlaceVo {

    private String id;

    private String firstLevelCategoryName;

    private String secondLevelCategoryName;

    private String thirdLevelCategoryName;

    private String fourthLevelCategoryName;

    private String brand;

    private String productCode;

    private String productName;

    private String branchCompanyId;

    private String branchCompanyName;

    private String storeCode;

    private String storeName;

    private String supplierId;

    private String supplierCode;

    private String supplierName;

    private String adrSupId;

    private String adrSupCode;

    private String adrSupName;

    private Integer logisticsModel;

    private Integer placeType;

    private String placeId;

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getAdrSupId() {
        return adrSupId;
    }

    public void setAdrSupId(String adrSupId) {
        this.adrSupId = adrSupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public String getThirdLevelCategoryName() {
        return thirdLevelCategoryName;
    }

    public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
        this.thirdLevelCategoryName = thirdLevelCategoryName;
    }

    public String getFourthLevelCategoryName() {
        return fourthLevelCategoryName;
    }

    public void setFourthLevelCategoryName(String fourthLevelCategoryName) {
        this.fourthLevelCategoryName = fourthLevelCategoryName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAdrSupCode() {
        return adrSupCode;
    }

    public void setAdrSupCode(String adrSupCode) {
        this.adrSupCode = adrSupCode;
    }

    public String getAdrSupName() {
        return adrSupName;
    }

    public void setAdrSupName(String adrSupName) {
        this.adrSupName = adrSupName;
    }

    public Integer getLogisticsModel() {
        return logisticsModel;
    }

    public void setLogisticsModel(Integer logisticsModel) {
        this.logisticsModel = logisticsModel;
    }

    @Override
    public String toString() {
        return "ProdPlaceVo{" +
                "id='" + id + '\'' +
                ", firstLevelCategoryName='" + firstLevelCategoryName + '\'' +
                ", secondLevelCategoryName='" + secondLevelCategoryName + '\'' +
                ", thirdLevelCategoryName='" + thirdLevelCategoryName + '\'' +
                ", fourthLevelCategoryName='" + fourthLevelCategoryName + '\'' +
                ", brand='" + brand + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", branchCompanyId='" + branchCompanyId + '\'' +
                ", branchCompanyName='" + branchCompanyName + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", adrSupId='" + adrSupId + '\'' +
                ", adrSupCode='" + adrSupCode + '\'' +
                ", adrSupName='" + adrSupName + '\'' +
                ", logisticsModel=" + logisticsModel +
                ", placeType=" + placeType +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
