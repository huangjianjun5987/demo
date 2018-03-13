package com.yatang.sc.kidd.dto.common;

import java.io.Serializable;

/**
 * @描述:发货商详细信息（供应商）
 * @类名:KiddSenderInfoDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 9:43
 * @版本: v1.0
 */

public class KiddSenderInfoDto implements Serializable {
    private static final long serialVersionUID = -5850338055125140175L;
    private String senderCompany;       //发货人公司名称
    private String senderName;          //发货人姓名
    private String senderZipCode;       //发货人邮编
    private String senderTel;           //发货人固定电话
    private String senderMobile;        //发货人移动电话
    private String senderEmail;         //发货人电子邮箱
    private String senderCountrycode;   //发货人国家二字码
    private String senderProvince;      //发货人省份
    private String senderCity;          //发货人城市
    private String senderArea;          //发货人区域
    private String senderTown;          //发货人村镇发货人村镇
    private String senderDetailAddress; //发货人详细地址

    public String getSenderCompany() {
        return senderCompany;
    }

    public void setSenderCompany(String senderCompany) {
        this.senderCompany = senderCompany;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderZipCode() {
        return senderZipCode;
    }

    public void setSenderZipcode(String senderZipcode) {
        this.senderZipCode = senderZipCode;
    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderCountrycode() {
        return senderCountrycode;
    }

    public void setSenderCountrycode(String senderCountrycode) {
        this.senderCountrycode = senderCountrycode;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    public String getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(String senderTown) {
        this.senderTown = senderTown;
    }

    public String getSenderDetailAddress() {
        return senderDetailAddress;
    }

    public void setSenderDetailAddress(String senderDetailAddress) {
        this.senderDetailAddress = senderDetailAddress;
    }
}
