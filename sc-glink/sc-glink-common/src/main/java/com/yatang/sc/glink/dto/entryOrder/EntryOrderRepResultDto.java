package com.yatang.sc.glink.dto.entryOrder;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * create by chensiqiang
 * time 2017/8/1 10:45
 * 获取入库确认结果响应结果DTO
 */
public class EntryOrderRepResultDto implements Serializable {

    private String entryOrderCode;      //货主入库单编号
    private String wareHouseCode;       //仓库编码
    private String ownerCode;           //货主编码
    private String ownerOrderCode;      //货主产生的单号
    private String saleOrderCodeOwner;  //orderType=THRK或者HHRK时为货主发货单号
    private String saleOrderCodeWMS;    //orderType=THRK或者HHRK时为WMS发货单号
    private String orderType;           //入库单业务类型
    private String returnReason;        //退货原因入库单类型为HHRU或者THRH入库时输入
    private String remark;              //备注
    private String status;              //入库单状态
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;         //数据状态最新更新时间
    private int totalOrderLines;        //orderLines的总条数
    private List<OrderLinesRepDto> orderLines;  //订单明细信息

    public String getEntryOrderCode() {
        return entryOrderCode;
    }

    public void setEntryOrderCode(String entryOrderCode) {
        this.entryOrderCode = entryOrderCode;
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

    public String getOwnerOrderCode() {
        return ownerOrderCode;
    }

    public void setOwnerOrderCode(String ownerOrderCode) {
        this.ownerOrderCode = ownerOrderCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public int getTotalOrderLines() {
        return totalOrderLines;
    }

    public void setTotalOrderLines(int totalOrderLines) {
        this.totalOrderLines = totalOrderLines;
    }

    public List<OrderLinesRepDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLinesRepDto> orderLines) {
        this.orderLines = orderLines;
    }
}
