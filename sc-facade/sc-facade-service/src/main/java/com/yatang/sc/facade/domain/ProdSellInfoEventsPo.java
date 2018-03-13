package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;

public class ProdSellInfoEventsPo implements Serializable {
    /**
     * 数据自增ID
     */
    private Long id;

    /**
     * 售价ID
     */
    private Long priceId;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private String createUserId;

    /**
     * 序列化
     */
    private String serializedPayload;

    /**
     * prod_sell_info_events
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getSerializedPayload() {
        return serializedPayload;
    }

    public void setSerializedPayload(String serializedPayload) {
        this.serializedPayload = serializedPayload == null ? null : serializedPayload.trim();
    }
}