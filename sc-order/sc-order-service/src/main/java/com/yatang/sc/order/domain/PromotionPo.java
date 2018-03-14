package com.yatang.sc.order.domain;

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
public class PromotionPo implements Serializable {

    private static final long serialVersionUID = -4621971752364600333L;

    /**
     * 编号
     * */
    private String				id;

    /**
     * 状态
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
     * 是否与会员折扣叠加 0:否 1：是
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
    private PromoStoresPo       stores;

    private PromoCategoriesPo   promoCategoriesPo;

    private List<PromoCompaniesPo> companiesPoList;

    private List<PromoRecordsPo> recordsPoList;


    /**
     * 促销规则配置信息
     */
    private PromotionRulePo promotionRule;

    private String promotionRuleJson;

    private Long priority;

    private String simpleDescription;

    private String detailDescription;


    public PromotionRulePo getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(PromotionRulePo promotionRule) {
        this.promotionRule = promotionRule;
    }

    public String getPromotionRuleJson() {
        return promotionRuleJson;
    }

    public void setPromotionRuleJson(String promotionRuleJson) {
        this.promotionRuleJson = promotionRuleJson;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

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

    public void setId(String pId) {
        id = pId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String pStatus) {
        status = pStatus;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String pPromotionType) {
        promotionType = pPromotionType;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String pPromotionName) {
        promotionName = pPromotionName;
    }

    public String getPromotionDiscription() {
        return promotionDiscription;
    }

    public void setPromotionDiscription(String pPromotionDiscription) {
        promotionDiscription = pPromotionDiscription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date pStartDate) {
        startDate = pStartDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date pEndDate) {
        endDate = pEndDate;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String pDiscountType) {
        discountType = pDiscountType;
    }

    public BigDecimal getQuanifyAmount() {
        return quanifyAmount;
    }

    public void setQuanifyAmount(BigDecimal pQuanifyAmount) {
        quanifyAmount = pQuanifyAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal pDiscount) {
        discount = pDiscount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String pNote) {
        note = pNote;
    }

    public PromoCategoriesPo getPromoCategoriesPo() {
        return promoCategoriesPo;
    }

    public void setPromoCategoriesPo(PromoCategoriesPo pPromoCategoriesPo) {
        promoCategoriesPo = pPromoCategoriesPo;
    }

    public List<PromoCompaniesPo> getCompaniesPoList() {
        return companiesPoList;
    }

    public void setCompaniesPoList(List<PromoCompaniesPo> pCompaniesPoList) {
        companiesPoList = pCompaniesPoList;
    }

    public List<PromoRecordsPo> getRecordsPoList() {
        return recordsPoList;
    }

    public void setRecordsPoList(List<PromoRecordsPo> pRecordsPoList) {
        recordsPoList = pRecordsPoList;
    }

    public PromoStoresPo getStores() {
        return stores;
    }

    public void setStores(PromoStoresPo stores) {
        this.stores = stores;
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
