package com.yatang.sc.order.domain;

import java.io.Serializable;

/**
 * @description: 优惠券po
 * @author: yinyuxin
 * @date: 2017/9/19 17:24
 * @version: v1.0
 */
public class CouponsPo extends PromotionPo implements Serializable {

	private Long totalQuantity;                  //发放数量

	private Long personQty;                      //每人领取数量

	private Long grantQty;                       //领取数量

	private Long usedQty;                        //使用数量

	private String grantChannel;                 //发放方式 ('personal'、'platform')

	private String couponType;					//优惠券类型('default'、'toGive')
	
	private static final long serialVersionUID = 1L;



	public Long getTotalQuantity() {
		return totalQuantity;
	}



	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}



	public Long getPersonQty() {
		return personQty;
	}



	public void setPersonQty(Long personQty) {
		this.personQty = personQty;
	}



	public Long getGrantQty() {
		return grantQty;
	}



	public void setGrantQty(Long grantQty) {
		this.grantQty = grantQty;
	}



	public Long getUsedQty() {
		return usedQty;
	}



	public void setUsedQty(Long usedQty) {
		this.usedQty = usedQty;
	}



	public String getGrantChannel() {
		return grantChannel;
	}



	public void setGrantChannel(String grantChannel) {
		this.grantChannel = grantChannel == null ? null : grantChannel.trim();
	}



	public String getCouponType() {
		return couponType;
	}



	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	
}