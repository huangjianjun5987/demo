package com.yatang.sc.inventory.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ImAdjustmentItemPo implements Serializable {


    private static final long serialVersionUID = 7967444648121015887L;
    private Long id; //PK

    private String adjustmentId;//调整单id

    private String productId;//商品id

    private String productCode;//商品编号

    private String productName;//商品名称

    private BigDecimal avCost;//移动平均价

    private Long stockOnHand;//现有库存

    private Long quantity;//调整数量

    private BigDecimal adjustmentCost;//调整成本额

    private Date productDate;//商品生产日期 YYYY-MM-DD

    private Date expireDate;//商品过期日期 YYYY-MM-DD

    /********记录tranData用 start*************/

    private String groups;//部类

    private String dept;//大类

    private String classs;//中类

    private String subclass;//小类
    /********记录tranData用 end*************/

    private Date createTime;

    private Date modifyTime;

    private String createUserId;

    private String modifyUserId;

    @Override
    public String toString() {
        return "ImAdjustmentItemPo{" +
                "id=" + id +
                ", adjustmentId='" + adjustmentId + '\'' +
                ", productId='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", avCost=" + avCost +
                ", stockOnHand=" + stockOnHand +
                ", quantity=" + quantity +
                ", adjustmentCost=" + adjustmentCost +
                ", productDate=" + productDate +
                ", expireDate=" + expireDate +
                ", groups='" + groups + '\'' +
                ", dept='" + dept + '\'' +
                ", classs='" + classs + '\'' +
                ", subclass='" + subclass + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId='" + createUserId + '\'' +
                ", modifyUserId='" + modifyUserId + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdjustmentId(String adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAvCost(BigDecimal avCost) {
        this.avCost = avCost;
    }

    public void setStockOnHand(Long stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setAdjustmentCost(BigDecimal adjustmentCost) {
        this.adjustmentCost = adjustmentCost;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Long getId() {
        return id;
    }

    public String getAdjustmentId() {
        return adjustmentId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getAvCost() {
        return avCost;
    }

    public Long getStockOnHand() {
        return stockOnHand;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getAdjustmentCost() {
        return adjustmentCost;
    }

    public Date getProductDate() {
        return productDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public String getGroups() {
        return groups;
    }

    public String getDept() {
        return dept;
    }

    public String getClasss() {
        return classs;
    }

    public String getSubclass() {
        return subclass;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }
}