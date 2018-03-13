package com.yatang.sc.xinyi.dto.saleOrder;

import com.thoughtworks.xstream.annotations.XStreamAliasType;

import java.io.Serializable;

@XStreamAliasType("package")
public class SaleOrderJYCKPackageDto implements Serializable{

    private static final long serialVersionUID = 8266057919933045254L;

    private String logisticsCode;//物流公司编码
    private String logisticsName;//物流公司名称
    private String expressCode;//运单号

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }
}
