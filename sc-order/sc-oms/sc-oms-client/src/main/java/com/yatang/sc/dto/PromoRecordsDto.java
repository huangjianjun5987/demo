package com.yatang.sc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @描述: 促销订单记录
 * @作者: tankejia
 * @创建时间: 2017/9/4-20:11 .
 * @版本: 1.0 .
 */
public class PromoRecordsDto implements Serializable {

    private static final long serialVersionUID = -2351976613076595282L;

    private Integer              id;

    private String              promoId;

    private String              orderId;

    /**
     * 折扣金额
     * */
    private BigDecimal          discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer pId) {
        id = pId;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String pPromoId) {
        promoId = pPromoId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal pDiscount) {
        discount = pDiscount;
    }
}
