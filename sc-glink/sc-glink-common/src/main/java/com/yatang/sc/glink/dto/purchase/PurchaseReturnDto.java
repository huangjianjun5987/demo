package com.yatang.sc.glink.dto.purchase;

import java.io.Serializable;

/**
 * @description: 际链采购退货dto
 * @author zhoubaiyun
 * @date: 2017/11/8 16:46
 * @version: v1.0
 */
public class PurchaseReturnDto implements Serializable {

	private static final long		serialVersionUID	= 4547443703347481411L;

	/**
	 * 订单
	 */
	private PurchaseReturnOrderDto	order;



	public PurchaseReturnOrderDto getOrder() {
		return order;
	}



	public void setOrder(PurchaseReturnOrderDto order) {
		this.order = order;
	}

}
