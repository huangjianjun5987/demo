package com.yatang.sc.kidd.dto.saleOrder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 收货人信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 11:43
 * @版本: v1.0
 */
public class KiddSaleOrderReceiverInfoDto implements Serializable{

    private static final long serialVersionUID = 3184427287337392859L;
    private String name;//收货人姓名

    private String custmoerCode;

    private String mobile;//收货人手机

    private String province;//收货省

    private String city;//收货城市


    private String area;//区域 区县
    private String town;//村镇 小区/街道

    private String detailAddress;//收货区详细地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustmoerCode() {
        return custmoerCode;
    }

    public void setCustmoerCode(String custmoerCode) {
        this.custmoerCode = custmoerCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
