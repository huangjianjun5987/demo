package com.yatang.sc.operation.vo.coupon;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yatang.sc.operation.vo.PromotionVo;
import com.yatang.sc.validgroup.GroupOne;

/**
 * @description: 优惠券详情vo
 * @author: yinyuxin
 * @date: 2017/9/19 20:19
 * @version: v1.0
 */
public class CouponsDetailVo extends PromotionVo implements Serializable{

	private static final long serialVersionUID = 2578680035674476253L;

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Long totalQuantity;                  //发放数量

	private Long personQty;                      //每人领取数量

	private Long grantQty;                       //领取数量

	private Long usedQty;                        //使用数量

	@NotBlank(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private String grantChannel;                 //发放方式 ('personal'、'platform')

	private String couponType;					//优惠券类型('default'、'toGive')


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
		this.grantChannel = grantChannel;
	}



	public String getCouponType() {
		return couponType;
	}



	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	
}
