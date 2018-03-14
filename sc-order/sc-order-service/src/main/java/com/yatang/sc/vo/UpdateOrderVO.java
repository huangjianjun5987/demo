package com.yatang.sc.vo;

import com.yatang.sc.order.domain.Order;

public class UpdateOrderVO {
    private Order order;
    private Short preOrderState;
    private String description;
    private String operator;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order pOrder) {
        order = pOrder;
    }

    public Short getPreOrderState() {
        return preOrderState;
    }

    public void setPreOrderState(Short pPreOrderState) {
        preOrderState = pPreOrderState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String pOperator) {
        operator = pOperator;
    }
}
