package com.yatang.sc.kidd.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 物流信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 13:53
 * @版本: v1.0
 */
public class KiddSaleOrderDeliveryInfoDto implements Serializable {
    private static final long serialVersionUID = 3386050089915261650L;

    private String deliveryOrderCode;//出货单号 Xinyi


    //际链和心怡分别的属性
    // private String deliveryOrderType;//订单类型：FHCK=发货出库

    // private String orderType;//出货单类型 PTCK=普通出库单  Xinyi
    private String preDeliveryOrderCode;//原出库单号 (必填）

    private String scOrderType;//供应链订单类型

    private String warehouseCode;//逻辑仓库编码 Xinyi

    private String ownerCode;//货主编码 子公司ID GLink

    private String logisticsCode;

    private String logisticsName;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//发货单创建时间(glink)  出库单创建时间(xinyi)

    private KiddSaleOrderReceiverInfoDto receiverInfo;//收货人信息

    private Date payTime;//付款时间 GLink字段

    private List<KiddSaleOrderRelateOrderDto> relatedOrders;//销售订单关联单据类型

    private double actualOrderPrice;//订单总价

    private String remarkAddress;//传加盟商名称，规则如下:雅堂小超_No.000999(凯华丽景便利店)

    public String getRemarkAddress() {
        return remarkAddress;
    }

    public void setRemarkAddress(String remarkAddress) {
        this.remarkAddress = remarkAddress;
    }

    public String getPreDeliveryOrderCode() {
        return preDeliveryOrderCode;
    }

    public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
        this.preDeliveryOrderCode = preDeliveryOrderCode;
    }

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public String getScOrderType() {
        return scOrderType;
    }

    public void setScOrderType(String scOrderType) {
        this.scOrderType = scOrderType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }


    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public KiddSaleOrderReceiverInfoDto getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(KiddSaleOrderReceiverInfoDto receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public List<KiddSaleOrderRelateOrderDto> getRelatedOrders() {
        return relatedOrders;
    }

    public void setRelatedOrders(List<KiddSaleOrderRelateOrderDto> relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

    public double getActualOrderPrice() {
        return actualOrderPrice;
    }

    public void setActualOrderPrice(double actualOrderPrice) {
        this.actualOrderPrice = actualOrderPrice;
    }
}
