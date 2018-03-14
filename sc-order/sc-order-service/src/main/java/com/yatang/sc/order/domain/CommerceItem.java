package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.order.states.CommerceItemStatus;

public class CommerceItem implements Serializable {
    private Long id;

    private String catalogId;

    private Long quantity;

    private Short state;

    private String stateDetail;

    private Long itemPriceInfo;

    private Long shippedQuantity;

    private Long completedQuantity;

    private Long returnQuantity;

    private String skuId;

    private String productId;

    private Boolean isSelected;

    private String type;

    private Date creationTime;

    private ItemPrice itemPrice;

    /**
     * 均价
     */
    private Double avCost;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品名称
     */
    private String productImg;


    /**
     * 属性(颜色、尺寸等)
     */
    private Map<String, String> properties;

    /**
     * 销售单位的数量 eg:几箱
     */
    private Long saleQuantity;

    /**
     * 每箱多少瓶  就是销售内装数
     */
    private Integer unitQuantity;
    /**
     * 是否整箱销售：0-否；1-是
     */
    private Integer  sellFullCase;

    /**
     * 是否异常商品
     */
    private Boolean abnormalGoods  = false;

    /**
     * 异常原因
     */
    private String abnormalResonse;
	

	public CommerceItem() {
        state = CommerceItemStatus.INIT.getStateValue();
        stateDetail = CommerceItemStatus.INIT.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public ItemPrice getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(ItemPrice itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Double getAvCost() {
        if (avCost == null) {
            return 0D;
        }
        return avCost;
    }

    public void setAvCost(Double avCost) {
        this.avCost = avCost;
    }

    public Double getSalePrice() {
        if(itemPrice !=null){
            return itemPrice.getSalePrice();
        }

        return 0d;
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

    public Integer getSellFullCase() {
        return sellFullCase;
    }

    public void setSellFullCase(Integer sellFullCase) {
        this.sellFullCase = sellFullCase;
    }

    public Boolean getAbnormalGoods() {
        return abnormalGoods;
    }

    public void setAbnormalGoods(Boolean pAbnormalGoods) {
        abnormalGoods = pAbnormalGoods;
    }

    public String getAbnormalResonse() {
        return abnormalResonse;
    }

    public void setAbnormalResonse(String pAbnormalResonse) {
        abnormalResonse = pAbnormalResonse;
    }
}