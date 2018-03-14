package com.yatang.sc.purchase.dto;

import java.io.Serializable;

public class OrderRefundDto implements Serializable {
    private Integer id;

    private String returnOrderId;//退货单id

    private String refundId;//退款单id

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId == null ? null : returnOrderId.trim();
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId == null ? null : refundId.trim();
    }
}