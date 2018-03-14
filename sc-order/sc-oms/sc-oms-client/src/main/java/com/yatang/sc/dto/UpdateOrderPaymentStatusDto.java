package com.yatang.sc.dto;

import com.yatang.sc.order.states.OrderTotalStates;

import java.io.Serializable;

public class UpdateOrderPaymentStatusDto implements Serializable {
    private String mOrderNo;
    private OrderTotalStates mOrderTotalStates;
    private String mPaymentStates;

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String pOrderNo) {
        mOrderNo = pOrderNo;
    }

    public OrderTotalStates getOrderTotalStates() {
        return mOrderTotalStates;
    }

    public void setOrderTotalStates(OrderTotalStates pOrderTotalStates) {
        mOrderTotalStates = pOrderTotalStates;
    }

    public String getPaymentStates() {
        return mPaymentStates;
    }

    public void setPaymentStates(String pPaymentStates) {
        mPaymentStates = pPaymentStates;
    }

    @Override
    public String toString() {
        return "UpdateOrderPaymentStatusDto{" +
                "mOrderNo='" + mOrderNo + '\'' +
                ", mOrderTotalStates='" + mOrderTotalStates + '\'' +
                ", mPaymentStates='" + mPaymentStates + '\'' +
                '}';
    }
}
