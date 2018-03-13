package com.yatang.sc.glink.dto.entryOrder;

import java.io.Serializable;

/**
 * create by chensiqiang
 * 入库单收货仓明细DTO
 */
public class GetInfoDto implements Serializable {

    private String getCompany;         //收货仓公司名称
    private String getName;            //收货仓姓名
    private String getZipcode;         //收货仓邮编
    private String getTel;             //收货仓固定电话
    private String getMobile;          //收货仓移动电话
    private String getEmail;           //收货仓电子邮箱
    private String getCountrycode;     //收货仓国家二字码
    private String getProvince;        //收货仓省份
    private String getCity;            //收货仓城市
    private String getArea;            //收货仓地区
    private String getTown;            //收货仓村镇
    private String getDetailAddress;   //收货仓详细地址

    public String getGetCompany() {
        return getCompany;
    }

    public void setGetCompany(String getCompany) {
        this.getCompany = getCompany;
    }

    public String getGetName() {
        return getName;
    }

    public void setGetName(String getName) {
        this.getName = getName;
    }

    public String getGetZipcode() {
        return getZipcode;
    }

    public void setGetZipcode(String getZipcode) {
        this.getZipcode = getZipcode;
    }

    public String getGetTel() {
        return getTel;
    }

    public void setGetTel(String getTel) {
        this.getTel = getTel;
    }

    public String getGetMobile() {
        return getMobile;
    }

    public void setGetMobile(String getMobile) {
        this.getMobile = getMobile;
    }

    public String getGetEmail() {
        return getEmail;
    }

    public void setGetEmail(String getEmail) {
        this.getEmail = getEmail;
    }

    public String getGetCountrycode() {
        return getCountrycode;
    }

    public void setGetCountrycode(String getCountrycode) {
        this.getCountrycode = getCountrycode;
    }

    public String getGetProvince() {
        return getProvince;
    }

    public void setGetProvince(String getProvince) {
        this.getProvince = getProvince;
    }

    public String getGetCity() {
        return getCity;
    }

    public void setGetCity(String getCity) {
        this.getCity = getCity;
    }

    public String getGetArea() {
        return getArea;
    }

    public void setGetArea(String getArea) {
        this.getArea = getArea;
    }

    public String getGetTown() {
        return getTown;
    }

    public void setGetTown(String getTown) {
        this.getTown = getTown;
    }

    public String getGetDetailAddress() {
        return getDetailAddress;
    }

    public void setGetDetailAddress(String getDetailAddress) {
        this.getDetailAddress = getDetailAddress;
    }
}
