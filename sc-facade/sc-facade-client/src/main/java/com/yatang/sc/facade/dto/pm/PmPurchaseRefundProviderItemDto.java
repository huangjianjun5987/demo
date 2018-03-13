package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @描述:供应商采购退货商品单
 * @作者: leixin
 * @创建时间: 2018/1/10 10:41
 * @版本: v1.0
 */
public class PmPurchaseRefundProviderItemDto implements Serializable {
    private static final long serialVersionUID = -7807803805778925439L;
    /**
     *退货单id
     */
    private Long							id;
    /**
     * 关联采购单号
     */
    private Long				purchaseOrderNo;

    /**
     * 商品编号
     */
    private String productCode;
    /**
     * 条码(国际码)
     */
    private String internationalCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 规格
     */
    private String packingSpecifications;

    /**
     * 采购内装数(默认箱规)
     */
    private Integer purchaseInsideNumber;

    /**
     * 采购价格(退货单价)（含税）
     */
    private BigDecimal purchasePrice;

    /**
     * 退货数量
     */
    private Integer refundAmount;
    /**
     * 退货金额(含税)
     */
    private BigDecimal refundMoney;
    /**
     * 实际退货数量
     */
    private Integer realRefundAmount;

    /**
     * 实际退货金额(含税)
     */
    private BigDecimal realRefundMoney;

    /**
     * 收货地址
     */
    private String adrName;

    /**
     * 条形码地址
     */
    private String barCodeUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(Long purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getPackingSpecifications() {
        return packingSpecifications;
    }

    public void setPackingSpecifications(String packingSpecifications) {
        this.packingSpecifications = packingSpecifications;
    }

    public Integer getPurchaseInsideNumber() {
        return purchaseInsideNumber;
    }

    public void setPurchaseInsideNumber(Integer purchaseInsideNumber) {
        this.purchaseInsideNumber = purchaseInsideNumber;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getRealRefundAmount() {
        return realRefundAmount;
    }

    public void setRealRefundAmount(Integer realRefundAmount) {
        this.realRefundAmount = realRefundAmount;
    }

    public BigDecimal getRealRefundMoney() {
        return realRefundMoney;
    }

    public void setRealRefundMoney(BigDecimal realRefundMoney) {
        this.realRefundMoney = realRefundMoney;
    }

    public String getAdrName() {
        return adrName;
    }

    public void setAdrName(String adrName) {
        this.adrName = adrName;
    }

    public String getBarCodeUrl() {
        return barCodeUrl;
    }

    public void setBarCodeUrl(String barCodeUrl) {
        this.barCodeUrl = barCodeUrl;
    }
}
