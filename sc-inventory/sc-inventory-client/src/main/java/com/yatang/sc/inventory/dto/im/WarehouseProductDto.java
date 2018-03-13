package com.yatang.sc.inventory.dto.im;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
    private String actualQty;//数量
    private String inventoryType;//库存类型
    private String productDate;//商品生产日期
    private String expireDate;//商品过期时间
    private String produceCode;//生产批次
    private String batchCode;//批次编码
    private String lockQuantity;//冻结数量
    private String updateTime;//WMS库存更新时间

    @Override
    public String toString() {
        return "WarehouseProductDto{" +
                "itemCode='" + itemCode + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", wmsID='" + wmsID + '\'' +
                ", actualQty='" + actualQty + '\'' +
                ", inventoryType='" + inventoryType + '\'' +
                ", productDate='" + productDate + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", produceCode='" + produceCode + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", lockQuantity='" + lockQuantity + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
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

    public void setActualQty(String actualQty) {
        this.actualQty = actualQty;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setLockQuantity(String lockQuantity) {
        this.lockQuantity = lockQuantity;
    }

    public void setUpdateTime(String updateTime) {
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

    public String getActualQty() {
        return actualQty;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public String getProductDate() {
        return productDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getLockQuantity() {
        return lockQuantity;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
