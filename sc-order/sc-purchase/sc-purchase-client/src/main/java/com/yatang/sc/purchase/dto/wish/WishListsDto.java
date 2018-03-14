package com.yatang.sc.purchase.dto.wish;

import java.io.Serializable;
import java.util.Map;

public class WishListsDto implements Serializable {

    private static final long serialVersionUID = 1290429792564575267L;

    private String id;
    private String productCode;
    private String productName;
    private String productImg;
    private long quantity;
    private String barCode;
    private String status;//1有货，0无货

    //已到货需要字段
    private int  sellFullCase;//是否整箱销售：0-否；1-是
    private int unitQuantity;//每箱多少瓶  就是销售内装数
//    private int minNumber;//起订量
    private double salePrice;//销售单价
    private double fullCasePrice;
    /**
     * 属性(颜色、尺寸等)
     */
    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public double getFullCasePrice() {
        return fullCasePrice;
    }

    public void setFullCasePrice(double fullCasePrice) {
        this.fullCasePrice = fullCasePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSellFullCase() {
        return sellFullCase;
    }

    public void setSellFullCase(int sellFullCase) {
        this.sellFullCase = sellFullCase;
    }

    public int getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(int unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
