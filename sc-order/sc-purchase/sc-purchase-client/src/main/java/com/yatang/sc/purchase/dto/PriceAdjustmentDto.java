package com.yatang.sc.purchase.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class PriceAdjustmentDto implements Serializable{

    private static final long serialVersionUID = 3268206652665909968L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 单个商品价格变化额度
     */
    private double adjustment;

    /**
     * 价格变化描述
     */
    private String adjustmentDescription;

    /**
     * 关联的手动调价对象
     */
    private String manualPricingAdjustment;

    /**
     * 促销类型
     */
    private Integer pricingModelType;
    /**
     * 关联的促销
     */
    private String pricingModel;

    /**
     * 调价数量
     */
    private double quantityAdjusted;

    /**
     * 价格变化总额
     */
    private double totalAdjustment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(double adjustment) {
        this.adjustment = adjustment;
    }

    public String getAdjustmentDescription() {
        return adjustmentDescription;
    }

    public void setAdjustmentDescription(String adjustmentDescription) {
        this.adjustmentDescription = adjustmentDescription;
    }

    public String getManualPricingAdjustment() {
        return manualPricingAdjustment;
    }

    public void setManualPricingAdjustment(String manualPricingAdjustment) {
        this.manualPricingAdjustment = manualPricingAdjustment;
    }

    public Integer getPricingModelType() {
        return pricingModelType;
    }

    public boolean isCouponAdjument(){
        if(pricingModelType != null && pricingModelType == 1){
            return true;
        }
        return false;
    }
    public void setPricingModelType(Integer pricingModelType) {
        this.pricingModelType = pricingModelType;
    }

    public String getPricingModel() {
        return pricingModel;
    }

    public void setPricingModel(String pricingModel) {
        this.pricingModel = pricingModel;
    }

    public double getQuantityAdjusted() {
        return quantityAdjusted;
    }

    public void setQuantityAdjusted(double quantityAdjusted) {
        this.quantityAdjusted = quantityAdjusted;
    }

    public double getTotalAdjustment() {
        return totalAdjustment;
    }

    public void setTotalAdjustment(double totalAdjustment) {
        this.totalAdjustment = totalAdjustment;
    }
}
