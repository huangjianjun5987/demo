package com.yatang.sc.glink.dto;

import java.io.Serializable;
import java.util.List;

/**
 * create by chensiqiang
 * 取消单据DTO
 */
public class CancelAllOrdersDto implements Serializable {

    private int type;               //系统重复处理订单方式 1.直接返回错误 2.重复订单跳过
    private List<CancelOrdersDto> orders; //订单信息

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CancelOrdersDto> getOrders() {
        return orders;
    }

    public void setOrders(List<CancelOrdersDto> orders) {
        this.orders = orders;
    }
}
