package com.yatang.sc.glink.dto.saleOrder;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 发票明细DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class InvoiceDto implements Serializable {
    private String invoiceType;//发票类型
    private String invoiceHeader;//发票抬头
    private String invoiceAmount;//发票总金额
    private String invoiceContent;//发票内容
    private String invoiceCode;//纳税企业的标识
    private String invoiceNumber;//纳税企业内部的发票号
    private List<InvoiceDetailDto> invoiceDetail;//发票行

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(String invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public List<InvoiceDetailDto> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetailDto> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
