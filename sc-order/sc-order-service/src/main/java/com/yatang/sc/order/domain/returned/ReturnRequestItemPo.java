package com.yatang.sc.order.domain.returned;

import java.io.Serializable;
import java.util.Date;

public class ReturnRequestItemPo implements Serializable {
    private Long id;

    private String returnId;//销售退换货单表id

    private String catalogId;//根类目id

    private Long quantity;//下单时的数量

    private Short state;//收货状态

    private String stateDetail;//收货状态描述

    private Long itemPriceInfo;//购物车商品级别的价格信息

    private Long shippedQuantity;//配送的数量

    private Long completedQuantity;//签收数量

    private Long returnQuantity;//发生了退换货的数量

    private Long actualReturnQuantity;//实收数量

    private String productCode;//sku产品id

    private String productId;//产品id

    private String productName;//商品名称

    private Date creationTime;//创建时间

    private Long saleQuantity;//销售数量

    private Integer unitQuantity;//销售内装数

    private Double rawTotalPrice;//退换货总额

    private Double actualRawTotalPrice;//实际退换货总总额

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId == null ? null : returnId.trim();
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId == null ? null : catalogId.trim();
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail;
    }

    public Long getItemPriceInfo() {
        return itemPriceInfo;
    }

    public void setItemPriceInfo(Long itemPriceInfo) {
        this.itemPriceInfo = itemPriceInfo;
    }

    public Long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(Long shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public Long getCompletedQuantity() {
        return completedQuantity;
    }

    public void setCompletedQuantity(Long completedQuantity) {
        this.completedQuantity = completedQuantity;
    }

    public Long getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Long returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Long getActualReturnQuantity() {
        return actualReturnQuantity;
    }

    public void setActualReturnQuantity(Long actualReturnQuantity) {
        this.actualReturnQuantity = actualReturnQuantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Long getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(Long saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public Integer getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(Integer unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getRawTotalPrice() {
        return rawTotalPrice;
    }

    public void setRawTotalPrice(Double rawTotalPrice) {
        this.rawTotalPrice = rawTotalPrice;
    }

    public Double getActualRawTotalPrice() {
        return actualRawTotalPrice;
    }

    public void setActualRawTotalPrice(Double actualRawTotalPrice) {
        this.actualRawTotalPrice = actualRawTotalPrice;
    }
}