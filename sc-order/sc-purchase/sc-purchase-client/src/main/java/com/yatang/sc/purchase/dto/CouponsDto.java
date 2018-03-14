package com.yatang.sc.purchase.dto;

import java.io.Serializable;

/**
 * @description: 优惠券Dto
 * @author: yinyuxin
 * @date: 2017/9/19 17:24
 * @version: v1.0
 */
public class CouponsDto extends PromotionDto implements Serializable {

	private static final long serialVersionUID = -2582994219339628384L;

	private Long totalQuantity;                  //发放数量

	private Long personQty;                      //每人领取数量

	private Long grantQty;                       //领取数量

	private Long usedQty;                        //使用数量

	private String grantChannel;                 //发放方式 ('personal'、'platform')

	private Integer             couPonStatus;   //0：已抢光   1:可领取   2：已领取

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



	public Integer getCouPonStatus() {
		return couPonStatus;
	}



	public void setCouPonStatus(Integer couPonStatus) {
		this.couPonStatus = couPonStatus;
	}
}