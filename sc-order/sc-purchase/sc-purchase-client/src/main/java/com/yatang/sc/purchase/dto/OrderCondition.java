package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
@Data
public class OrderCondition implements Serializable{
    private static final long serialVersionUID = -7628609004054272489L;

    private String profileId;//用户id
    private Short state;//购物车状态
    private int page;
    private int pageSize;
    private int des;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDes() {
        return des;
    }

    public void setDes(int des) {
        this.des = des;
    }
}
