package com.yatang.sc.sorder.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class DirectCommitItemVO implements Serializable{
    private static final long serialVersionUID = 4168163512838738979L;

    @NotBlank(message = "{msg.notEmpty.message}")
    private String productId;

    @NotBlank(message = "{msg.notEmpty.message}")
    private long quantity;

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
