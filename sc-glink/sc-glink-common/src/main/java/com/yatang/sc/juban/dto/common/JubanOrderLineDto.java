package com.yatang.sc.juban.dto.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:心怡仓库需要的商品信息
 * @类名:XinyiOrderLineDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 14:59
 * @版本: v1.0
 */
@XStreamAliasType("orderLine")
public class JubanOrderLineDto implements Serializable {
    private static final long serialVersionUID = 3566635426767314078L;
    private String orderLineNo;//收货单明细ID
    private String ownerCode;//货主编码
    private String itemCode;//商品编码
    private String itemId=itemCode;//仓储系统商品ID
    private String itemName;//商品名称
    private int planQty;//应收商品数量
    private int actualQty;//实际收获数量
    private String skuProperty;//商品属性
    private Double purchasePrice=0.0;//采购价
    private Double retailPrice=0.0;//零售价
    private String inventoryType;//库存类型
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date productDate;//商品生产日期
    @JSONField(format = "yyyy-MM-dd")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date expireDate;//商品过期日期
    private String produceCode;//生产批号
    private String batchCode;//批次编码

    public int getActualQty() {
        return actualQty;
    }

    public void setActualQty(int actualQty) {
        this.actualQty = actualQty;
    }

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPlanQty() {
        return planQty;
    }

    public String getSkuProperty() {
        return skuProperty;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public Date getProductDate() {
        return productDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Override
    public String toString() {
        return "XinyiOrderLineDto{" +
                "orderLineNo='" + orderLineNo + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", planQty=" + planQty +
                ", skuProperty='" + skuProperty + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", retailPrice=" + retailPrice +
                ", inventoryType='" + inventoryType + '\'' +
                ", productDate=" + productDate +
                ", expireDate=" + expireDate +
                ", produceCode='" + produceCode + '\'' +
                ", batchCode='" + batchCode + '\'' +
                '}';
    }
}
