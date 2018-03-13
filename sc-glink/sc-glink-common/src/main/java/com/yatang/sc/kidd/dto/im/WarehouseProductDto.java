package com.yatang.sc.kidd.dto.im;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:仓库商品信息
 * @类名:WarehouseProductDto
 * @作者: lvheping
 * @创建时间: 2017/9/4 18:04
 * @版本: v1.0
 */

public class WarehouseProductDto implements Serializable {
    private static final long serialVersionUID = -5931926189154005434L;
    private String itemCode;//商品编码
    private String ownerCode;//货主编码（子公司编码）
    private String warehouseCode;//仓库编码
    private String wmsID;//仓库商品id
    private Integer actualQty;//数量
    private String inventoryType;//库存类型
    @JSONField(format = "yyyy-MM-dd")
    private Date productDate;//商品生产日期
    @JSONField(format = "yyyy-MM-dd")
    private Date expireDate;//商品过期时间
    private String produceCode;//生产批次
    private String batchCode;//批次编码
    private Integer lockQuantity;//冻结数量
    private Date updateTime;//WMS库存更新时间

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    public Integer getLockQuantity() {
        return lockQuantity;
    }

    public void setLockQuantity(Integer lockQuantity) {
        this.lockQuantity = lockQuantity;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setWmsID(String wmsID) {
        this.wmsID = wmsID;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public String getWmsID() {
        return wmsID;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public Date getProductDate() {
        return productDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return "WarehouseProductDto{" +
                "itemCode='" + itemCode + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", wmsID='" + wmsID + '\'' +
                ", actualQty='" + actualQty + '\'' +
                ", inventoryType='" + inventoryType + '\'' +
                ", productDate=" + productDate +
                ", expireDate=" + expireDate +
                ", produceCode='" + produceCode + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", lockQuantity='" + lockQuantity + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
