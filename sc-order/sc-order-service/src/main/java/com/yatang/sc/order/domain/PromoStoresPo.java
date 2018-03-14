package com.yatang.sc.order.domain;

import java.io.Serializable;

/**
 * @描述: 促销指定门店
 * @作者: tankejia
 * @创建时间: 2017/9/4-10:34 .
 * @版本: 1.0 .
 */
public class PromoStoresPo implements Serializable {

    private static final long serialVersionUID = -8235820518116420818L;

    /**
     * 促销id
     * */
    private String              promoId;

    /**
     * 门店ID
     * */
    private String              storeId;

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}