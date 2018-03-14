package com.yatang.sc.purchase.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yatang.sc.dto.PromotionRuleDto;

/**
 * Created by qiugang on 9/4/2017.
 */
public class PricingModel implements Serializable {

    private String id;

    private Integer type;

    private String discountType;

    private String promotionName;

    private double quanifyAmount;

    private double discount;

    private Boolean isSuperposePromo;

    private Boolean isSuperposeUserDiscount;

    private List<String> companies;

    private List<String> stores;

    private List<String> categories1;

    private List<String> categories2;

    private List<String> categories3;

    private List<String> categories4;

    private String status;

    private Date startDate;

    private Date endDate;
    
    private PromotionRuleDto promotionRule;

    private long Priority;
    
    private String promotionDiscription;

	public String getPromotionDiscription() {
		return promotionDiscription;
	}

	public void setPromotionDiscription(String promotionDiscription) {
		this.promotionDiscription = promotionDiscription;
	}

	public Boolean getSuperposePromo() {
        return isSuperposePromo;
    }

    public void setSuperposePromo(Boolean superposePromo) {
        isSuperposePromo = superposePromo;
    }

    public Boolean getSuperposeUserDiscount() {
        return isSuperposeUserDiscount;
    }

    public long getPriority() {
		return Priority;
	}

	public void setPriority(long priority) {
		Priority = priority;
	}

	public void setSuperposeUserDiscount(Boolean superposeUserDiscount) {
        isSuperposeUserDiscount = superposeUserDiscount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public double getQuanifyAmount() {
        return quanifyAmount;
    }

    public void setQuanifyAmount(double quanifyAmount) {
        this.quanifyAmount = quanifyAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    public List<String> getStores() {
        return stores;
    }

    public void setStores(List<String> stores) {
        this.stores = stores;
    }

    public List<String> getCategories1() {
        return categories1;
    }

    public void setCategories1(List<String> categories1) {
        this.categories1 = categories1;
    }

    public List<String> getCategories2() {
        return categories2;
    }

    public void setCategories2(List<String> categories2) {
        this.categories2 = categories2;
    }

    public List<String> getCategories3() {
        return categories3;
    }

    public void setCategories3(List<String> categories3) {
        this.categories3 = categories3;
    }

    public List<String> getCategories4() {
        return categories4;
    }

    public void setCategories4(List<String> categories4) {
        this.categories4 = categories4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public PromotionRuleDto getPromotionRule() {
		return promotionRule;
	}

	public void setPromotionRule(PromotionRuleDto promotionRule) {
		this.promotionRule = promotionRule;
	}
    
}
