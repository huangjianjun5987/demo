package com.yatang.sc.purchase.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OMSItemPriceInfoDto implements Serializable{

    private static final long serialVersionUID = -3724748380151670756L;

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
     * 价格计算终止原因
     */
    private String finalReasonCode;

    /**
     * 基础价格
     */
    private double listPrice;

    /**
     * 购物车级别促销所分摊的折扣价
     */
    private double orderDiscountShare;

    /**
     * 作为促销提供者所占的数量
     */
    private long quantityAsQualifier;

    /**
     * 作为促销接受者所占的数量
     */
    private long quantityDiscounted;

    /**
     * 未执行促销前的购物车商品净额
     */
    private double rawTotalPrice;

    /**
     * 购物车商品售价
     */
    private double salePrice;


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

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getOrderDiscountShare() {
        return orderDiscountShare;
    }

    public void setOrderDiscountShare(double orderDiscountShare) {
        this.orderDiscountShare = orderDiscountShare;
    }

    public long getQuantityAsQualifier() {
        return quantityAsQualifier;
    }

    public void setQuantityAsQualifier(long quantityAsQualifier) {
        this.quantityAsQualifier = quantityAsQualifier;
    }

    public long getQuantityDiscounted() {
        return quantityDiscounted;
    }

    public void setQuantityDiscounted(long quantityDiscounted) {
        this.quantityDiscounted = quantityDiscounted;
    }

    public double getRawTotalPrice() {
        return rawTotalPrice;
    }

    public void setRawTotalPrice(double rawTotalPrice) {
        this.rawTotalPrice = rawTotalPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
