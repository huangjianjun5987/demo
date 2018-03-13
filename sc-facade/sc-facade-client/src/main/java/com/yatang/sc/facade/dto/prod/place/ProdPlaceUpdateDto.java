package com.yatang.sc.facade.dto.prod.place;

import java.io.Serializable;
import java.util.Date;

/*
*@Author tangqi
*@Date 2018/1/15 15:40
*@Desc 商品地点关系更新VO
*/
public class ProdPlaceUpdateDto implements Serializable {

    private static final long serialVersionUID = 8685409527258830578L;

    private String id;

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

    /**配送方式：0:直送，1:配送*/
    private Integer logisticsModel;

    /**最近修改时间*/
    private Date lastModifyDate;

    /**最近修改人*/
    private String lastModifyUserId;

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getLastModifyUserId() {
        return lastModifyUserId;
    }

    public void setLastModifyUserId(String lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
    }

    public Integer getLogisticsModel() {
        return logisticsModel;
    }

    public void setLogisticsModel(Integer logisticsModel) {
        this.logisticsModel = logisticsModel;
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

    @Override
    public String toString() {
        return "ProdPlaceUpdateDto{" +
                "id='" + id + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", adrSupId='" + adrSupId + '\'' +
                ", adrSupCode='" + adrSupCode + '\'' +
                ", adrSupName='" + adrSupName + '\'' +
                ", logisticsModel=" + logisticsModel +
                ", lastModifyDate=" + lastModifyDate +
                ", lastModifyUserId='" + lastModifyUserId + '\'' +
                '}';
    }
}
