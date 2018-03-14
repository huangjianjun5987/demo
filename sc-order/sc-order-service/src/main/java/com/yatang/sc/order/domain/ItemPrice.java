package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.List;

public class ItemPrice implements Serializable {
    private Long id;

    private Double amount;

    private String currencyCode;

    private String finalReasonCode;

    private Double listPrice;

    private Double orderDiscountShare;

    private Long quantityAsQualifier;

    private Long quantityDiscounted;

    private Double rawTotalPrice;

    private Double salePrice;

    /**
     * 调价记录
     */
    private List<PriceAdjustment> adjustments;

    private static final long serialVersionUID = 1L;

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
        this.currencyCode = currencyCode;
    }

    public String getFinalReasonCode() {
        return finalReasonCode;
    }

    public void setFinalReasonCode(String finalReasonCode) {
        this.finalReasonCode = finalReasonCode;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Double getOrderDiscountShare() {
        return orderDiscountShare;
    }

    public void setOrderDiscountShare(Double orderDiscountShare) {
        this.orderDiscountShare = orderDiscountShare;
    }

    public Long getQuantityAsQualifier() {
        return quantityAsQualifier;
    }

    public void setQuantityAsQualifier(Long quantityAsQualifier) {
        this.quantityAsQualifier = quantityAsQualifier;
    }

    public Long getQuantityDiscounted() {
        return quantityDiscounted;
    }

    public void setQuantityDiscounted(Long quantityDiscounted) {
        this.quantityDiscounted = quantityDiscounted;
    }

    public Double getRawTotalPrice() {
        return rawTotalPrice;
    }

    public void setRawTotalPrice(Double rawTotalPrice) {
        this.rawTotalPrice = rawTotalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public List<PriceAdjustment> getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(List<PriceAdjustment> adjustments) {
        this.adjustments = adjustments;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}