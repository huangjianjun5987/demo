package com.yatang.sc.sorder.vo;

import com.yatang.sc.dto.WebShippingProductDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
public class WebShippingGroupVo implements Serializable{

    /**
     * 订单编号
     */
    private String id;
    /**
     * 运单号
     */
    private String shippingNo;
    /**
     * 配送方式
     */
    private String shippingMethod;
    /**
     * 配送日期
     */
    private String shipOnDate;
    /**
     * 预计到达日期
     */
    private String estimatedArrivalDate;
    /**
     * 送货人
     */
    private String deliveryer;
    /**
     * 送货人联系方式
     */
    private String deliveryerPhone;

    /**
     * 配送商品列表
     */
    private List<WebShippingProductDto> shippingProductDtos;

    public WebShippingGroupVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShipOnDate() {
        return shipOnDate;
    }

    public void setShipOnDate(String shipOnDate) {
        this.shipOnDate = shipOnDate;
    }

    public String getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(String estimatedArrivalDate) {
        this.estimatedArrivalDate = estimatedArrivalDate;
    }

    public String getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(String deliveryer) {
        this.deliveryer = deliveryer;
    }

    public String getDeliveryerPhone() {
        return deliveryerPhone;
    }

    public void setDeliveryerPhone(String deliveryerPhone) {
        this.deliveryerPhone = deliveryerPhone;
    }

    public List<WebShippingProductDto> getShippingProductDtos() {
        return shippingProductDtos;
    }

    public void setShippingProductDtos(List<WebShippingProductDto> shippingProductDtos) {
        this.shippingProductDtos = shippingProductDtos;
    }

}
