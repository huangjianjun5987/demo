package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

public class MqFailedMsg implements Serializable {
    private String id;
    private String orderId;
    private String messageType;
    private Boolean resendSuccess;
    private Date createDateTime;

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String pMessageType) {
        messageType = pMessageType;
    }

    public Boolean getResendSuccess() {
        return resendSuccess;
    }

    public void setResendSuccess(Boolean pResendSuccess) {
        resendSuccess = pResendSuccess;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date pCreateDateTime) {
        createDateTime = pCreateDateTime;
    }

    @Override
    public String toString() {
        return "MqFailedMsg{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", messageType='" + messageType + '\'' +
                ", resendSuccess=" + resendSuccess +
                ", createDateTime=" + createDateTime +
                '}';
    }
}
