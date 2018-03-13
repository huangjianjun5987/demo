package com.yatang.sc.xinyi.dto.saleOrder;

import com.thoughtworks.xstream.annotations.XStreamAliasType;

import java.io.Serializable;

@XStreamAliasType("senderInfo")
public class SaleOrderSenderInfoDto implements Serializable{

    private static final long serialVersionUID = 7349340758912394608L;
    private String name; //姓名
    private String mobile;//移动电话
    private String province;//省份
    private String city;//城市
    private String area;//区域
    private String detailAddress;//详细地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
