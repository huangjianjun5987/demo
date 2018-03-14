package com.yatang.sc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @描述: 促销活动
 * @作者: tankejia
 * @创建时间: 2017/9/4-10:17 .
 * @版本: 1.0 .
 */
public class PromotionDto implements Serializable {

    private static final long serialVersionUID = -6441482945317071645L;

    /**
     * 编号
     * */
    private String				id;

    /**
     * 状态 released: 已发布,unreleased:未发布,closed:已关闭,ended:已结束
     * */
    private String              status;

    /**
     * 促销类型
     * */
    private String              promotionType;

    /**
     * 活动名称
     * */
    private String              promotionName;

    /**
     * 折扣描述
     * */
    private String              promotionDiscription;

    /**
     * 开始时间
     * */
    private Date                startDate;

    /**
     * 结束时间
     * */
    private Date                endDate;

    /**
     * 折扣类型
     * */
    private String              discountType;

    /**
     * 条件金额
     * */
    private BigDecimal          quanifyAmount;

    /**
     * 折扣
     * */
    private BigDecimal          discount;

    /**
     * 备注
     * */
    private String              note;

    /**
     * 0 为促销，1为优惠券
     */
    private Integer  type;

    /**
     * 是否与会员折扣叠加
     */
    private Integer isSuperposeUserDiscount;

    /**
     * 是否与促销折扣或者优惠券折扣叠加:1是0否
     */
    private Integer isSuperposeProOrCouDiscount;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 发布时间
     */
    private Date publishDate;

    /**
     * 发布人id
     */
    private String publishUserId;


    /**
     * 指定门店
     * */
    private PromoStoresDto              stores;

    private PromoCategoriesDto   promoCategoriesPo;

    private List<PromoCompaniesDto> companiesPoList;

    private List<PromoRecordsDto> recordsPoList;

    /**
     * 促销规则配置信息
     */
    private PromotionRuleDto promotionRule;

    private Long priority;

    private String simpleDescription;

    private String detailDescription;


    public String getSimpleDescription() {
        return simpleDescription;
    }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public PromotionRuleDto getPromotionRule() {
		return promotionRule;
	}

	public void setPromotionRule(PromotionRuleDto promotionRule) {
		this.promotionRule = promotionRule;
	}


	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsSuperposeUserDiscount() {
        return isSuperposeUserDiscount;
    }

    public void setIsSuperposeUserDiscount(Integer isSuperposeUserDiscount) {
        this.isSuperposeUserDiscount = isSuperposeUserDiscount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionDiscription() {
        return promotionDiscription;
    }

    public void setPromotionDiscription(String promotionDiscription) {
        this.promotionDiscription = promotionDiscription;
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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getQuanifyAmount() {
        return quanifyAmount;
    }

    public void setQuanifyAmount(BigDecimal quanifyAmount) {
        this.quanifyAmount = quanifyAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PromoCategoriesDto getPromoCategoriesPo() {
        return promoCategoriesPo;
    }

    public void setPromoCategoriesPo(PromoCategoriesDto promoCategoriesPo) {
        this.promoCategoriesPo = promoCategoriesPo;
    }

    public List<PromoCompaniesDto> getCompaniesPoList() {
        return companiesPoList;
    }

    public void setCompaniesPoList(List<PromoCompaniesDto> companiesPoList) {
        this.companiesPoList = companiesPoList;
    }

    public PromoStoresDto getStores() {
        return stores;
    }

    public void setStores(PromoStoresDto stores) {
        this.stores = stores;
    }

    public List<PromoRecordsDto> getRecordsPoList() {
        return recordsPoList;
    }

    public void setRecordsPoList(List<PromoRecordsDto> recordsPoList) {
        this.recordsPoList = recordsPoList;
    }



    public Integer getIsSuperposeProOrCouDiscount() {
        return isSuperposeProOrCouDiscount;
    }



    public void setIsSuperposeProOrCouDiscount(Integer isSuperposeProOrCouDiscount) {
        this.isSuperposeProOrCouDiscount = isSuperposeProOrCouDiscount;
    }



    public Date getCreateDate() {
        return createDate;
    }



    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    public String getCreateUserId() {
        return createUserId;
    }



    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }



    public Date getPublishDate() {
        return publishDate;
    }



    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }



    public String getPublishUserId() {
        return publishUserId;
    }



    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
    }
}
