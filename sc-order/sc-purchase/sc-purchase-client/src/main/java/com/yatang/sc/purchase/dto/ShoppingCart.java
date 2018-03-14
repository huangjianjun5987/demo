package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by qiugang on 7/8/2017.
 */
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 139964337811170823L;

    private long version;

    private String userId;

    private String storeId;

    private String branchCompanyId;

    private String deliveryWarehouseCode;

    private OrderDto currentOrder;

    private String lastOrderId;

    public void updateVersion(){
        version = version + 1;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getDeliveryWarehouseCode() {
        return deliveryWarehouseCode;
    }

    public void setDeliveryWarehouseCode(String deliveryWarehouseCode) {
        this.deliveryWarehouseCode = deliveryWarehouseCode;
    }

    public OrderDto getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(OrderDto currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String getLastOrderId() {
        return lastOrderId;
    }

    public void setLastOrderId(String lastOrderId) {
        this.lastOrderId = lastOrderId;
    }

}
