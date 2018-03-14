package com.yatang.sc.purchase.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @描述: 优惠券领取记录
 * @类名: CouponActivity
 * @作者: kangdong
 * @创建时间: 2017/9/19 13:58
 * @版本: v1.0
 */
@Data
public class CouponActivityRecordDto implements Serializable {

	private static final long serialVersionUID = 729832959443174265L;
	/**
	 * ID
	 */
	private long				id;
	/**
	 * 门店ID
	 */
	private String				storeId;
	/**
	 * 优惠券
	 */
	private String				promoId;
	/**
	 * 时间
	 */
	/**
	 * 状态(active,used)
	 */
	private String				state;
	/**
	 * 编号
	 */
	private String				pId;

	/**
	 * 状态
	 */
	private String				status;

	/**
	 * 促销类型
	 */
	private String				promotionType;

	/**
	 * 活动名称
	 */
	private String				promotionName;

	/**
	 * 折扣描述
	 */
	private String				promotionDiscription;

	/**
	 * 开始时间
	 */
	private Date				startDate;

	/**
	 * 结束时间
	 */
	private Date				endDate;

	/**
	 * 折扣类型
	 */
	private String				discountType;

	/**
	 * 条件金额
	 */
	private BigDecimal			quanifyAmount;

	/**
	 * 折扣
	 */
	private BigDecimal			discount;

	/**
	 * 备注
	 */
	private String				note;
	/**
	 * 分类ID
	 */
	private String				categoryId;
	/**
	 * 类名
	 */
	private String				categoryName;

	/**
	 * 是否可用
	 */
	private Boolean				enabled;



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getpId() {
		return pId;
	}



	public void setpId(String pId) {
		this.pId = pId;
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



	public String getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public Boolean getEnabled() {
		return enabled;
	}



	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
