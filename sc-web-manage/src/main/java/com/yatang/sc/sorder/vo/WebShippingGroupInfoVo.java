package com.yatang.sc.sorder.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.dto.WebShippingProductDto;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WebShippingGroupInfoVo implements Serializable {

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

    @JsonSerialize(using = ImageUrlSerializer.class)
    @JsonDeserialize(using = ImageUrlDeserializer.class)
    private String singedCertImg;    //签收凭证


    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String pShippingNo) {
        shippingNo = pShippingNo;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String pShippingMethod) {
        shippingMethod = pShippingMethod;
    }

    public Date getShipOnDate() {
        return shipOnDate;
    }

    public void setShipOnDate(Date pShipOnDate) {
        shipOnDate = pShipOnDate;
    }

    public Date getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(Date pEstimatedArrivalDate) {
        estimatedArrivalDate = pEstimatedArrivalDate;
    }

    public String getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(String pDeliveryer) {
        deliveryer = pDeliveryer;
    }

    public String getDeliveryerPhone() {
        return deliveryerPhone;
    }

    public void setDeliveryerPhone(String pDeliveryerPhone) {
        deliveryerPhone = pDeliveryerPhone;
    }

    public List<WebShippingProductDto> getShippingProductDtos() {
        return shippingProductDtos;
    }

    public void setShippingProductDtos(List<WebShippingProductDto> pShippingProductDtos) {
        shippingProductDtos = pShippingProductDtos;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String pShippingState) {
        shippingState = pShippingState;
    }

    public String getShippingStateDesc() {
        return shippingStateDesc;
    }

    public void setShippingStateDesc(String pShippingStateDesc) {
        shippingStateDesc = pShippingStateDesc;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String pDistributionName) {
        distributionName = pDistributionName;
    }

    public String getShippingModes() {
        return shippingModes;
    }

    public void setShippingModes(String pShippingModes) {
        shippingModes = pShippingModes;
    }

    public String getSingedCertImg() {
        return singedCertImg;
    }

    public void setSingedCertImg(String pSingedCertImg) {
        singedCertImg = pSingedCertImg;
    }
}
