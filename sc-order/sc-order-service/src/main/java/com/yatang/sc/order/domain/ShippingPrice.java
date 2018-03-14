package com.yatang.sc.order.domain;


import java.io.Serializable;
import java.util.List;

public class ShippingPrice implements Serializable {
    private Long id;

    private Double amount;

    private String currencyCode;

    private String finalReasonCode;

    private Double rawShipping;

    /**
     * 调价记录
     */
    private List<PriceAdjustment> adjustments;

    private static final long serialVersionUID = 1L;

    private ShippingPriceAdj shippingPriceAdj;

    public ShippingPriceAdj getShippingPriceAdj() {
        return shippingPriceAdj;
    }

    public void setShippingPriceAdj(ShippingPriceAdj shippingPriceAdj) {
        this.shippingPriceAdj = shippingPriceAdj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getFinalReasonCode() {
        return finalReasonCode;
    }

    public void setFinalReasonCode(String finalReasonCode) {
        this.finalReasonCode = finalReasonCode == null ? null : finalReasonCode.trim();
    }

    public Double getRawShipping() {
        return rawShipping;
    }

    public void setRawShipping(Double rawShipping) {
        this.rawShipping = rawShipping;
    }

    public List<PriceAdjustment> getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(List<PriceAdjustment> adjustments) {
        this.adjustments = adjustments;
    }


}