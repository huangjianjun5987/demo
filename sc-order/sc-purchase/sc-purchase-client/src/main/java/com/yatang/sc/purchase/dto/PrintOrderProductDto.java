package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/7/18.
 */
@Data
public class PrintOrderProductDto implements Serializable{

    private static final long serialVersionUID = -6372585581623334030L;
    private String id;
    private String skuId;
    private String productId;
    private String productName;
    private String quantity;
    private Double sale_price;
    private Double amount;

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

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
