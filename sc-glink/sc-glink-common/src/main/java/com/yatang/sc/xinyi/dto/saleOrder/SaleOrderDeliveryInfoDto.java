package com.yatang.sc.xinyi.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 销售出库单物流相关信息创建DTO
 * @作者: yangshuang
 * @创建时间: 2017年7月31日20:50:50
 */

@XStreamAliasType("deliveryOrder")
public class SaleOrderDeliveryInfoDto implements Serializable {

    private static final long serialVersionUID = 4958199658540591396L;
    private String deliveryOrderCode;//出货单号

    private String orderType;//出货单类型 PTCK=普通出库单

    private String warehouseCode;//逻辑仓库编码


    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date createTime;//发货单创建时间

    private SaleOrderReceiverInfoDto receiverInfo;//收货人信息

    private List<SaleOrderRelateOrderDto> relatedOrders;
    /**
     * 发货单
     */
    private String logisticsCode;//物流公司编码

    private String sourcePlatformCode;//订单来源平台编码

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date placeOrderTime;//前台订单 (店铺订单) 创建时间 (下单时间)

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date operateTime;//操作 (审核) 时间

    private String shopNick;//店铺名称

    private SaleOrderSenderInfoDto senderInfo;//发货人信息

    private double actualOrderPrice;//订单总价

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SaleOrderReceiverInfoDto getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(SaleOrderReceiverInfoDto receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public List<SaleOrderRelateOrderDto> getRelatedOrders() {
        return relatedOrders;
    }

    public void setRelatedOrders(List<SaleOrderRelateOrderDto> relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getSourcePlatformCode() {
        return sourcePlatformCode;
    }

    public void setSourcePlatformCode(String sourcePlatformCode) {
        this.sourcePlatformCode = sourcePlatformCode;
    }

    public Date getPlaceOrderTime() {
        return placeOrderTime;
    }

    public void setPlaceOrderTime(Date placeOrderTime) {
        this.placeOrderTime = placeOrderTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }


    public SaleOrderSenderInfoDto getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SaleOrderSenderInfoDto senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getShopNick() {
        return shopNick;
    }

    public void setShopNick(String shopNick) {
        this.shopNick = shopNick;
    }

    public double getActualOrderPrice() {
        return actualOrderPrice;
    }

    public void setActualOrderPrice(double actualOrderPrice) {
        this.actualOrderPrice = actualOrderPrice;
    }
}

