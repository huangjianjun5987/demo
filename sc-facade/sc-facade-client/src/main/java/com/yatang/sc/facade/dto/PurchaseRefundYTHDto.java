package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.util.List;

public class PurchaseRefundYTHDto implements Serializable {

	private static final long					serialVersionUID	= 1L;
	private PurchaseRefundOrderDto				entryOrder;					// 采购确认单基本信息
	private List<PurchaseRefundOrderItemDto>	orderLines;					// 商品详细信息



	public PurchaseRefundOrderDto getEntryOrder() {
		return entryOrder;
	}



	public void setEntryOrder(PurchaseRefundOrderDto entryOrder) {
		this.entryOrder = entryOrder;
	}



	public List<PurchaseRefundOrderItemDto> getOrderLines() {
		return orderLines;
	}



	public void setOrderLines(List<PurchaseRefundOrderItemDto> orderLines) {
		this.orderLines = orderLines;
	}

}
