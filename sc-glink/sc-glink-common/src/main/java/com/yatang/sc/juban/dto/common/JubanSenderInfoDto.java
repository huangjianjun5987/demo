package com.yatang.sc.juban.dto.common;

import com.thoughtworks.xstream.annotations.XStreamAliasType;

import java.io.Serializable;

/**
 * @描述:心怡仓库接收发货人信息
 * @类名:XinyiSenderInfo
 * @作者: lvheping
 * @创建时间: 2017/9/25 14:44
 * @版本: v1.0
 */
@XStreamAliasType("senderInfo")
public class JubanSenderInfoDto implements Serializable {
    private static final long serialVersionUID = 479125820685887901L;
    private String company;//公司名称
    private String name;//姓名
    private String mobile;//移动电话
    private String email;//电子邮箱
    private String province;//公司所在省
    private String city;//公司所在市
    private String area;//公司所在区
    private String detailAddress;//公司详细地址

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    @Override
    public String toString() {
        return "XinyiSenderInfoDto{" +
                "company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }
}
