package com.yatang.sc.kidd.dto.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:采购订单商品明细信息
 * @类名:KiddOrderLinesDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 9:42
 * @版本: v1.0
 */

public class KiddOrderLinesDto implements Serializable {
    private static final long serialVersionUID = -6121817986354404081L;
    private String orderLineNo;     //唯一的入库单行号         (必填)
    private String returnOrderLineNo;//原销售订单的行号    (必填)
    private String itemCode;        //商品编码，sku编码        (必填)
    private String itemId;           //仓储系统商品ID           (必填)
    private String inventoryType;   //库存类型 ZP=正品,CC=残次,JS=机损,XS=箱损
    private String itemName;        //商品名称
    private String pcs;             //商品规格
    private String isGift;         //是否为赠品
    private String skuProperty;     //商品品类
    private int planQty;         //商品入库数量         (必填)
    private int actualQty;//商品实际收货数量
    private int acualQty;//商品实际入库数量(际链名称写错了，际链专用)
    private String stockUnit;       //包装单位
    private double purchasePrice=0.0;   //采购单价            (必填)
    private double priceCount;      //单价*入库数量
    private double retailPrice;     //零售单价
    @JSONField(format = "yyyy-MM-dd")
    private Date productDate;     //商品生产日期YYYY-MM-DD
    @JSONField(format = "yyyy-MM-dd")
    private Date expireDate;      //商品过期日期YYYY-MM-DD
    private String produceCode;     //生产批号
    private String batchCode;       //批次编码,
    private String string1;         //扩展字段1
    private String string2;         //扩展字段1
    private String string3;         //扩展字段1
    private String string4;         //扩展字段1
    private String string5;         //扩展字段1

    public int getAcualQty() {
        return acualQty;
    }

    public void setAcualQty(int acualQty) {
        this.acualQty = acualQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public int getPlanQty() {
        return planQty;
    }

    public int getActualQty() {
        return actualQty==0?acualQty:actualQty;
    }

    public void setActualQty(int actualQty) {
        this.actualQty = actualQty;
    }

    public String getReturnOrderLineNo() {
        return returnOrderLineNo;
    }

    public void setReturnOrderLineNo(String returnOrderLineNo) {
        this.returnOrderLineNo = returnOrderLineNo;
    }

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getIsGift() {
        return isGift;
    }

    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }

    public String getSkuProperty() {
        return skuProperty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public String getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(double priceCount) {
        this.priceCount = priceCount;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public String getString4() {
        return string4;
    }

    public void setString4(String string4) {
        this.string4 = string4;
    }

    public String getString5() {
        return string5;
    }

    public void setString5(String string5) {
        this.string5 = string5;
    }
}
