package com.yatang.sc.inventory.dto.im;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * "items":[
 * {
 * "itemCode":"",
 * "itemId":"",
 * "inventoryType":"",
 * "quantity":"",
 * "batchCode":"",
 * "productDate":"yyyy-MM-dd",
 * "expireDate":"yyyy-MM-dd",
 * "produceCode":"",
 * "snCode":"",
 * "remark":""
 * }
 * ]
 * /**
 *
 * @描述: 库存调整单数据商品信息
 * @作者: yangshuang
 * @创建时间:2017年09月04日15:07
 */

public class KiddImAdjustmentItemDto implements Serializable {

    private static final long serialVersionUID = -1457126585393432735L;
    private String itemCode; //商品编码

    private String itemId;//仓储系统商品ID 否
    private String inventoryType;//库存类型 ZP=正品, CC=残次,JS=机损, XS= 箱损, ZT=在途库存，默认为ZP 否


    private Integer quantity;//盘盈盘亏商品变化量 盘盈为正数，盘亏为负数


    private String batchCode;//批次编码

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productDate;//商品生产日期 YYYY-MM-DD

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;//商品过期日期 YYYY-MM-DD

    private String produceCode;//生产批号

    private String snCode;//商品序列号

    private String remark;//备注

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

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
