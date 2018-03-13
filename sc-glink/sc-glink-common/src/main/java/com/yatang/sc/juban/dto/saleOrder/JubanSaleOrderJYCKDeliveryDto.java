package com.yatang.sc.juban.dto.saleOrder;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAliasType;

@XStreamAliasType("deliveryOrder")
public class JubanSaleOrderJYCKDeliveryDto implements Serializable{

    private static final long serialVersionUID = -2643277872428734993L;

    private String deliveryOrderCode;//出库单号
    private String warehouseCode;//仓库编码
    private String orderType;//出库单类型
    private String status;//出库单状态 出库单状态, string (50)  (NEW-未开始处理,  ACCEPT-仓库接单 , PARTDELIVERED-部分发货完成,  DELIVERED-发货完成,  EXCEPTION-异常,  CANCELED-取消,  CLOSED-关闭,  REJECT-拒单,  CANCELEDFAIL-取消失败) ,  (只传英文编码)

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
