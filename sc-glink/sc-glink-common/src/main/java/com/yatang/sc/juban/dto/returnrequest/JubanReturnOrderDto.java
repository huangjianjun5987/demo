package com.yatang.sc.juban.dto.returnrequest;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.yatang.sc.juban.dto.common.JubanSenderInfoDto;

/**
 * @描述:封装创建退货单的基本信息
 * @类名:ReturnOrderDto
 * @作者: lvheping
 * @创建时间: 2017/10/17 11:14
 * @版本: v1.0
 */
@XStreamAliasType("returnOrder")
public class JubanReturnOrderDto implements Serializable {
    private static final long serialVersionUID = -3326902978769113645L;
    private String returnOrderCode;//ERP的退货入库单编码,
    private String returnOrderId;//仓库系统订单编码
    private String warehouseCode;//仓库编码
    private String orderType;//单据类型,THRK=退货入库，HHRK=换货入库(心怡暂时不支持)
    private String orderFlag;//用字符串格式来表示订单标记列表：比如VISIT^ SELLER_AFFORD^SYNC_RETURN_BILL 等, 中间用“^”来隔开 订单标记list (所有字母全部大写) ： VISIT=上门；SELLER_AFFORD=是否卖家承担运费 (默认是) ；SYNC_RETURN_BILL=同时退回发票
    private String preDeliveryOrderCode;//原出库单号（ERP分配）
    private String preDeliveryOrderId;//原出库单号（WMS分配）
    private String logisticsCode;//物流公司编码SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通  、ZTO=中通 (ZTO) 、HTKY=百世汇通、UC=优速、STO=申通、TTKDEX=天天快递  、QFKD=全峰、FAST=快捷、POSTB=邮政小包  、GTO=国通、YUNDA=韵达、JD=京东配送、DD=当当宅配、AMAZON=亚马逊物流、OTHER=其他
    private String logisticsName;//物流公司名称
    private String expressCode;//运单号
    private String returnReason;//退货原因
    private String buyerNick;//买家昵称
    private String remark;//备注
    private JubanSenderInfoDto senderInfo;//发件人信息

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getReturnOrderCode() {
        return returnOrderCode;
    }

    public void setReturnOrderCode(String returnOrderCode) {
        this.returnOrderCode = returnOrderCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getPreDeliveryOrderCode() {
        return preDeliveryOrderCode;
    }

    public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
        this.preDeliveryOrderCode = preDeliveryOrderCode;
    }

    public String getPreDeliveryOrderId() {
        return preDeliveryOrderId;
    }

    public void setPreDeliveryOrderId(String preDeliveryOrderId) {
        this.preDeliveryOrderId = preDeliveryOrderId;
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

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JubanSenderInfoDto getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(JubanSenderInfoDto senderInfo) {
        this.senderInfo = senderInfo;
    }

    @Override
    public String toString() {
        return "XinyiReturnOrderDto{" +
                "returnOrderCode='" + returnOrderCode + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderFlag='" + orderFlag + '\'' +
                ", preDeliveryOrderCode='" + preDeliveryOrderCode + '\'' +
                ", preDeliveryOrderId='" + preDeliveryOrderId + '\'' +
                ", logisticsCode='" + logisticsCode + '\'' +
                ", logisticsName='" + logisticsName + '\'' +
                ", expressCode='" + expressCode + '\'' +
                ", returnReason='" + returnReason + '\'' +
                ", buyerNick='" + buyerNick + '\'' +
                ", remark='" + remark + '\'' +
                ", senderInfo=" + senderInfo +
                '}';
    }
}
