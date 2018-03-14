package com.yatang.sc.purchase.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SignOrderDto implements Serializable{
    private static final long serialVersionUID = -3375987763450429916L;
    @NotNull
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }
}
