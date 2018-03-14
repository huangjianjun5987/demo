package com.yatang.sc.order.domain;

import java.io.Serializable;

public class PriceAdjustment implements Serializable {
    private Long id;

    private Double adjustment;

    private String adjustmentDescription;

    private String pricingModel;

    private Long quantityAdjusted;

    private Double totalAdjustment;

    private Integer pricingModelType;

    private String activityIds;

    private static final long serialVersionUID = 1L;

    public PriceAdjustment() {
    }

    public Integer getPricingModelType() {
        return pricingModelType;
    }

    public void setPricingModelType(Integer pricingModelType) {
        this.pricingModelType = pricingModelType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    public String getAdjustmentDescription() {
        return adjustmentDescription;
    }

    public void setAdjustmentDescription(String adjustmentDescription) {
        this.adjustmentDescription = adjustmentDescription == null ? null : adjustmentDescription.trim();
    }

    public String getPricingModel() {
        return pricingModel;
    }

    public void setPricingModel(String pricingModel) {
        this.pricingModel = pricingModel == null ? null : pricingModel.trim();
    }

    public Long getQuantityAdjusted() {
        return quantityAdjusted;
    }

    public void setQuantityAdjusted(Long quantityAdjusted) {
        this.quantityAdjusted = quantityAdjusted;
    }

    public Double getTotalAdjustment() {
        return totalAdjustment;
    }

    public void setTotalAdjustment(Double totalAdjustment) {
        this.totalAdjustment = totalAdjustment;
    }

    public String getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(String pActivityIds) {
        activityIds = pActivityIds;
    }
}