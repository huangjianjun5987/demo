package com.yatang.sc.thirdorder.busi.dto;

import java.io.Serializable;

public class BusiOrderDto implements Serializable{

    private static final long serialVersionUID = -601000681318883911L;
    private String orderId;
    private String merchantLogisticsInfo;
    private String merchantLogisticsNumber;
    private String merchantLogisticsCompanyCode;
    private String logisticsCompanyType;
    private String state;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantLogisticsInfo() {
        return merchantLogisticsInfo;
    }

    public void setMerchantLogisticsInfo(String merchantLogisticsInfo) {
        this.merchantLogisticsInfo = merchantLogisticsInfo;
    }

    public String getMerchantLogisticsNumber() {
        return merchantLogisticsNumber;
    }

    public void setMerchantLogisticsNumber(String merchantLogisticsNumber) {
        this.merchantLogisticsNumber = merchantLogisticsNumber;
    }

    public String getMerchantLogisticsCompanyCode() {
        return merchantLogisticsCompanyCode;
    }

    public void setMerchantLogisticsCompanyCode(String merchantLogisticsCompanyCode) {
        this.merchantLogisticsCompanyCode = merchantLogisticsCompanyCode;
    }

    public String getLogisticsCompanyType() {
        return logisticsCompanyType;
    }

    public void setLogisticsCompanyType(String logisticsCompanyType) {
        this.logisticsCompanyType = logisticsCompanyType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
