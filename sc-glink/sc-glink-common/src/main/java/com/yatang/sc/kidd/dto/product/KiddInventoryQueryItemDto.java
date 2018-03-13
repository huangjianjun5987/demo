package com.yatang.sc.kidd.dto.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:
 * @类名:
 * @作者: lvheping
 * @创建时间: 2017/9/30 16:40
 * @版本: v1.0
 */
@XStreamAliasType("item")
public class KiddInventoryQueryItemDto implements Serializable {
    private static final long serialVersionUID = 3249454201580139912L;
    private String warehouseCode;//仓库编码
    private String itemCode;//商品编码
    private String itemId;//仓储系统商品ID
    private String inventoryType;//库存类型  ZP=正品, CC=残次,JS=机损, XS= 箱损, ZT=在途库存
    private Integer quantity;//未冻结库存数量
    private Integer lockQuantity;//冻结库存数量
    private String batchCode;//批次编码
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date productDate;//商品生产日期
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date expireDate;//商品过期日期
    private String produceCode;//生产批号

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getLockQuantity() {
        return lockQuantity;
    }

    public void setLockQuantity(Integer lockQuantity) {
        this.lockQuantity = lockQuantity;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    @Override
    public String toString() {
        return "InventoryQueryItemDto{" +
                "warehouseCode='" + warehouseCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemId='" + itemId + '\'' +
                ", inventoryType='" + inventoryType + '\'' +
                ", quantity=" + quantity +
                ", lockQuantity=" + lockQuantity +
                ", batchCode='" + batchCode + '\'' +
                ", productDate=" + productDate +
                ", expireDate=" + expireDate +
                ", produceCode='" + produceCode + '\'' +
                '}';
    }
}
