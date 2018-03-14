package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券使用记录po
 * @author yinyuxin
 * @date 2017年9月19日14:28:05
 */
public class CouponRecordPo implements Serializable {
    private Long id;                                       //主键id

    private String orderId;                                //订单ID

    private String promoId;                                //优惠券id

    private Double discountAmount;                         //折扣费用

    private Date   recordTime;                             //使用时间

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId == null ? null : promoId.trim();
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }



    public Date getRecordTime() {
        return recordTime;
    }



    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
}