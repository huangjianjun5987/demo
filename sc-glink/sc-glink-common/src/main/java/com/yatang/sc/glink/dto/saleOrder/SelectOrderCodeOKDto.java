package com.yatang.sc.glink.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.io.Serializable;

/**
 * @描述: 获取订单确认结果DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class SelectOrderCodeOKDto implements Serializable {
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//查询开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//查询截止时间
    private String orgCode;//机构自定义编码
    private String warehouseCode;//WMS自定义编码
    private String projectCode;//项目编号
    private String orderType;//订单类型
    private String deliveryOrderCode;//发货单号
    private Integer pageNo;//要请求第几页
    private Integer pageSize;//总页码数

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
