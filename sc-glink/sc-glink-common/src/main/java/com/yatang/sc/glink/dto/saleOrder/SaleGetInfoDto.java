package com.yatang.sc.glink.dto.saleOrder;

import java.io.Serializable;

/**
 * @描述: 收货人信息DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class SaleGetInfoDto  implements Serializable {
    private String consignee;//收货人姓名
    private String custmoerCode;//收货人编号
    private String consigneeOrg;//收货单位
    private String consigneePhone;//收货人手机
    private String deliveryProvince;//收货省
    private String deliveryCity;//收货城市
    private String deliveryDistrict;//收货区县
    private String deliveryVillage;//收货小区/街道
    private String deliveryAddress;//收货地址

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCustmoerCode() {
        return custmoerCode;
    }

    public void setCustmoerCode(String custmoerCode) {
        this.custmoerCode = custmoerCode;
    }

    public String getConsigneeOrg() {
        return consigneeOrg;
    }

    public void setConsigneeOrg(String consigneeOrg) {
        this.consigneeOrg = consigneeOrg;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getDeliveryProvince() {
        return deliveryProvince;
    }

    public void setDeliveryProvince(String deliveryProvince) {
        this.deliveryProvince = deliveryProvince;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }

    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict = deliveryDistrict;
    }

    public String getDeliveryVillage() {
        return deliveryVillage;
    }

    public void setDeliveryVillage(String deliveryVillage) {
        this.deliveryVillage = deliveryVillage;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
