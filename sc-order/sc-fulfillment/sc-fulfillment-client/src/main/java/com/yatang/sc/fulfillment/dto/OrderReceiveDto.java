package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by qiugang on 7/27/2017.
 */
public class OrderReceiveDto implements Serializable {

    private String orderId;
    private String operatorId;
    Map<?, Long> signedMap;//签收数量


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String pOperatorId) {
        operatorId = pOperatorId;
    }


    public Map<?, Long> getSignedMap() {
        return signedMap;
    }

    public void setSignedMap(Map<?, Long> signedMap) {
        this.signedMap = signedMap;
    }
}
