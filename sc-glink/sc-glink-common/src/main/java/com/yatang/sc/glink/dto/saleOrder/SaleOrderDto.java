package com.yatang.sc.glink.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 销售订单DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class SaleOrderDto implements Serializable {

    private String deliverOrderType;//发货单类型
    private String orderCode;//订单编号
    private String orderCodeWms;//WMS单号
    private String orderCode2;//编号2
    private String preDeliveryOrderCode;//订单类型：HHCK=换货出库时必填,关联货主原ERP订单号
    private String preDeliveryOrderCodeWms;//订单类型：HHCK=换货出库时必填,关联WMS原订单号
    private String projectCode;//项目编号
    private String warehouseCode;//仓储商自定义编码
    private String ownerCode;//货主编号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;//货主订单创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operTimeERP;//货主订单审核通过时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;//订单付款时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;//期望送货时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date earliestDeliveryTime;//最早可送时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date latestDeliveryTime;//客户要求最晚送达时间
    private String consignedeeCode;//客户(收货人)编号
    private String shippingMethod;//配送方式
    private String logisticsCode;//物流公司编码
    private String logisticsName;//物流公司名称
    private String expressCode;//运单号
    private String pickingAddress;//提货地址
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date earliestPickingTime;//提货时间-开始
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date latestPickingTime;//提货时间-截至
    private String sales;//销售员
    private String saleTel;//销售员电话
    private String callCenterTel;//客服电话
    private String paymentMethod;//货款方式
    private String payStatus;//支付状态
    private String totalAmount;//订单总金额
    private String amount;//代收款金额
    private String gotAmount;//已收金额
    private String itemAmount;//商品总金额
    private String discountAmount;//订单折扣金额
    private String freight;//配送费用
    private String serviceFee;//COD服务费
    private String isUrgency;//是否紧急
    private String isInvoiceFlag;//是否需要发票
    private String remark;//备注
    private String shopName;//商铺
    private String remark1;//扩展字段1
    private String remark2;//扩展字段2
    private String remark3;//扩展字段3
    private String remark4;//扩展字段4
    private String remark5;//扩展字段5
    private String remark6;//扩展字段6
    private String remark7;//扩展字段7
    private String salePlate;//销售平台
    private SaleGetInfoDto getInfo;//收件人信息
    private SaleSendInfoDto sendInfo;//发货人信息
    private List<SaleCargoDto> cargo;//物品
    private List<InvoiceDto> invoices;//发票明细列表

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

    public String getOrderCode2() {
        return orderCode2;
    }

    public void setOrderCode2(String orderCode2) {
        this.orderCode2 = orderCode2;
    }

    public String getPreDeliveryOrderCode() {
        return preDeliveryOrderCode;
    }

    public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
        this.preDeliveryOrderCode = preDeliveryOrderCode;
    }

    public String getPreDeliveryOrderCodeWms() {
        return preDeliveryOrderCodeWms;
    }

    public void setPreDeliveryOrderCodeWms(String preDeliveryOrderCodeWms) {
        this.preDeliveryOrderCodeWms = preDeliveryOrderCodeWms;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOperTimeERP() {
        return operTimeERP;
    }

    public void setOperTimeERP(Date operTimeERP) {
        this.operTimeERP = operTimeERP;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getEarliestDeliveryTime() {
        return earliestDeliveryTime;
    }

    public void setEarliestDeliveryTime(Date earliestDeliveryTime) {
        this.earliestDeliveryTime = earliestDeliveryTime;
    }

    public Date getLatestDeliveryTime() {
        return latestDeliveryTime;
    }

    public void setLatestDeliveryTime(Date latestDeliveryTime) {
        this.latestDeliveryTime = latestDeliveryTime;
    }

    public String getConsignedeeCode() {
        return consignedeeCode;
    }

    public void setConsignedeeCode(String consignedeeCode) {
        this.consignedeeCode = consignedeeCode;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
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

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getPickingAddress() {
        return pickingAddress;
    }

    public void setPickingAddress(String pickingAddress) {
        this.pickingAddress = pickingAddress;
    }

    public Date getEarliestPickingTime() {
        return earliestPickingTime;
    }

    public void setEarliestPickingTime(Date earliestPickingTime) {
        this.earliestPickingTime = earliestPickingTime;
    }

    public Date getLatestPickingTime() {
        return latestPickingTime;
    }

    public void setLatestPickingTime(Date latestPickingTime) {
        this.latestPickingTime = latestPickingTime;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getSaleTel() {
        return saleTel;
    }

    public void setSaleTel(String saleTel) {
        this.saleTel = saleTel;
    }

    public String getCallCenterTel() {
        return callCenterTel;
    }

    public void setCallCenterTel(String callCenterTel) {
        this.callCenterTel = callCenterTel;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGotAmount() {
        return gotAmount;
    }

    public void setGotAmount(String gotAmount) {
        this.gotAmount = gotAmount;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getIsUrgency() {
        return isUrgency;
    }

    public void setIsUrgency(String isUrgency) {
        this.isUrgency = isUrgency;
    }

    public String getIsInvoiceFlag() {
        return isInvoiceFlag;
    }

    public void setIsInvoiceFlag(String isInvoiceFlag) {
        this.isInvoiceFlag = isInvoiceFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5;
    }

    public String getRemark6() {
        return remark6;
    }

    public void setRemark6(String remark6) {
        this.remark6 = remark6;
    }

    public String getRemark7() {
        return remark7;
    }

    public void setRemark7(String remark7) {
        this.remark7 = remark7;
    }

    public String getSalePlate() {
        return salePlate;
    }

    public void setSalePlate(String salePlate) {
        this.salePlate = salePlate;
    }

    public SaleGetInfoDto getGetInfo() {
        return getInfo;
    }

    public void setGetInfo(SaleGetInfoDto getInfo) {
        this.getInfo = getInfo;
    }

    public SaleSendInfoDto getSendInfo() {
        return sendInfo;
    }

    public void setSendInfo(SaleSendInfoDto sendInfo) {
        this.sendInfo = sendInfo;
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
