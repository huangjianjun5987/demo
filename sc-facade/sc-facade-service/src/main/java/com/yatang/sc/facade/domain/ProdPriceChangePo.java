package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdPriceChangePo implements Serializable {
    private static final long serialVersionUID = -9032376564371741130L;
    /**
     *
     */
    private Long id;

    /**
     * 变更类型:0:采购进价变更;1:售价变更
     */
    private Integer changeType;

    /**
     * 供应商ID
     */
    private String spId;

    /**
     * 供应商地点ID
     */
    private String spAdrId;

    /**
     * 子公司id
     */
    private String branchCompanyId;

    /**
     * 商品ID
     */
    private String productId;


    /**
     * 毛利率
     */
    private BigDecimal grossProfitMargin;

    /**
     * 当前价格
     */
    private BigDecimal price;

    /**
     * 提交价格
     */
    private BigDecimal newestPrice;

    /**
     * 调价百分比
     */
    private BigDecimal percentage;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private String createUserId;


    private Long priceId;//价格变动id
    /**
     * 商品ID
     */
    private String productCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpAdrId() {
        return spAdrId;
    }

    public void setSpAdrId(String spAdrId) {
        this.spAdrId = spAdrId;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getGrossProfitMargin() {
        return grossProfitMargin;
    }

    public void setGrossProfitMargin(BigDecimal grossProfitMargin) {
        this.grossProfitMargin = grossProfitMargin;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNewestPrice() {
        return newestPrice;
    }

    public void setNewestPrice(BigDecimal newestPrice) {
        this.newestPrice = newestPrice;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}