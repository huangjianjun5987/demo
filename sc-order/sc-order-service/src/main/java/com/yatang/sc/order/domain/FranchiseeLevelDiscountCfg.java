package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class FranchiseeLevelDiscountCfg implements Serializable {
    private Long id;

    private String fLevel;

    private BigDecimal discount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfLevel() {
        return fLevel;
    }

    public void setfLevel(String fLevel) {
        this.fLevel = fLevel == null ? null : fLevel.trim();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}