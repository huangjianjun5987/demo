package com.yatang.sc.purchase.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class OrderPriceInfoDto implements Serializable{

    private static final long serialVersionUID = -5062687201200932176L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 订单中商品未执行任何折扣的净额
     */
    private double rawSubtotal;

    /**
     * 订单商品总额(去除优惠后的值)
     */
    private double amount;

    /**
     * 货币种类code
     */
    private String currencyCode;

    /**
     * 订单折扣额
     */
    private double discountAmount;

    /**
     * 价格计算终止原因
     */
    private String finalReasonCode;

    /**
     * 手工调价总额
     */
    private double manualAdjustmentTotal;

    /**
     * 运费总额
     */
    private double shipping;

    /**
     * 订单实际最终总额
     */
    private double total;

    private double userDiscountAmount;

    private double couponDiscountAmount;

    private List<PromotionDto> availablePromotions =new ArrayList<>();
    
    public List<PromotionDto> getAvailablePromotions() {
		return availablePromotions;
	}

	public void setAvailablePromotions(List<PromotionDto> availablePromotions) {
		this.availablePromotions = availablePromotions;
	}

	/**
     * 价格变化记录
     */
    private List<PriceAdjustmentDto>  adjustments;

    public double getCouponDiscountAmount() {
        return couponDiscountAmount;
    }

    public void setCouponDiscountAmount(double couponDiscountAmount) {
        this.couponDiscountAmount = couponDiscountAmount;
    }

    public double getUserDiscountAmount() {
        return userDiscountAmount;
    }

    public void setUserDiscountAmount(double userDiscountAmount) {
        this.userDiscountAmount = userDiscountAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public double getAmount() {
    	if(amount<0){
    		return 0d;
    	}
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFinalReasonCode() {
        return finalReasonCode;
    }

    public void setFinalReasonCode(String finalReasonCode) {
        this.finalReasonCode = finalReasonCode;
    }

    public double getManualAdjustmentTotal() {
        return manualAdjustmentTotal;
    }

    public void setManualAdjustmentTotal(double manualAdjustmentTotal) {
        this.manualAdjustmentTotal = manualAdjustmentTotal;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private void ensureContainer() {
        if(this.adjustments == null){
            this.adjustments = new ArrayList<PriceAdjustmentDto>();
        }
    }


	public List<PriceAdjustmentDto> getAdjustments() {
        ensureContainer();
		return adjustments;
	}

	public void setAdjustments(List<PriceAdjustmentDto> adjustments) {
		this.adjustments = adjustments;
	}

}
