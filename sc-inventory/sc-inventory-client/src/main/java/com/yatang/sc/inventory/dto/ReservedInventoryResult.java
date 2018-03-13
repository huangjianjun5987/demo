package com.yatang.sc.inventory.dto;

import java.io.Serializable;

public class ReservedInventoryResult implements Serializable {

    private String orderId;
    private ReservedInventoryStatus staus;
    private String msg;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public ReservedInventoryStatus getStaus() {
        return staus;
    }

    public void setStaus(ReservedInventoryStatus pStaus) {
        staus = pStaus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String pMsg) {
        msg = pMsg;
    }

    public enum ReservedInventoryStatus {
        RESERVED_ALL,//全部预留成功
        RESERVED_FAILURE,//全部预留失败
        RESERVED_PORTION_SUCCESS,//部分预留成功
        RESERVED_PORTION_FAIL//部分预留失败
    }
}

