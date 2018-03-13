package com.yatang.sc.glink.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * create by chensiqiang
 *推送订单信息DTO
 */
public class PushOrdersDto implements Serializable {

    private String orderCode;       //订单编号
    private String ownerCode;       //货主编码
    private int dataType;           //数据类型
    private String currentStatus;   //最新状态
    private String orderType;       //订单类型
    private String orderCodeWMS;    //仓库编码
    private String operatorCode;    //操作员编码
    private String operatorName;    //操作员姓名
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;      //操作时间
    private String operatorInfo;    //操作内容
    private String remark;           //备注
    private String logisticsName;   //承运商名称
    private String expressCode;     //运单号
    private String driverName;      //司机名称
    private String driverPhone;     //司机电话
    private String deliveryTime;    //预计送达时间

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrdertype(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderCodeWMS() {
        return orderCodeWMS;
    }

    public void setOrderCodeWMS(String orderCodeWMS) {
        this.orderCodeWMS = orderCodeWMS;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getOperatorInfo() {
        return operatorInfo;
    }

    public void setOperatorInfo(String operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
