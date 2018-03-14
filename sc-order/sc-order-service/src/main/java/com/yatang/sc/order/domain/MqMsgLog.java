package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

public class MqMsgLog implements Serializable {
    private String msgId;
    private String orderId;
    private String msgBody;
    private String msgType;
    private boolean processResult;
    private String comment;
    private Date recivDate;


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String pMsgId) {
        msgId = pMsgId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String pMsgBody) {
        msgBody = pMsgBody;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String pMsgType) {
        msgType = pMsgType;
    }

    public boolean isProcessResult() {
        return processResult;
    }

    public void setProcessResult(boolean pProcessResult) {
        processResult = pProcessResult;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String pComment) {
        comment = pComment;
    }

    public Date getRecivDate() {
        return recivDate;
    }

    public void setRecivDate(Date pRecivDate) {
        recivDate = pRecivDate;
    }

    @Override
    public String toString() {
        return "MqMsgLog{" +
                "msgId='" + msgId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", msgBody='" + msgBody + '\'' +
                ", msgType='" + msgType + '\'' +
                ", processResult=" + processResult +
                ", comment='" + comment + '\'' +
                ", recivDate=" + recivDate +
                '}';
    }
}
