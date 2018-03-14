package com.yatang.sc.app.vo.coupon;

import java.io.Serializable;

/**
 * @描述: 促销品类
 * @作者: tankejia
 * @创建时间: 2017/9/4-10:31 .
 * @版本: 1.0 .
 */
public class PromoCategoriesDto implements Serializable {

    private static final long serialVersionUID = -3003810467948317912L;

    /**
     * 促销id
     * */
    private String              promoId;

    /**
     * 分类ID
     * */
    private String              categoryId;

    /**
     * 类名
     * */
    private String              categoryName;

    /**
     * 分类级别
     * */
    private Integer             categoryLevel;

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String pPromoId) {
        promoId = pPromoId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String pCategoryId) {
        categoryId = pCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String pCategoryName) {
        categoryName = pCategoryName;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer pCategoryLevel) {
        categoryLevel = pCategoryLevel;
    }
}
