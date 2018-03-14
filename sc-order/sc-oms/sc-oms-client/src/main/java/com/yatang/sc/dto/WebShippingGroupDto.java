package com.yatang.sc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/26.
 */
public class WebShippingGroupDto implements Serializable{

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
     * 配送日期 对应PO中actualShipDate字段手动设置
     */
    private Date shipOnDate;
    /**
     * 预计到达日期
     */
    private Date estimatedArrivalDate;
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

    /***********************************供应商直送新增的相关字段 尹玉新 ***********************/

    private String shippingState;    //物流状态

    private String shippingStateDesc;//物流状态描述

    private String distributionName; //配送方名称

    private String shippingModes;    //配送方式

    private String singedCertImg;    //签收凭证

    //todo 备注字段
    /****************************************************************************************/


    public WebShippingGroupDto() {
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

    public Date getShipOnDate() {
        return shipOnDate;
    }

    public void setShipOnDate(Date shipOnDate) {
        this.shipOnDate = shipOnDate;
    }

    public Date getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(Date estimatedArrivalDate) {
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



    public String getDistributionName() {
        return distributionName;
    }



    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }



    public String getShippingState() {
        return shippingState;
    }



    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }



    public String getShippingStateDesc() {
        return shippingStateDesc;
    }



    public void setShippingStateDesc(String shippingStateDesc) {
        this.shippingStateDesc = shippingStateDesc;
    }



    public String getShippingModes() {
        return shippingModes;
    }



    public void setShippingModes(String shippingModes) {
        this.shippingModes = shippingModes;
    }



    public String getSingedCertImg() {
        return singedCertImg;
    }



    public void setSingedCertImg(String singedCertImg) {
        this.singedCertImg = singedCertImg;
    }


}
