package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;

public class ProviderOrderSingReqDto implements Serializable {
    private String orderId;
    private String signedMsg;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public String getSignedMsg() {
        if (signedMsg == null) {
            signedMsg = "";
        }
        return signedMsg;
    }

    public void setSignedMsg(String pSignedMsg) {
        signedMsg = pSignedMsg;
    }
}
