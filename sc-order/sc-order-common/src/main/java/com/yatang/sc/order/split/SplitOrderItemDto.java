package com.yatang.sc.order.split;

import java.io.Serializable;

public class SplitOrderItemDto implements Serializable {

    private Long commerceId;
    private Long quantity;

    public SplitOrderItemDto() {}

    public SplitOrderItemDto(Long commerceId, Long quantity) {
        this.commerceId = commerceId;
        this.quantity = quantity;
    }

    public Long getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(Long pCommerceId) {
        commerceId = pCommerceId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long pQuantity) {
        quantity = pQuantity;
    }
}
