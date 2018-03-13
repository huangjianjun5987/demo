package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;

/**
 * @描述:采购订单推送信息
 * @类名:KiddEntryOrderDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 9:37
 * @版本: v1.0
 */

public class KiddEntryOrderDto implements Serializable {
    private static final long serialVersionUID = -371505657121505587L;
    private String wareHouseCode;   //仓库编码   (必填)
    private String ownerCode;       //货主编码    (必填)
    private String entryOrderCode;  //入库单号    (必填)
    private String projectCode;     //入库单所属项目名称
    private String ownerOrderCode;  //关联货主单据信息
    private String orderType;       //入库单业务类型    (必填)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectStartTime;   //预计到货日期
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectEndTime;     //预计最迟到货日期
    private String supplierName;    //供应商名称
    private String supplierCode;    //供应商编码
    private String arrivalMode;     //到货方式:托运、配送、自提、空运等
    private String areaCode;        //入库城市
    private String logisticsName;   //物流公司名称
    private String logisticsCode;   //物流公司编码
    private String expressCode;     //运单号
    private int totalOrderLines;    //总行数
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeERP;   //货主系统中的创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeERP;   //货主系统中的更新时间
    private String operatorName;    //制单人员
    private String operatorCode;    //制单人员ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;       //货主入库单审核完成时间
    private String remark;          //备注
    private KiddSenderInfoDto senderInfoDto;    //发货人明细信息
    private KiddGetInfoDto getInfoDto;    //收货仓明细信息
    private List<KiddOrderLinesDto> OrderLines;  //货品详情

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public void setEntryOrderCode(String entryOrderCode) {
        this.entryOrderCode = entryOrderCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public void setOwnerOrderCode(String ownerOrderCode) {
        this.ownerOrderCode = ownerOrderCode;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setExpectStartTime(Date expectStartTime) {
        this.expectStartTime = expectStartTime;
    }

    public void setExpectEndTime(Date expectEndTime) {
        this.expectEndTime = expectEndTime;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setArrivalMode(String arrivalMode) {
        this.arrivalMode = arrivalMode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public void setTotalOrderLines(int totalOrderLines) {
        this.totalOrderLines = totalOrderLines;
    }

    public void setCreateTimeERP(Date createTimeERP) {
        this.createTimeERP = createTimeERP;
    }

    public void setUpdateTimeERP(Date updateTimeERP) {
        this.updateTimeERP = updateTimeERP;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSenderInfoDto(KiddSenderInfoDto senderInfoDto) {
        this.senderInfoDto = senderInfoDto;
    }

    public void setGetInfoDto(KiddGetInfoDto getInfoDto) {
        this.getInfoDto = getInfoDto;
    }

    public void setOrderLines(List<KiddOrderLinesDto> orderLines) {
        OrderLines = orderLines;
    }

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public String getEntryOrderCode() {
        return entryOrderCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getOwnerOrderCode() {
        return ownerOrderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public Date getExpectStartTime() {
        return expectStartTime;
    }

    public Date getExpectEndTime() {
        return expectEndTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getArrivalMode() {
        return arrivalMode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public int getTotalOrderLines() {
        return totalOrderLines;
    }

    public Date getCreateTimeERP() {
        return createTimeERP;
    }

    public Date getUpdateTimeERP() {
        return updateTimeERP;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public String getRemark() {
        return remark;
    }

    public KiddSenderInfoDto getSenderInfoDto() {
        return senderInfoDto;
    }

    public KiddGetInfoDto getGetInfoDto() {
        return getInfoDto;
    }

    public List<KiddOrderLinesDto> getOrderLines() {
        return OrderLines;
    }

}
