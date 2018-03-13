package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdPriceChangeDto implements Serializable {

    private static final long serialVersionUID = -1289581706387703258L;
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
     * 区间起始数量
     */
    private Integer startNumber;

    /**
     * 区间结束数量
     */
    private Integer endNumber;

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
        this.spId = spId == null ? null : spId.trim();
    }

    public String getSpAdrId() {
        return spAdrId;
    }

    public void setSpAdrId(String spAdrId) {
        this.spAdrId = spAdrId == null ? null : spAdrId.trim();
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId == null ? null : branchCompanyId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Integer endNumber) {
        this.endNumber = endNumber;
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
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }
}