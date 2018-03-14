package com.yatang.sc.dto;

import java.io.Serializable;

/**
 * @描述: 促销区域
 * @作者: tankejia
 * @创建时间: 2017/9/4-10:27 .
 * @版本: 1.0 .
 */
public class PromoCompaniesDto implements Serializable {

    private static final long serialVersionUID = 2773324601991560718L;

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

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
