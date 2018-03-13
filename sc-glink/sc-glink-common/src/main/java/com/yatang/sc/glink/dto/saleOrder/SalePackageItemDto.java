package com.yatang.sc.glink.dto.saleOrder;

import java.io.Serializable;

/**
 * @描述:包裹内的商品信息
 * @作者: wangcheng
 * @创建时间:2017年08月04日15:58
 */
public class SalePackageItemDto implements Serializable {
    private String itemCode;//商品编码
    private String itemId;//商品仓储系统编码
    private int quantity;//包裹内该商品的数量

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
