package com.yatang.sc.glink.dto;

import java.io.Serializable;

/**
 * create by chensiqiang
 * 取消单据订单信息DTO
 */
public class CancelOrdersDto implements Serializable {

    private String orderCode;   //货主单据编号
    private String projectCode; //项目编号
    private String orgCode;     //机构编号ERP或者是WMS；
    private String orderType;   //单据业务类型

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
