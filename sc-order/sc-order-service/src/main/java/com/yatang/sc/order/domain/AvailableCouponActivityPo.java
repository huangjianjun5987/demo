package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>查询商家可用优惠券结果PO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月20日
 */
public class AvailableCouponActivityPo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	// 实际优惠券ID
	private String				couponActivityId;
	// 优惠券种类ID
	private String				couponId;
	// 开始日期
	private Date				startDate;
	// 结束日期
	private Date				endDate;
	// 条件金额
	private BigDecimal			quanifyAmount;
	// 折扣
	private BigDecimal			discount;
	// 折扣类型
	private String				discountType;
	// 类名
	private String				categoryName;


	public String getCouponActivityId() {
		return couponActivityId;
	}

	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
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

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
