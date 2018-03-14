package com.yatang.sc.operation.vo;

import java.io.Serializable;

public class ParamScPurchaseVo implements Serializable {

    private static final long serialVersionUID = -6514074913090537727L;

    /**门店编号*/
    private String storeId;

    /**是否支持供应链采购(0 不支持 1 支持)*/
    private Integer scPurchaseFlag;

    /**送货仓库编码*/
    private String deliveryWarehouseCode;

    /**仓库名字*/
    private String deliveryWarehouseName;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getScPurchaseFlag() {
        return scPurchaseFlag;
    }

    public void setScPurchaseFlag(Integer scPurchaseFlag) {
        this.scPurchaseFlag = scPurchaseFlag;
    }

    public String getDeliveryWarehouseCode() {
        return deliveryWarehouseCode;
    }

    public void setDeliveryWarehouseCode(String deliveryWarehouseCode) {
        this.deliveryWarehouseCode = deliveryWarehouseCode;
    }

    public String getDeliveryWarehouseName() {
        return deliveryWarehouseName;
    }

    public void setDeliveryWarehouseName(String deliveryWarehouseName) {
        this.deliveryWarehouseName = deliveryWarehouseName;
    }
}
