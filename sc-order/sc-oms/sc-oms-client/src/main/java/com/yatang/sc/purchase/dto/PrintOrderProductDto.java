package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/7/18.
 */
public class PrintOrderProductDto implements Serializable{

    private String id;
    private String skuId;
    private String productId;
    private String productName;
    private String quantity;
    private Double salePrice;
    private Double amount;

    public PrintOrderProductDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSale_price(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
