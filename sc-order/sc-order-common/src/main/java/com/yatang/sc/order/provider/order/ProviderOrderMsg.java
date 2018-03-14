package com.yatang.sc.order.provider.order;


import java.io.Serializable;

/**
 * 供应商协同平台通知消息
 */
public class ProviderOrderMsg implements Serializable {
    private ProviderOrderStatus status;
    private String body;

    public ProviderOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ProviderOrderStatus pStatus) {
        status = pStatus;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String pBody) {
        body = pBody;
    }
}
