package com.yatang.sc.facade.dto.prod.place;

import com.yatang.sc.facade.common.BaseDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/15 14:12
*@Desc 新增商品地点关系VO
*/
public class ProdPlaceAddDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -7928158450847168369L;

    /** 需要添加商品地点关系的商品Id */
    private List<String> productIds = new ArrayList<>();

    /** 地点类型：0:所有;1:子公司;2:区域;3:门店 */
    private Integer placeType;

    /** 地点Id */
    private String placeId;

    /** 供应商ID */
    private String supplierId;

    /** 供应商编码 */
    private String supplierCode;

    /** 供应商名称 */
    private String supplierName;

    /** 供应商地点Id */
    private String adrSupId;

    /** 供应商地点编码 */
    private String adrSupCode;

    /** 供应商地点名称 */
    private String adrSupName;

    /** 送货类型：0:直送，1:配送 */
    private Integer logisticsModel;

    /** 创建时间 */
    private Date createDate;

    /** 创建人 */
    private String createUserId;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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
        return "ProdPlaceAddDto{" +
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
                ", createDate=" + createDate +
                ", createUserId='" + createUserId + '\'' +
                '}';
    }

}
