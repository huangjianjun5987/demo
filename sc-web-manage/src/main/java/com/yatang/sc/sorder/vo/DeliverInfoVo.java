package com.yatang.sc.sorder.vo;

import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liusongjie on 2017/7/28.
 */

public class DeliverInfoVo implements Serializable {
    @NotBlank(message = "{msg.notEmpty.message}",groups = GroupOne.class)
    private String id;

    @NotBlank(message = "{msg.notEmpty.message}",groups = GroupOne.class)
    private String shippingMethod;

    @NotBlank(message = "{msg.notEmpty.message}",groups = GroupOne.class)
    private String shippingNo;

    private String deliveryer;

    private String cellphone;

    private String deliveryDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(String deliveryer) {
        this.deliveryer = deliveryer;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getArrivedEstimatedDate() {
        return arrivedEstimatedDate;
    }

    public void setArrivedEstimatedDate(String arrivedEstimatedDate) {
        this.arrivedEstimatedDate = arrivedEstimatedDate;
    }

    public List<DeliveryGoodsVo> getDeliveryGoodsVos() {
        return deliveryGoodsVos;
    }

    public void setDeliveryGoodsVos(List<DeliveryGoodsVo> deliveryGoodsVos) {
        this.deliveryGoodsVos = deliveryGoodsVos;
    }

    private String arrivedEstimatedDate;

    private List<DeliveryGoodsVo> deliveryGoodsVos;
}
