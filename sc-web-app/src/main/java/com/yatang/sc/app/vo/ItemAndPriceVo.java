package com.yatang.sc.app.vo;

import java.io.Serializable;

public class ItemAndPriceVo implements Serializable {

    private static final long serialVersionUID = -1710441532599215471L;

    private  double total;

    private  String item;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
