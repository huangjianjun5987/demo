package com.yatang.sc.operation.vo.prod.place;

import javax.validation.constraints.NotNull;

/*
*@Author tangqi
*@Date 2018/1/15 15:40
*@Desc 商品地点关系更新VO
*/
public class ProdPlaceUpdateVo {

    @NotNull(message = "{msg.notEmpty.message}")
    private String id;

    @NotNull(message = "{msg.notEmpty.message}")
    private String supplierId;

    @NotNull(message = "{msg.notEmpty.message}")
    private String supplierCode;

    @NotNull(message = "{msg.notEmpty.message}")
    private String supplierName;

    @NotNull(message = "{msg.notEmpty.message}")
    private String adrSupId;

    @NotNull(message = "{msg.notEmpty.message}")
    private String adrSupCode;

    @NotNull(message = "{msg.notEmpty.message}")
    private String adrSupName;

    @NotNull(message = "{msg.notEmpty.message}")
    private Integer logisticsModel;//0:直送，1:配送

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
        return "ProdPlaceUpdateVo{" +
                "id='" + id + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", adrSupId='" + adrSupId + '\'' +
                ", adrSupCode='" + adrSupCode + '\'' +
                ", adrSupName='" + adrSupName + '\'' +
                ", logisticsModel=" + logisticsModel +
                '}';
    }
}
