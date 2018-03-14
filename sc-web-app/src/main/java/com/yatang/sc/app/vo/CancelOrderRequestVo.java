package com.yatang.sc.app.vo;

import java.io.Serializable;

public class CancelOrderRequestVo implements Serializable {
    private String id;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CancelOrderRequestDto{" +
                "id='" + id + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
