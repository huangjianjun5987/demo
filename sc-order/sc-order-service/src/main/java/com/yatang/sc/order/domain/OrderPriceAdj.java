package com.yatang.sc.order.domain;

import java.io.Serializable;

public class OrderPriceAdj implements Serializable {
    private Long id;

    private Long orderPriceId;

    private Long adjustmentId;

    private Byte sequence;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderPriceId() {
        return orderPriceId;
    }

    public void setOrderPriceId(Long orderPriceId) {
        this.orderPriceId = orderPriceId;
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