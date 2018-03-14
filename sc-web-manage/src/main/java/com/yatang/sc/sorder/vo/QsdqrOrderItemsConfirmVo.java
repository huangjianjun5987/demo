package com.yatang.sc.sorder.vo;

import java.io.Serializable;

/**
 * @description: 供应商直送 已签收待确认订单的签收商品数量 接口参数vo
 * @author: yinyuxin
 * @date: 2018/1/17 15:34
 * @version: v1.0
 */
public class QsdqrOrderItemsConfirmVo implements Serializable{

	private static final long serialVersionUID = 8115755675417570282L;

	/**
	 * commerce_item表主键id
	 */
	private Long commerceId;

	/**
	 * 签收数量
	 */
	private Long completedQuantity;



	public Long getCommerceId() {
		return commerceId;
	}



	public void setCommerceId(Long commerceId) {
		this.commerceId = commerceId;
	}



	public Long getCompletedQuantity() {
		return completedQuantity;
	}



	public void setCompletedQuantity(Long completedQuantity) {
		this.completedQuantity = completedQuantity;
	}
}
