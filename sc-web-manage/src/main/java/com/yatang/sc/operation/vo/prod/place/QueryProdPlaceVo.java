package com.yatang.sc.operation.vo.prod.place;

import com.yatang.sc.operation.common.BaseVo;

import javax.validation.constraints.NotNull;

/**
 * Created by tangqi on 2018/1/18 14:12.
 */
public class QueryProdPlaceVo extends BaseVo {

    private String firstLevelCategoryId;

    private String secondLevelCategoryId;

    private String thirdLevelCategoryId;

    private String fourthLevelCategoryId;

    private String brand;

    private String productId;

    private String productName;

    private String internationalCode;

    private Integer logisticsModel;

    private Integer placeType;

    private String placeId;

    @NotNull(message = "{msg.notEmpty.message}")
    private String branchCompanyId;

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getFirstLevelCategoryId() {
        return firstLevelCategoryId;
    }

    public void setFirstLevelCategoryId(String firstLevelCategoryId) {
        this.firstLevelCategoryId = firstLevelCategoryId;
    }

    public String getSecondLevelCategoryId() {
        return secondLevelCategoryId;
    }

    public void setSecondLevelCategoryId(String secondLevelCategoryId) {
        this.secondLevelCategoryId = secondLevelCategoryId;
    }

    public String getThirdLevelCategoryId() {
        return thirdLevelCategoryId;
    }

    public void setThirdLevelCategoryId(String thirdLevelCategoryId) {
        this.thirdLevelCategoryId = thirdLevelCategoryId;
    }

    public String getFourthLevelCategoryId() {
        return fourthLevelCategoryId;
    }

    public void setFourthLevelCategoryId(String fourthLevelCategoryId) {
        this.fourthLevelCategoryId = fourthLevelCategoryId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public Integer getLogisticsModel() {
        return logisticsModel;
    }

    public void setLogisticsModel(Integer logisticsModel) {
        this.logisticsModel = logisticsModel;
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

    @Override
    public String toString() {
        return "QueryProdPlaceVo{" +
                "firstLevelCategoryId='" + firstLevelCategoryId + '\'' +
                ", secondLevelCategoryId='" + secondLevelCategoryId + '\'' +
                ", thirdLevelCategoryId='" + thirdLevelCategoryId + '\'' +
                ", fourthLevelCategoryId='" + fourthLevelCategoryId + '\'' +
                ", brand='" + brand + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", internationalCode='" + internationalCode + '\'' +
                ", logisticsModel=" + logisticsModel +
                ", placeType=" + placeType +
                ", placeId='" + placeId + '\'' +
                ", branchCompanyId='" + branchCompanyId + '\'' +
                '}';
    }
}
