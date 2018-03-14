package com.yatang.sc.sorder.vo;

import java.io.Serializable;

public class DirectStoreInfoVo implements Serializable {

    private static final long serialVersionUID = -9091979209542260118L;

    private String             receivingAddress;  //收货地址

    private String             consignee;    //收货人

    private String             phone;      //收货人电话

    private String				branchCompanyId;  //分公司Id

    private String				deliveryWarehouseCode;    //送货仓库编码

    private String             storeId;                //门店id

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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
