package com.yatang.sc.order.domain;

import java.io.Serializable;

public class ItemPriceAdj implements Serializable {
    private Long id;

    private Long itemPriceId;

    private Long adjustmentId;

    private Byte sequence;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemPriceId() {
        return itemPriceId;
    }

    public void setItemPriceId(Long itemPriceId) {
        this.itemPriceId = itemPriceId;
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