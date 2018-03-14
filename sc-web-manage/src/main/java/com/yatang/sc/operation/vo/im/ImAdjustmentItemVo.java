package com.yatang.sc.operation.vo.im;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @描述: 库存调整单商品
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/29 下午3:40
 * @版本: v1.0
 */

public class ImAdjustmentItemVo implements Serializable {


    private static final long serialVersionUID = -1716202173129387421L;
    private Long id; //PK

    private String adjustmentId;//调整单id

    private String productId;//商品id

    @NotBlank(message = "{msg.notEmpty.message}")
    private String productCode;//商品编号

    private String productName;//商品名称


    private BigDecimal avCost;//移动平均价

    private Long stockOnHand;//现有库存

    @Min(value = 1, message = "{msg.min.message}")
    private Long quantity;//调整数量

    private BigDecimal adjustmentCost;//调整成本额

    private Date createTime;

    private Date modifyTime;

    private String createUserId;

    private String modifyUserId;

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

    @Override
    public String toString() {
        return "ImAdjustmentItemVo{" +
                "id=" + id +
                ", adjustmentId='" + adjustmentId + '\'' +
                ", productId='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", avCost=" + avCost +
                ", stockOnHand=" + stockOnHand +
                ", quantity=" + quantity +
                ", adjustmentCost=" + adjustmentCost +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId='" + createUserId + '\'' +
                ", modifyUserId='" + modifyUserId + '\'' +
                '}';
    }
}