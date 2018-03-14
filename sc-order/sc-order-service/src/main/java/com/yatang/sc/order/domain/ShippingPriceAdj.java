package com.yatang.sc.order.domain;

import java.io.Serializable;

public class ShippingPriceAdj implements Serializable {
    private Long id;

    private Long shippingPriceId;

    private Long adjustmentId;

    private Byte sequence;

    private ShippingPrice shippingPrice;

    public ShippingPrice getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(ShippingPrice shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShippingPriceId() {
        return shippingPriceId;
    }

    public void setShippingPriceId(Long shippingPriceId) {
        this.shippingPriceId = shippingPriceId;
    }

    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public Byte getSequence() {
        return sequence;
    }

    public void setSequence(Byte sequence) {
        this.sequence = sequence;
    }
}