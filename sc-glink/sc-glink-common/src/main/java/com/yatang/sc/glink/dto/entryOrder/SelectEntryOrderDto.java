package com.yatang.sc.glink.dto.entryOrder;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * create by chensiqiang
 * 获取入库单确认结果DTO
 */
public class SelectEntryOrderDto implements Serializable {

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;         //数据更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;           //截止时间
    private String ownerCode;       //货主编码
    private String wareHouseCode;   //WMSA自定义编码
    private String projectCode;     //项目编号
    private String orderType;       //订单类型
    private String entryOrderCode;  //入库单号
    private Integer pageNo;             //页码
    private Integer pageSize;           //每页显示记录数

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

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
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

    public String getEntryOrderCode() {
        return entryOrderCode;
    }

    public void setEntryOrderCode(String entryOrderCode) {
        this.entryOrderCode = entryOrderCode;
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
