package com.yatang.sc.fulfillment.dto;

import com.yatang.sc.glink.dto.saleOrder.SaleCargoDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiugang on 7/27/2017.
 */
public class OrderDeliveryDto implements Serializable {

    private static final long serialVersionUID = -3910985943544888875L;
    private String orderId;

    //心怡物流信息
    private String logisticsCode;//物流公司编码

    private String logisticsName;//物流公司名称

    private String expressCode;//运单号

    //new 际链物流信息
    List<SaleCargoDto> cargoDtos;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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


    public List<SaleCargoDto> getCargoDtos() {
        return cargoDtos;
    }

    public void setCargoDtos(List<SaleCargoDto> cargoDtos) {
        this.cargoDtos = cargoDtos;
    }
}
