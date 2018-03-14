package com.yatang.sc.purchase.dto.wish;

import java.io.Serializable;

public class SaveWishListsDto implements Serializable{

    private static final long serialVersionUID = -6342507104759821947L;

    private long quantity;
    private String barCode;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
