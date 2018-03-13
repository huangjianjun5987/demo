package com.yatang.sc.xinyi.dto.purchase;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.yatang.sc.xinyi.dto.common.XinyiSenderInfoDto;

/**
 * @描述:心怡采购单入库信息
 * @类名:XinyiEntryOrderDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 11:40
 * @版本: v1.0
 */
@XStreamAliasType("entryOrder")
public class XinyiEntryOrderDto implements Serializable {
    private static final long serialVersionUID = 2519807304518434735L;
    private Integer totalOrderLines;//采购收货单商品行数
    private String entryOrderCode;//入库单号
    private String ownerCode;//货主编码
    private String purchaseOrderCode;//采购单号
    private String warehouseCode;//仓库编码
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date orderCreateTime;//采购单创建时间
    private String orderType;//业务类型 CGRK=采购入库
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date expectStartTime;//预期到货时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date expectEndTime;//最迟预期到货时间
    private String supplierName;//采购单供应商地点名称
    private String supplierCode;//采购单供应商地点编码
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date operateTime;//采购单审核时间
    private XinyiSenderInfoDto senderInfo;//供应商明细信息

    public Integer getTotalOrderLines() {
        return totalOrderLines;
    }

    public String getEntryOrderCode() {
        return entryOrderCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
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

    public Date getOperateTime() {
        return operateTime;
    }

    public void setTotalOrderLines(Integer totalOrderLines) {
        this.totalOrderLines = totalOrderLines;
    }

    public void setEntryOrderCode(String entryOrderCode) {
        this.entryOrderCode = entryOrderCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
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

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public XinyiSenderInfoDto getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(XinyiSenderInfoDto senderInfo) {
        this.senderInfo = senderInfo;
    }

    @Override
    public String toString() {
        return "XinyiEntryOrderDto{" +
                "totalOrderLines=" + totalOrderLines +
                ", entryOrderCode='" + entryOrderCode + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", purchaseOrderCode='" + purchaseOrderCode + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", orderCreateTime=" + orderCreateTime +
                ", orderType='" + orderType + '\'' +
                ", expectStartTime=" + expectStartTime +
                ", expectEndTime=" + expectEndTime +
                ", supplierName='" + supplierName + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", operateTime=" + operateTime +
                ", senderInfo=" + senderInfo +
                '}';
    }
}
