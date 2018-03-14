package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.List;


public class OrderPrice implements Serializable {
	private static final long serialVersionUID = -8181574463879714618L;

	private Long id;

    private Double rawSubtotal;

    private Double amount;

    private String currencyCode;

    private Double discountAmount;

    private String finalReasonCode;

    private Double manualAdjustmentTotal;

    private Double shipping;

    private Double total;

    private Double userDiscountAmount;

    private Double couponDiscountAmount;

    List<PriceAdjustment> adjustments;

    private boolean couponActivityReverted;
    

    public Double getCouponDiscountAmount() {
        if (couponDiscountAmount == null) {
            return 0.0D;
        }
        return couponDiscountAmount;
    }

    public void setCouponDiscountAmount(Double couponDiscountAmount) {
        this.couponDiscountAmount = couponDiscountAmount;
    }

    public Double getUserDiscountAmount() {
        if (userDiscountAmount == null) {
            return 0.0D;
        }
        return userDiscountAmount;
    }

    public void setUserDiscountAmount(Double userDiscountAmount) {
        this.userDiscountAmount = userDiscountAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRawSubtotal() {
        if (rawSubtotal == null) {
            return 0.0D;
        }
        return rawSubtotal;
    }

    public void setRawSubtotal(Double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public Double getAmount() {
        if (amount == null) {
            return 0.0D;
        }
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

    public Double getDiscountAmount() {
        if (discountAmount == null) {
            return 0.0D;
        }
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFinalReasonCode() {
        return finalReasonCode;
    }

    public void setFinalReasonCode(String finalReasonCode) {
        this.finalReasonCode = finalReasonCode == null ? null : finalReasonCode.trim();
    }

    public Double getManualAdjustmentTotal() {
        if (manualAdjustmentTotal == null) {
            return 0.0D;
        }
        return manualAdjustmentTotal;
    }

    public void setManualAdjustmentTotal(Double manualAdjustmentTotal) {
        this.manualAdjustmentTotal = manualAdjustmentTotal;
    }

    public Double getShipping() {
        if (shipping == null) {
            return 0.0D;
        }
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public Double getTotal() {
        if (total == null) {
            return 0.0D;
        }
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<PriceAdjustment> getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(List<PriceAdjustment> adjustments) {
        this.adjustments = adjustments;
    }

	public boolean isCouponActivityReverted() {
		return couponActivityReverted;
	}

	public void setCouponActivityReverted(boolean couponActivityReverted) {
		this.couponActivityReverted = couponActivityReverted;
	}
}