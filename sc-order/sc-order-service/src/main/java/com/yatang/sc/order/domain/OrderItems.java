package com.yatang.sc.order.domain;

import java.io.Serializable;


public class OrderItems implements Serializable {
    private Long id;

    private String orderId;

    private Long commerceItemId;

    private Boolean delete;

    private static final long serialVersionUID = 1L;

    private CommerceItem commerceItem;

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

    public Long getCommerceItemId() {
        return commerceItemId;
    }

    public void setCommerceItemId(Long commerceItemId) {
        this.commerceItemId = commerceItemId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CommerceItem getCommerceItem() {
        return commerceItem;
    }

    public void setCommerceItem(CommerceItem commerceItem) {
        this.commerceItem = commerceItem;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean pDelete) {
        delete = pDelete;
    }
}