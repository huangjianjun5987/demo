package com.yatang.sc.sorder.vo;

import java.io.Serializable;

public class SimpleProductVo implements Serializable {

    private static final long serialVersionUID = 1310830203830596849L;

    private String productId;  //商品id

    private String productCode;  //商品code

    private String productName;   //商品名字

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
