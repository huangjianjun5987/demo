package com.yatang.sc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liusongjie on 2017/7/28.
 */
public class DeliverInfoDto implements Serializable {
    private String orderId;

    private String shippingMethod;

    private String shippingNo;

    private String deliveryer;

    private String cellphone;

    private Date deliveryDate;

    private Date estimatedArrivedDate;

    private List<DeliveryGoodsDto> deliveryGoodsDtos;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<DeliveryGoodsDto> getDeliveryGoodsDtos() {
        return deliveryGoodsDtos;
    }

    public void setDeliveryGoodsDtos(List<DeliveryGoodsDto> deliveryGoodsDtos) {
        this.deliveryGoodsDtos = deliveryGoodsDtos;
    }

    public String getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(String deliveryer) {
        this.deliveryer = deliveryer;
    }

    public Date getEstimatedArrivedDate() {
        return estimatedArrivedDate;
    }

    public void setEstimatedArrivedDate(Date estimatedArrivedDate) {
        this.estimatedArrivedDate = estimatedArrivedDate;
    }
}
