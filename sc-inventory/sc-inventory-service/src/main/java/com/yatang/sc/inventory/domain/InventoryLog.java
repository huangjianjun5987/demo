package com.yatang.sc.inventory.domain;

import java.io.Serializable;
import java.util.Date;

public class InventoryLog implements Serializable {
    private Long id;
    private String orderId;//订单ID
    private String commerceId;//commerceItemId
    private String productId;//商品ID
    private String loc;//仓库编码
    private Long quantity;//数量
    private String type;//类型：reserve,saleOut,arrived,unArrive,cancel,stockIn
    private String comment;
    private Date dateTime;//时间

    public enum Type {
        reserve("reserve", "预留操作"), saleOut("saleOut", "发货操作"), arrived("arrived", "送达操作"), unArrive("unArrive", "未送达操作"), cancel("cancel", "取消操作"), saleReturn("cancel", "销售退货操作"), stockIn("stockIn", "入库操作"),purchaseRefund("refund","采购退货操作");
        private String val;
        private String desc;

        Type(String val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        public String getVal() {
            return val;
        }

        public String getDesc() {
            return desc;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(String commerceId) {
        this.commerceId = commerceId;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
