package com.yatang.sc.order.domain;

import java.io.Serializable;

public class QuerOrderItemsPo implements Serializable{

    private static final long serialVersionUID = 2429070157512163805L;
    private String saleName;
    private String price;
    private long num;
    private String product_id;
    private String sku_id;

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
