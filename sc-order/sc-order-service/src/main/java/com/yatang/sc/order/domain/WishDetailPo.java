package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 心愿单详情po
 */
public class WishDetailPo implements Serializable {
    private static final long serialVersionUID = -6861450096018187307L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 心愿单id
     */
    private Long wishListId;

    /**
     * 门店编号
     */
    private String storeId;

    /**
     * 加盟商编号
     */
    private String franchiserId;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 数量
     */
    private Long quantity;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * sc_wish_detail
     */
    private String gbCode;
    private String productCode;
    private String productName;
    private String productImg;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGbCode() {
        return gbCode;
    }

    public void setGbCode(String gbCode) {
        this.gbCode = gbCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWishListId() {
        return wishListId;
    }

    public void setWishListId(Long wishListId) {
        this.wishListId = wishListId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public String getFranchiserId() {
        return franchiserId;
    }

    public void setFranchiserId(String franchiserId) {
        this.franchiserId = franchiserId == null ? null : franchiserId.trim();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}