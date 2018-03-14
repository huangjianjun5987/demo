package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/7/14.
 */
public class OrderProductListDto implements Serializable{

    private static final long serialVersionUID = -3709572695502330278L;
    private String iconUrl;
    private String name;
    private String saleName;
    private String price;
    private long num;
    private String product_id;
    private String sku_id;
    private List<ProductAttributeDto> attributeDtoList;

    public List<ProductAttributeDto> getAttributeDtoList() {
        return attributeDtoList;
    }

    public void setAttributeDtoList(List<ProductAttributeDto> attributeDtoList) {
        this.attributeDtoList = attributeDtoList;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
