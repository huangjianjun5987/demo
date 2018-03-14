package com.yatang.sc.sorder.vo;

import java.io.Serializable;

/**
 * @描述: 导入失败数据
 * @作者: tankejia
 * @创建时间: 2017/11/6-11:19 .
 * @版本: 1.0 .
 */
public class UploadFailedVo implements Serializable {

    private static final long serialVersionUID = 1602592136839714212L;

    private String productName;

    private String productCode;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public UploadFailedVo() {
    }

    public UploadFailedVo(String productName, String productCode) {
        this.productName = productName;
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "UploadFailedVo{" +
                "productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}
