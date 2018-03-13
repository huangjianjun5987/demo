package com.yatang.sc.facade.domain.prodplace;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/11 14:01
*@Desc 商品地点关系
*/
@Document(collection = "ProdPlacePo")
public class ProdPlacePo {

    @Id
    private String id;

    private Integer placeType;

    private String placeId;

    private String productId;

    private String productCode;

    private String productName;

    private String brand;

    private String firstLevelCategoryId;

    private String firstLevelCategoryName;

    private String secondLevelCategoryId;

    private String secondLevelCategoryName;

    private String thirdLevelCategoryId;

    private String thirdLevelCategoryName;

    private String fourthLevelCategoryId;

    private String fourthLevelCategoryName;

    private List<String> internationalCodes;

    private String branchCompanyId;

    private String branchCompanyName;

    private String storeId;

    private String storeCode;

    private String storeName;

    private String supplierId;

    private String supplierCode;

    private String supplierName;

    private String adrSupId;

    private String adrSupCode;

    private String adrSupName;

    private Integer logisticsModel;//0:直送，1:配送

    private Date createDate;

    private Date lastModifyDate;

    private String createUserId;

    private String lastModifyUserId;

    private Date auditDate;

    private String auditUserId;

    public List<String> getInternationalCodes() {
        return internationalCodes;
    }

    public void setInternationalCodes(List<String> internationalCodes) {
        this.internationalCodes = internationalCodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFirstLevelCategoryId() {
        return firstLevelCategoryId;
    }

    public void setFirstLevelCategoryId(String firstLevelCategoryId) {
        this.firstLevelCategoryId = firstLevelCategoryId;
    }

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public String getSecondLevelCategoryId() {
        return secondLevelCategoryId;
    }

    public void setSecondLevelCategoryId(String secondLevelCategoryId) {
        this.secondLevelCategoryId = secondLevelCategoryId;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public String getThirdLevelCategoryId() {
        return thirdLevelCategoryId;
    }

    public void setThirdLevelCategoryId(String thirdLevelCategoryId) {
        this.thirdLevelCategoryId = thirdLevelCategoryId;
    }

    public String getThirdLevelCategoryName() {
        return thirdLevelCategoryName;
    }

    public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
        this.thirdLevelCategoryName = thirdLevelCategoryName;
    }

    public String getFourthLevelCategoryId() {
        return fourthLevelCategoryId;
    }

    public void setFourthLevelCategoryId(String fourthLevelCategoryId) {
        this.fourthLevelCategoryId = fourthLevelCategoryId;
    }

    public String getFourthLevelCategoryName() {
        return fourthLevelCategoryName;
    }

    public void setFourthLevelCategoryName(String fourthLevelCategoryName) {
        this.fourthLevelCategoryName = fourthLevelCategoryName;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getLastModifyUserId() {
        return lastModifyUserId;
    }

    public void setLastModifyUserId(String lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    @Override
    public String toString() {
        return "ProdPlacePo{" +
                "id='" + id + '\'' +
                ", placeType=" + placeType +
                ", placeId='" + placeId + '\'' +
                ", productId='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", firstLevelCategoryId='" + firstLevelCategoryId + '\'' +
                ", firstLevelCategoryName='" + firstLevelCategoryName + '\'' +
                ", secondLevelCategoryId='" + secondLevelCategoryId + '\'' +
                ", secondLevelCategoryName='" + secondLevelCategoryName + '\'' +
                ", thirdLevelCategoryId='" + thirdLevelCategoryId + '\'' +
                ", thirdLevelCategoryName='" + thirdLevelCategoryName + '\'' +
                ", fourthLevelCategoryId='" + fourthLevelCategoryId + '\'' +
                ", fourthLevelCategoryName='" + fourthLevelCategoryName + '\'' +
                ", internationalCodes=" + internationalCodes +
                ", branchCompanyId='" + branchCompanyId + '\'' +
                ", branchCompanyName='" + branchCompanyName + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", adrSupId='" + adrSupId + '\'' +
                ", adrSupCode='" + adrSupCode + '\'' +
                ", adrSupName='" + adrSupName + '\'' +
                ", logisticsModel=" + logisticsModel +
                ", createDate=" + createDate +
                ", lastModifyDate=" + lastModifyDate +
                ", createUserId='" + createUserId + '\'' +
                ", lastModifyUserId='" + lastModifyUserId + '\'' +
                ", auditDate=" + auditDate +
                ", auditUserId='" + auditUserId + '\'' +
                '}';
    }
}
