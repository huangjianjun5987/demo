package com.yatang.sc.facade.domain.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmPurchaseRefundItemPo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 退货单ID
     */
    private Long purchaseRefundId;

    /**
     * 采购单号
     */
    private String purchaseOrderNo;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品编号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 条码(国际码)
     */
    private String internationalCode;

    /**
     * 规格
     */
    private String packingSpecifications;

    /**
     * 产地
     */
    private String producePlace;

    /**
     * 采购内装数(默认箱规)
     */
    private Integer purchaseInsideNumber;

    /**
     * 移动加权平均价格（不与数据库对应）
     */
    private Double avCost;

    /**
     * 单位
     */
    private String unitExplanation;

    /**
     * 税率,进项税率
     */
    private BigDecimal inputTaxRate;

    /**
     * 采购价格（含税）
     */
    private BigDecimal purchasePrice;

    /**
     * 可退库存
     */
    private Integer possibleNum;

    /**
     * 退货数量
     */
    private Integer refundAmount;

    /**
     * 退货金额(含税)
     */
    private BigDecimal refundMoney;

    /**
     * 退货成本额
     */
    private BigDecimal refundCost;

    /**
     * 实际退货数量
     */
    private Integer realRefundAmount;

    /**
     * 实际退货金额(含税)
     */
    private BigDecimal realRefundMoney;

    /**
     * 退货原因(0:破损;1:临期;2:库存过剩;3:其他)
     */
    private Integer refundReason;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 
     */
    private String createUserId;

    /**
     * 
     */
    private String modifyUserId;

    /**
     * 是否有效:0,无效,1:有效
     */
    private Integer isValid;

    /**
     * 流程节点id
     */
    private Long processId;

    /**
     * pm_purchase_refund_item
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseRefundId() {
        return purchaseRefundId;
    }

    public void setPurchaseRefundId(Long purchaseRefundId) {
        this.purchaseRefundId = purchaseRefundId ;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode == null ? null : internationalCode.trim();
    }

    public String getPackingSpecifications() {
        return packingSpecifications;
    }

    public void setPackingSpecifications(String packingSpecifications) {
        this.packingSpecifications = packingSpecifications == null ? null : packingSpecifications.trim();
    }

    public String getProducePlace() {
        return producePlace;
    }

    public void setProducePlace(String producePlace) {
        this.producePlace = producePlace == null ? null : producePlace.trim();
    }

    public Integer getPurchaseInsideNumber() {
        return purchaseInsideNumber;
    }

    public void setPurchaseInsideNumber(Integer purchaseInsideNumber) {
        this.purchaseInsideNumber = purchaseInsideNumber;
    }

    public String getUnitExplanation() {
        return unitExplanation;
    }

    public void setUnitExplanation(String unitExplanation) {
        this.unitExplanation = unitExplanation == null ? null : unitExplanation.trim();
    }

    public BigDecimal getInputTaxRate() {
        return inputTaxRate;
    }

    public void setInputTaxRate(BigDecimal inputTaxRate) {
        this.inputTaxRate = inputTaxRate;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getPossibleNum() {
        return possibleNum;
    }

    public void setPossibleNum(Integer possibleNum) {
        this.possibleNum = possibleNum;
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

    public BigDecimal getRefundCost() {
        return refundCost;
    }

    public void setRefundCost(BigDecimal refundCost) {
        this.refundCost = refundCost;
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

    public Integer getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(Integer refundReason) {
        this.refundReason = refundReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }



    public Long getProcessId() {
        return processId;
    }



    public void setProcessId(Long processId) {
        this.processId = processId;
    }



    public Double getAvCost() {
        return avCost;
    }



    public void setAvCost(Double avCost) {
        this.avCost = avCost;
    }
}