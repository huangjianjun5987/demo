package com.yatang.sc.inventory.dto;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/7/28.
 */
public class ItemInventoryDto implements Serializable {
    /**
     * id
     */
    private String id;


    private String itemId;
    /**
     * 商品id
     */
    private String productId;

    /**
     * 仓库编码
     */
    private String loc;
    /**
     * 已签收数量
     */
    private long completeQty;
    /**
     * 销售出库数量
     */
    private long saleStock;
    /**
     * 订单确认数量
     */
    private long itemQty;

    /**
     * 销售内装数
     */
    private int unitQuantity;
    /**
     * 已出库数量
     */
    private long stock;

    private String productCode;//商品Code
    private String vatRate;
    private String groups;
    private String dept;
    private String classs;
    private String subclass;
    /**
     * 销售单价
     */
    private double actualPrice;

    private double avCost;//移动平均价

    private String branchCompanyId;
    /**
     * 商品名称
     */
    private String productName;

    public String getProductName() { return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public ItemInventoryDto() {
    }


    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getItemQty() {
        return itemQty;
    }

    public void setItemQty(long itemQty) {
        this.itemQty = itemQty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String pItemId) {
        itemId = pItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public long getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(long completeQty) {
        this.completeQty = completeQty;
    }

    public long getSaleStock() {
        return saleStock;
    }

    public void setSaleStock(long saleStock) {
        this.saleStock = saleStock;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVatRate() {
        return vatRate;
    }

    public void setVatRate(String vatRate) {
        this.vatRate = vatRate;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public double getAvCost() {
        return avCost;
    }

    public void setAvCost(double avCost) {
        this.avCost = avCost;
    }

    public int getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(int pUnitQuantity) {
        unitQuantity = pUnitQuantity;
    }
}
