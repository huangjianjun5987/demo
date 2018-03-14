package com.yatang.sc.order.domain;

import java.io.Serializable;

/**
 * @描述: 促销区域
 * @作者: tankejia
 * @创建时间: 2017/9/4-10:27 .
 * @版本: 1.0 .
 */
public class PromoCompaniesPo implements Serializable {

    private static final long serialVersionUID = 6136575371268262554L;

    /**
     * 促销id
     * */
    private String              promoId;

    /**
     * 分公司ID
     * */
    private String              companyId;

    /**
     * 分公司名称
     * */
    private String              companyName;

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String pPromoId) {
        promoId = pPromoId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String pCompanyId) {
        companyId = pCompanyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
