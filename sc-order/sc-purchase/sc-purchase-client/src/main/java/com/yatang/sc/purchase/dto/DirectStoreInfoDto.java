package com.yatang.sc.purchase.dto;

import java.io.Serializable;

public class DirectStoreInfoDto implements Serializable{
    private static final long serialVersionUID = -7889348793195582876L;

    private String receivingAddress;  //收货地址

    private String consignee;    //收货人

    private String phone;      //收货人电话

    private String				branchCompanyId;  //分公司Id

    private String				deliveryWarehouseCode;    //送货仓库编码

    private String storeId;    //门店id

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
