package com.yatang.sc.glink.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

/**
 * @描述: 销售订单查询结果响应DTO
 * @作者: wangcheng
 * @创建时间: 2017年8月1日19:20:50
 */
public class SaleOrderRepDto implements Serializable{
    private String deliverOrderType;//订单类型：XSCK=销售出库
    private String orderCode;//销售订单号
    private String orderCodeWms;//WMS系统中生成的订单号
    private String warehouseCode;//物理仓编码
    private String status;//主要记录数据处理状态
    private String operatorCode;//当前状态操作员编码
    private String operatorName;//当前状态操作员姓名
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;//当前状态操作时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date orderConfirmTime;//订单完成时间
    private double storageFee;//仓储费用(18,2)
    private String packagesNum;//包裹数
    private String actualVolume;//所有包裹总的实际出库的体积
    private String actualWeight;//所有包裹总的实际出库的重量
    private String remark;
    private List<SalePackageDto> packages;//包裹没有编码时packages不用传值
    private List<SaleCargoDto> cargo;//物品
    private List<InvoiceDto> invoices;//明细信息

    public String getDeliverOrderType() {
        return deliverOrderType;
    }

    public void setDeliverOrderType(String deliverOrderType) {
        this.deliverOrderType = deliverOrderType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCodeWms() {
        return orderCodeWms;
    }

    public void setOrderCodeWms(String orderCodeWms) {
        this.orderCodeWms = orderCodeWms;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Date getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(Date orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }

    public double getStorageFee() {
        return storageFee;
    }

    public void setStorageFee(double storageFee) {
        this.storageFee = storageFee;
    }

    public String getPackagesNum() {
        return packagesNum;
    }

    public void setPackagesNum(String packagesNum) {
        this.packagesNum = packagesNum;
    }

    public String getActualVolume() {
        return actualVolume;
    }

    public void setActualVolume(String actualVolume) {
        this.actualVolume = actualVolume;
    }

    public String getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(String actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SalePackageDto> getPackages() {
        return packages;
    }

    public void setPackages(List<SalePackageDto> packages) {
        this.packages = packages;
    }

    public List<SaleCargoDto> getCargo() {
        return cargo;
    }

    public void setCargo(List<SaleCargoDto> cargo) {
        this.cargo = cargo;
    }

    public List<InvoiceDto> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDto> invoices) {
        this.invoices = invoices;
    }
}
