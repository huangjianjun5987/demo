package com.yatang.sc.order.domain.returned;

import java.io.Serializable;

/**
 * @描述: 退货/拒收数量
 * @类名: ReturnQuantityPo
 * @作者: kangdong
 * @创建时间: 2017/11/24 10:40
 * @版本: v1.0
 */
public class ReturnQuantityPo implements Serializable {

	private static final long	serialVersionUID	= -7108685341621245244L;
	private Long				id;
	private Long				returnQuantity;
	private Double				rawTotalPrice;								// 退换货总额



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getReturnQuantity() {
		return returnQuantity;
	}



	public void setReturnQuantity(Long returnQuantity) {
		this.returnQuantity = returnQuantity;
	}



	public Double getRawTotalPrice() {
		return rawTotalPrice;
	}



	public void setRawTotalPrice(Double rawTotalPrice) {
		this.rawTotalPrice = rawTotalPrice;
	}
}
