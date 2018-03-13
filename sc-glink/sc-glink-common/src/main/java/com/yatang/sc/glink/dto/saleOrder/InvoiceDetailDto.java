package com.yatang.sc.glink.dto.saleOrder;

import java.io.Serializable;

/**
 * @描述: 发票行详情DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class InvoiceDetailDto  implements Serializable {
    private String itemName;//商品名称
    private String unit;//商品单位
    private double price;//商品单价
    private Integer quantity;//数量
    private double amount;//金额

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
