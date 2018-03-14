package com.yatang.sc.sorder.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ManualSplitOrderVo implements Serializable {
    private String parentOrderId;
    private List<Map<String, Long>> groups;

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public List<Map<String, Long>> getGroups() {
        return groups;
    }

    public void setGroups(List<Map<String, Long>> groups) {
        this.groups = groups;
    }
}
