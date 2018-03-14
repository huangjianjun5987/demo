package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class ShippingPriceInfoDto implements Serializable{
    private static final long serialVersionUID = -2115345175829018752L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 购物车商品的价格计算结果
     */
    private double amount;

    /**
     * 货币种类代码
     */
    private String currencyCode;

    /**
     * 调价记录
     */
    private List<PriceAdjustmentDto> adjustments;

    /**
     * 价格计算终止原因
     */
    private String finalReasonCode;

    /**
     * 未执行减免的运费净额
     */
    private double rawShipping;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
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

    public String getFinalReasonCode() {
        return finalReasonCode;
    }

    public void setFinalReasonCode(String finalReasonCode) {
        this.finalReasonCode = finalReasonCode;
    }

    public double getRawShipping() {
        return rawShipping;
    }

    public void setRawShipping(double rawShipping) {
        this.rawShipping = rawShipping;
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
