package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券领取记录po
 * @author yinyuxin
 * @date 2017年9月19日14:24:15
 */
public class CouponActivityPo implements Serializable {
	private Long id;                                       //主键id

	private String storeId;                                //门店ID

	private String promoId;                                //优惠券id

	private Date activityDate;                             //激活时间

	private String state;                                  //状态(active:已领取,used:已使用,canceled:已作废)

	private String modifyUser;								//(作废操作)修改人id

	private Date modifyTime;								//作废时间

	private static final long serialVersionUID = 1L;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId == null ? null : storeId.trim();
	}



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId == null ? null : promoId.trim();
	}



	public Date getActivityDate() {
		return activityDate;
	}



	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}