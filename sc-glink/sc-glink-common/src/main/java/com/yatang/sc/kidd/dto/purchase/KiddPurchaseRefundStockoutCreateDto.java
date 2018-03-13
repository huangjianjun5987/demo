package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 采购退货单下发仓库参数dto
 * @author: yinyuxin
 * @date: 2017/11/15 15:23
 * @version: v1.0
 */
public class KiddPurchaseRefundStockoutCreateDto implements Serializable {

	private static final long						serialVersionUID	= 3567410342256559674L;

	private KiddPurchaseReturnDto					deliveryOrder;
	private KiddPurchaseReturnDto					order;

	/**
	 * 货品详情
	 */
	private List<KiddPurchaseRefundOrderLinesDto>	orderLines;



	public KiddPurchaseReturnDto getOrder() {
		return order;
	}



	public void setOrder(KiddPurchaseReturnDto order) {
		this.order = order;
	}



	public KiddPurchaseReturnDto getDeliveryOrder() {
		return deliveryOrder;
	}



	public void setDeliveryOrder(KiddPurchaseReturnDto deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}



	public List<KiddPurchaseRefundOrderLinesDto> getOrderLines() {
		return orderLines;
	}



	public void setOrderLines(List<KiddPurchaseRefundOrderLinesDto> orderLines) {
		this.orderLines = orderLines;
	}
}
