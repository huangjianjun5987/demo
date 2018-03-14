package com.yatang.sc.operation.vo.prod.place;

import com.yatang.sc.operation.common.BaseVo;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/15 14:12
*@Desc 新增商品地点关系VO
*/
public class ProdPlaceAddVo extends BaseVo {

    private List<String> productIds = new ArrayList<>();

    @NotNull(message = "{msg.notEmpty.message}")
    private Integer placeType;//0:所有;1:子公司;2:区域;3:门店

    @NotNull(message = "{msg.notEmpty.message}")
    private String placeId;

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
    private Integer logisticsModel;

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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Integer getLogisticsModel() {
        return logisticsModel;
    }

    public void setLogisticsModel(Integer logisticsModel) {
        this.logisticsModel = logisticsModel;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
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
        return "ProdPlaceAddVo{" +
                "productIds=" + productIds +
                ", placeType=" + placeType +
                ", placeId='" + placeId + '\'' +
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
