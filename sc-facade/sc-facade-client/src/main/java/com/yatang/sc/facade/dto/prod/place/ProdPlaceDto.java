package com.yatang.sc.facade.dto.prod.place;

import java.io.Serializable;
import java.util.Date;

/*
*@Author tangqi
*@Date 2018/1/11 14:01
*@Desc 商品地点关系
*/
public class ProdPlaceDto implements Serializable {

    private static final long serialVersionUID = 940226105449788876L;

    private String id;

    /** 地点类型：0:所有;1:子公司;2:区域;3:门店 */
    private Integer placeType;

    /** 地点Id */
    private String placeId;

    /**商品Id*/
    private String productId;

    /**商品编码*/
    private String productCode;

    /**商品名称*/
    private String productName;

    /**商品品牌*/
    private String brand;

    /**部类Id*/
    private String firstLevelCategoryId;

    /**部类名称*/
    private String firstLevelCategoryName;

    /**大类Id*/
    private String secondLevelCategoryId;

    /**大类名称*/
    private String secondLevelCategoryName;

    /**中类Id*/
    private String thirdLevelCategoryId;

    /**中类名称*/
    private String thirdLevelCategoryName;

    /**小类Id*/
    private String fourthLevelCategoryId;

    /**小类名称*/
    private String fourthLevelCategoryName;

    /**分公司Id*/
    private String branchCompanyId;

    /**分公司名称*/
    private String branchCompanyName;

    /**门店Id*/
    private String storeId;

    /**门店编码*/
    private String storeCode;

    /**门店名称*/
    private String storeName;

    /**供应商Id*/
    private String supplierId;

    /**供应商编码*/
    private String supplierCode;

    /**供应商名称*/
    private String supplierName;

    /**供应商地点Id*/
    private String adrSupId;

    /**供应商地点编码*/
    private String adrSupCode;

    /**供应商地点名称*/
    private String adrSupName;

    /** 送货类型：0:直送，1:配送 */
    private Integer logisticsModel;

    /**创建时间*/
    private Date createDate;

    /**最近修改时间*/
    private Date lastModifyDate;

    /**创建人Id*/
    private String createUserId;

    /**最近修改人Id*/
    private String lastModifyUserId;

    /**审核时间*/
    private Date auditDate;

    /**审核人Id*/
    private String auditUserId;

    /**物流模式(接收数据用)*/
    private String logisticsType;

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

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    @Override
    public String toString() {
        return "ProdPlaceDto{" +
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
