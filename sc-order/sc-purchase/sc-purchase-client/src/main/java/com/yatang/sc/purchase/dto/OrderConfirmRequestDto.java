package com.yatang.sc.purchase.dto;

import java.io.Serializable;

/**
 * Created by qiugang on 7/10/2017.
 */
public class OrderConfirmRequestDto implements Serializable {

    private static final long serialVersionUID = 6472775049693791072L;
    private boolean checkInventory=false;

    public boolean isCheckInventory() {
        return checkInventory;
    }

    public void setCheckInventory(boolean pCheckInventory) {
        checkInventory = pCheckInventory;
    }
}
