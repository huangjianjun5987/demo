package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liusongjie on 2017/7/11.
 */
@Data
public class InvoiceInfoDto implements Serializable {

    private static final long serialVersionUID = 8232555108374695720L;
    /**
     * 发票ID
     */
    private String invoiceId;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 纳税人识别码
     */
    private String taxpayerIdentificationNumber;

    /**
     * 注册地址
     */
    private String registeredAddress;

    /**
     * 公司电话
     */
    private String companyPhone;

    /**
     * 开户银行
     */
    private String depositBank;

    /**
     * 开户账号
     */
    private String accountNumber;

    /**
     * 创建时间
     */
    private Date entryDatetime;

    /**
     * 更新时间
     */
    private Date updateDatetime;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxpayerIdentificationNumber() {
        return taxpayerIdentificationNumber;
    }

    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getEntryDatetime() {
        return entryDatetime;
    }

    public void setEntryDatetime(Date entryDatetime) {
        this.entryDatetime = entryDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
