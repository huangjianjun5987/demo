package com.yatang.sc.sorder.vo;

import java.io.Serializable;

public class ValidateProductVo implements Serializable {

    private static final long serialVersionUID = 4574715895065397892L;

    private  String productId;  //商品id

    private  long quantity;     //下单商品的数量

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
