package com.yatang.sc.glink.dto.returnrequest;

import com.alibaba.fastjson.annotation.JSONField;
import com.yatang.sc.glink.dto.OrderLinesDto;
import com.yatang.sc.glink.dto.SenderInfoDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述:封装调用际链创建退货单参数
 * @类名:ReturnOrderDto
 * @作者: lvheping
 * @创建时间: 2017/10/17 15:05
 * @版本: v1.0
 */

public class ReturnOrderDto implements Serializable {
    private static final long serialVersionUID = -9186235859869932921L;
    private String wareHouseCode;//仓库编码，所属仓储商的自定义编码
    private String ownerCode;//货主编码，所属货主的自定义编码
    private String entryOrderCode;//入库单号(非中文)。货主系统的入库单号
    private String preDeliveryOrderCode;//原出库单号
    private String saleOrderCodeOwner;//orderType=THRK或者HHRK时为货主发货单号
    private String saleOrderCodeWMS;//orderType=THRK或者HHRK时为WMS发货单号
    private String orderType;//入库单业务类型：THRK=退货入库，HHRK=换货入库；
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectStartTime;//预计到货日期yyyy-MM-ddHH:mm:ss
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectEndTime;//最迟预期到货时间yyyy-MM-ddHH:mm:ss
    private String supplierName;//供应商名称
    private String supplierCode;//供应商编码
    private String arrivalMode;//到货方式，托运、配送、自提、空运等
    private String areaCode;//入库城市
    private String logisticsName;//物流公司名称
    private String logisticsCode;//物流公司编码。SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通、ZTO=中通(ZTO)、HTKY=百世汇通、UC=优速、STO=申通、TTKDEX=天天快递、QFKD=全峰、FAST=快捷、POSTB=邮政小包、GTO=国通、YUNDA=韵达,OTHER=其他
    private String expressCode;//运单号
    private String totalOrderLines;//总行数
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeERP;//货主系统中，这个单的创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeERP;//货主系统中，这个单的更新时间
    private String operatorName;//货主传过来，制单人/员
    private String operatorCode;//货主传过来，制单人/员ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;//货主出库单的审核完成时间
    private String returnReason;//退货原因入库单类型为HHRU或者THRH入库时输入
    private String remark;//备注
    private SenderInfoDto senderInfo;//发货人信息
    private List<OrderLinesDto> orderLines;//商品详情

    public String getPreDeliveryOrderCode() {
        return preDeliveryOrderCode;
    }

    public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
        this.preDeliveryOrderCode = preDeliveryOrderCode;
    }

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getEntryOrderCode() {
        return entryOrderCode;
    }

    public void setEntryOrderCode(String entryOrderCode) {
        this.entryOrderCode = entryOrderCode;
    }

    public String getSaleOrderCodeOwner() {
        return saleOrderCodeOwner;
    }

    public void setSaleOrderCodeOwner(String saleOrderCodeOwner) {
        this.saleOrderCodeOwner = saleOrderCodeOwner;
    }

    public String getSaleOrderCodeWMS() {
        return saleOrderCodeWMS;
    }

    public void setSaleOrderCodeWMS(String saleOrderCodeWMS) {
        this.saleOrderCodeWMS = saleOrderCodeWMS;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getExpectStartTime() {
        return expectStartTime;
    }

    public void setExpectStartTime(Date expectStartTime) {
        this.expectStartTime = expectStartTime;
    }

    public Date getExpectEndTime() {
        return expectEndTime;
    }

    public void setExpectEndTime(Date expectEndTime) {
        this.expectEndTime = expectEndTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getArrivalMode() {
        return arrivalMode;
    }

    public void setArrivalMode(String arrivalMode) {
        this.arrivalMode = arrivalMode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getTotalOrderLines() {
        return totalOrderLines;
    }

    public void setTotalOrderLines(String totalOrderLines) {
        this.totalOrderLines = totalOrderLines;
    }

    public Date getCreateTimeERP() {
        return createTimeERP;
    }

    public void setCreateTimeERP(Date createTimeERP) {
        this.createTimeERP = createTimeERP;
    }

    public Date getUpdateTimeERP() {
        return updateTimeERP;
    }

    public void setUpdateTimeERP(Date updateTimeERP) {
        this.updateTimeERP = updateTimeERP;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SenderInfoDto getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfoDto senderInfo) {
        this.senderInfo = senderInfo;
    }

    public List<OrderLinesDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLinesDto> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "ReturnOrderDto{" +
                "wareHouseCode='" + wareHouseCode + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", entryOrderCode='" + entryOrderCode + '\'' +
                ", preDeliveryOrderCode='" + preDeliveryOrderCode + '\'' +
                ", saleOrderCodeOwner='" + saleOrderCodeOwner + '\'' +
                ", saleOrderCodeWMS='" + saleOrderCodeWMS + '\'' +
                ", orderType='" + orderType + '\'' +
                ", expectStartTime=" + expectStartTime +
                ", expectEndTime=" + expectEndTime +
                ", supplierName='" + supplierName + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", arrivalMode='" + arrivalMode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", logisticsName='" + logisticsName + '\'' +
                ", logisticsCode='" + logisticsCode + '\'' +
                ", expressCode='" + expressCode + '\'' +
                ", totalOrderLines='" + totalOrderLines + '\'' +
                ", createTimeERP=" + createTimeERP +
                ", updateTimeERP=" + updateTimeERP +
                ", operatorName='" + operatorName + '\'' +
                ", operatorCode='" + operatorCode + '\'' +
                ", operateTime=" + operateTime +
                ", returnReason='" + returnReason + '\'' +
                ", remark='" + remark + '\'' +
                ", senderInfo=" + senderInfo +
                ", orderLines=" + orderLines +
                '}';
    }
}
