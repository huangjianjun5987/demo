package com.yatang.sc.glink.dto.entryOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yatang.sc.glink.dto.OrderLinesDto;
import com.yatang.sc.glink.dto.SenderInfoDto;

/**
 * create by chensiqiang
 * 增加入库单信息DTO
 */
public class EntryOrderDto implements Serializable {

    private String wareHouseCode;   //仓库编码
    private String ownerCode;       //货主编码
    private String entryOrderCode;  //入库单号
    private String projectCode;     //入库单所属项目名称
    private String ownerOrderCode;  //关联货主单据信息
    private String orderType;       //入库单业务类型
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
    private SenderInfoDto senderInfoDto;    //发货人明细信息
    private GetInfoDto getInfoDto;    //收货仓明细信息
    private List<OrderLinesDto> OrderLines;  //货品详情

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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOwnerOrderCode() {
        return ownerOrderCode;
    }

    public void setOwnerOrderCode(String ownerOrderCode) {
        this.ownerOrderCode = ownerOrderCode;
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

    public int getTotalOrderLines() {
        return totalOrderLines;
    }

    public void setTotalOrderLines(int totalOrderLines) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SenderInfoDto getSenderInfoDto() {
        return senderInfoDto;
    }

    public void setSenderInfoDto(SenderInfoDto senderInfoDto) {
        this.senderInfoDto = senderInfoDto;
    }

    public GetInfoDto getGetInfoDto() {
        return getInfoDto;
    }

    public void setGetInfoDto(GetInfoDto getInfoDto) {
        this.getInfoDto = getInfoDto;
    }

    public List<OrderLinesDto> getOrderLines() {
        return OrderLines;
    }

    public void setOrderLines(List<OrderLinesDto> orderLines) {
        OrderLines = orderLines;
    }
}
