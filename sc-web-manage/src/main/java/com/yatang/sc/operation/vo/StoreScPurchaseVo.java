package com.yatang.sc.operation.vo;

import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;

import java.io.Serializable;
@ExcelName(name="baimingdandaochu")
public class StoreScPurchaseVo implements Serializable{
    private static final long serialVersionUID = 6269272897689131702L;

    @ExcelFieldName(name="所属子公司")
    private String branchCompanyName;

    @ExcelFieldName(name="加盟商编号")
    private String franchiseeId;

    @ExcelFieldName(name="加盟商名称")
    private String franchinessName;

    @ExcelFieldName(name="门店编号")
    private String storeId;

    @ExcelFieldName(name="门店名称")
    private String storeName;

    @ExcelFieldName(name="省")
    private String provinceName;

    @ExcelFieldName(name="市")
    private String cityName;

    @ExcelFieldName(name="区")
    private String districtName;

    @ExcelFieldName(name="详细地址")
    private String address;

    @ExcelFieldName(name="联系人")
    private String contact;

    @ExcelFieldName(name="联系电话")
    private String mobilePhone;

    private Integer scPurchaseFlag;

    @ExcelFieldName(name="上线状态")
    private String scPurchaseFlagDesc;

    private String deliveryWarehouseCode;
    private String deliveryWarehouseName;

    public String getScPurchaseFlagDesc() {
        if(scPurchaseFlag==0){
            return "未上线";
        }else if(scPurchaseFlag==1){
            return "已上线";
        }else{
            return "未知";
        }
    }

    public void setScPurchaseFlagDesc(String scPurchaseFlagDesc) {
        this.scPurchaseFlagDesc = scPurchaseFlagDesc;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getFranchinessName() {
        return franchinessName;
    }

    public void setFranchinessName(String franchinessName) {
        this.franchinessName = franchinessName;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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
