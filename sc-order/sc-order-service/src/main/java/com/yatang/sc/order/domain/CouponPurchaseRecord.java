package com.yatang.sc.order.domain;

import java.io.Serializable;

public class CouponPurchaseRecord implements Serializable {
    private Long id;

    private Long commerceItemId;

    private String orderId;

    private Long quantity;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommerceItemId() {
        return commerceItemId;
    }

    public void setCommerceItemId(Long commerceItemId) {
        this.commerceItemId = commerceItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}