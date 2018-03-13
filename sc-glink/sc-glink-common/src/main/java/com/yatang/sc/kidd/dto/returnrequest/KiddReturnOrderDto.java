package com.yatang.sc.kidd.dto.returnrequest;

import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:订单退货入库封装类
 * @类名:KiddReturnOrderDto
 * @作者: lvheping
 * @创建时间: 2017/10/18 11:21
 * @版本: v1.0
 */

public class KiddReturnOrderDto implements Serializable {
    private static final long serialVersionUID = 8804839050076910871L;
    private String wareHouseCode;//逻辑出货仓编码 （必填）
    private String ownerCode;//子公司编码  (必填）
    private String entryOrderCode;//退货单号 (必填）
    private String preDeliveryOrderCode;//原出库单号 (必填）
    private String orderType;//入库单业务类型：THRK=退货入库 HHRK=退货入库(必填）
    private String logisticsCode;//物流公司编码(OTHER=其他) (必填）
    private KiddSenderInfoDto senderInfo;//发货人信息
    private List<KiddOrderLinesDto> orderLines;//商品信息

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

    public String getPreDeliveryOrderCode() {
        return preDeliveryOrderCode;
    }

    public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
        this.preDeliveryOrderCode = preDeliveryOrderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public KiddSenderInfoDto getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(KiddSenderInfoDto senderInfo) {
        this.senderInfo = senderInfo;
    }

    public List<KiddOrderLinesDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<KiddOrderLinesDto> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "KiddReturnOrderDto{" +
                "wareHouseCode='" + wareHouseCode + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", entryOrderCode='" + entryOrderCode + '\'' +
                ", preDeliveryOrderCode='" + preDeliveryOrderCode + '\'' +
                ", orderType='" + orderType + '\'' +
                ", logisticsCode='" + logisticsCode + '\'' +
                ", senderInfo=" + senderInfo +
                ", orderLines=" + orderLines +
                '}';
    }
}
