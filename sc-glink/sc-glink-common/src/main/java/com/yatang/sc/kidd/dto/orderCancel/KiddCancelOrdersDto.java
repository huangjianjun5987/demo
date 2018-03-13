package com.yatang.sc.kidd.dto.orderCancel;

import java.io.Serializable;

/**
 * @描述:定时取消采购单详细信息
 * @类名:KiddCancelOrdersDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 17:47
 * @版本: v1.0
 */

public class KiddCancelOrdersDto implements Serializable {
    private static final long serialVersionUID = -3238512717040386945L;
    private String orderCode;   //单据编号
    private String projectCode; //项目编号
    private String orgCode;     //机构编号ERP或者是WMS；货主编码 子公司ID
    private String orderType;   //单据业务类型
    private String warehouseCode;//逻辑仓编码仓库编码



    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

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
