package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;
import java.util.Date;

public class ReturnRequestItemDto implements Serializable {

    private static final long serialVersionUID = 247930303107527177L;
    private Long id;

    private String returnId;//销售退换货单表id

    private String catalogId;//根类目id

    private Long quantity;//下单时的数量

    private Short state;//退货单总状态

    private String stateDetail;//退货单总状态描述

    private Long itemPriceInfo;//购物车商品级别的价格信息

    private Long shippedQuantity;//配送的数量

    private Long completedQuantity;//签收数量

    private Long returnQuantity;//发生了退换货的数量

    private Long actualReturnQuantity;//实际退换货的数量

    private String productCode;//商品编码

    private String productName;//商品名称

    private String productId;//产品id

    private Date creationTime;//创建时间

    private Double avCost;//平均价格

    private Long saleQuantity;//销售数量

    private Integer unitQuantity;//销售内装数

    private Double rawTotalPrice;//退换货总额

    private Double actualRawTotalPrice;//实际退换货总总额

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

    public Double getAvCost() {
        return avCost;
    }

    public void setAvCost(Double avCost) {
        this.avCost = avCost;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getActualReturnQuantity() {
        return actualReturnQuantity;
    }

    public void setActualReturnQuantity(Long actualReturnQuantity) {
        this.actualReturnQuantity = actualReturnQuantity;
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