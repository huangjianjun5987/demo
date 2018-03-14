package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

public class MsgRetryQueue implements Serializable {
    private Long id;
    private String uniqueId;
    private String msgBody;
    private int retryCount;
    private String msgType;
    private int order;
    private String state;
    private Date createTime;
    private Date lastExecTime;

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String pUniqueId) {
        uniqueId = pUniqueId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String pMsgBody) {
        msgBody = pMsgBody;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int pRetryCount) {
        retryCount = pRetryCount;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String pMsgType) {
        msgType = pMsgType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int pOrder) {
        order = pOrder;
    }

    public String getState() {
        return state;
    }

    public void setState(String pState) {
        state = pState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date pCreateTime) {
        createTime = pCreateTime;
    }

    public Date getLastExecTime() {
        return lastExecTime;
    }

    public void setLastExecTime(Date pLastExecTime) {
        lastExecTime = pLastExecTime;
    }
}
