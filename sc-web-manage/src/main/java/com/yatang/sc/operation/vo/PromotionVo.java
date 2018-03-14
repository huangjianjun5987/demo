package com.yatang.sc.operation.vo;


import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
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
public class PromotionVo implements Serializable {

    private static final long serialVersionUID = 2950196787462730318L;

    /**
     * 编号
     * */
    @NotBlank(groups = {DefaultGroup.class}, message = "{msg.notEmpty.message}")
    private String				id;

    /**
     * 状态 (released: 已发布,unreleased:未发布,closed:已关闭,ended:已结束)
     * */
    @NotBlank(groups = {DefaultGroup.class}, message = "{msg.notEmpty.message}")
    private String              status;

    /**
     * 促销类型
     * */
    private String              promotionType;

    /**
     * 活动名称
     * */
    @NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
    @Length(max=30, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
    private String              promotionName;

    /**
     * 折扣描述
     * */
    private String              promotionDiscription;

    /**
     * 开始时间
     * */
    @NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
    private Date                startDate;

    /**
     * 结束时间
     * */
    @NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
    private Date                endDate;

    /**
     * 折扣类型
     * */
    private String              discountType;

    /**
     * 条件金额
     * */
    @DecimalMin(value = "0", inclusive = false, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.largeThanZero.message}")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.bigDecimal.message}")
    private BigDecimal          quanifyAmount;

    /**
     * 折扣
     * */
    private BigDecimal          discount;

    /**
     * 备注
     * */
    @Length(max=200, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
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

    private PromoCategoriesVo   promoCategoriesPo;

    private List<PromoCompaniesVo> companiesPoList;

    /**
    * 接收指定门店
    * */
    @Valid
    private PromoStoresVo              stores;

    /**
     * 促销规则配置信息
     */

    @Valid
    private PromotionRuleVo promotionRule;

    /**
     * 优先级
     */
    private Long priority;

    /**
     * 简易描述
     */
    @Length(max=20, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
    private String simpleDescription;

    /**
     * 详细描述
     */
    @Length(max=200, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
    private String detailDescription;

    public String getSimpleDescription() { return simpleDescription; }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription; }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public PromotionRuleVo getPromotionRule() { return promotionRule; }

    public void setPromotionRule(PromotionRuleVo promotionRule) { this.promotionRule = promotionRule; }


    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
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



    public Integer getIsSuperposeProOrCouDiscount() {
        return isSuperposeProOrCouDiscount;
    }



    public void setIsSuperposeProOrCouDiscount(Integer isSuperposeProOrCouDiscount) {
        this.isSuperposeProOrCouDiscount = isSuperposeProOrCouDiscount;
    }



    public PromoCategoriesVo getPromoCategoriesPo() {
        return promoCategoriesPo;
    }



    public void setPromoCategoriesPo(PromoCategoriesVo promoCategoriesPo) {
        this.promoCategoriesPo = promoCategoriesPo;
    }



    public List<PromoCompaniesVo> getCompaniesPoList() {
        return companiesPoList;
    }



    public void setCompaniesPoList(List<PromoCompaniesVo> companiesPoList) {
        this.companiesPoList = companiesPoList;
    }



    public PromoStoresVo getStores() {
        return stores;
    }



    public void setStores(PromoStoresVo stores) {
        this.stores = stores;
    }
}
