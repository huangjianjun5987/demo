package com.yatang.sc.purchase.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by liusongjie on 2017/7/24.
 */

public class OrderClassificationDto implements Serializable{

    private static final long serialVersionUID = -3655181026531751923L;
    private String profileId;

    private String state;

    private String page;

    private String pageSize;

    public OrderClassificationDto() {
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
