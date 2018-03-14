package com.yatang.sc.app.vo.wish;

import java.io.Serializable;

public class SaveWishVo implements Serializable{
    private static final long serialVersionUID = -7496075928702982205L;

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
