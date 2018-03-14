package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

public class ReturnOrderLogPo implements Serializable {
    private Integer id;

    private String returnId;

    private Short returnState;

    private String operater;

    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId == null ? null : returnId.trim();
    }

    public Short getReturnState() {
        return returnState;
    }

    public void setReturnState(Short returnState) {
        this.returnState = returnState;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}