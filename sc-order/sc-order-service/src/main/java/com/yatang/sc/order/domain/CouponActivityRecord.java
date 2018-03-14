package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @描述: 优惠券领取记录
 * @类名: CouponActivity
 * @作者: kangdong
 * @创建时间: 2017/9/19 13:58
 * @版本: v1.0
 */
public class CouponActivityRecord implements Serializable {

	private static final long	serialVersionUID	= 761115609161981486L;
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
	private Date				avtivityDate;
	/**
	 * 状态(active,used)
	 */
	private String				state;

	/**
	 * 优惠券
	 */
	private PromotionPo			promotion;



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



	public Date getAvtivityDate() {
		return avtivityDate;
	}



	public void setAvtivityDate(Date avtivityDate) {
		this.avtivityDate = avtivityDate;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public PromotionPo getPromotion() {
		return promotion;
	}



	public void setPromotion(PromotionPo promotion) {
		this.promotion = promotion;
	}
}
