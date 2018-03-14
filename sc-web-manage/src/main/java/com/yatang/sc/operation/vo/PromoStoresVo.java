package com.yatang.sc.operation.vo;

import com.yatang.sc.validgroup.GroupThree;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @描述: 促销指定门店
 * @作者: tankejia
 * @创建时间: 2017/9/4-10:34 .
 * @版本: 1.0 .
 */
public class PromoStoresVo implements Serializable {

    private static final long serialVersionUID = 7140505286618059280L;

    /**
     * 促销id
     * */
    @NotBlank(groups = {GroupThree.class}, message = "{msg.notEmpty.message}")
    private String              promoId;

    /**
     * 门店ID
     * */
    @NotBlank(groups = {GroupThree.class}, message = "{msg.notEmpty.message}")
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