package com.yatang.sc.payment.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuwei on 2017/7/8.
 */
public abstract class BaseDto implements Serializable {
    private Long mId;
    private Date mCreateTime;
    private Date mEditTime;


    public Long getId() {
        return mId;
    }

    public void setId(Long pId) {
        mId = pId;
    }

    public Date getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(Date pCreateTime) {
        mCreateTime = pCreateTime;
    }

    public Date getEditTime() {
        return mEditTime;
    }

    public void setEditTime(Date pEditTime) {
        mEditTime = pEditTime;
    }
}
