package com.yatang.sc.sorder.vo.returnedOrder;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotBlank;



public class ReturnRequestItemVo implements Serializable {
    private static final long serialVersionUID = 5563615228232737680L;
    private Long id;

    private String returnId;//销售退换货单表id

    private String catalogId;//根类目id

    private Long quantity;//下单时的数量

    private Short state;//收货状态

    private String stateDetail;//收货状态描述

    private Long itemPriceInfo;//购物车商品级别的价格信息

    private Long shippedQuantity;//配送的数量

    private Long completedQuantity;//签收数量

    @Min(value = 1, message = "{msg.notEmpty.message}",groups = {GroupOne.class, DefaultGroup.class})
    private Long returnQuantity;//发生了退换货的数量

    private String productCode;//商品编码
    @NotBlank(message = "{msg.notEmpty.message}",groups = {GroupOne.class, DefaultGroup.class})
    private String productId;//产品id

    private Date creationTime;//创建时间

    private Long actualReturnQuantity;//实际退换货的数量


    private Long saleQuantity;//销售数量

    private Integer unitQuantity;//销售内装数


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

    public Long getActualReturnQuantity() {
        return actualReturnQuantity;
    }

    public void setActualReturnQuantity(Long actualReturnQuantity) {
        this.actualReturnQuantity = actualReturnQuantity;
    }
}