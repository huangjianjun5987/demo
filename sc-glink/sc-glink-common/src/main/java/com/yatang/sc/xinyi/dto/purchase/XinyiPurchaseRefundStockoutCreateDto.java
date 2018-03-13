package com.yatang.sc.xinyi.dto.purchase;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 心怡出库单创建接口请求参数dto
 * @author: yinyuxin
 * @date: 2017/11/15 15:14
 * @version: v1.0
 */
public class XinyiPurchaseRefundStockoutCreateDto implements Serializable{

	private static final long serialVersionUID = -4972633595074595873L;


	private XinyiPurchaseReturnParmDto deliveryOrder;

	/**
	 * 货品详情
	 */
	private List<XinyiPurchaseRefundOrderLinesDto> orderLines;



	public XinyiPurchaseReturnParmDto getDeliveryOrder() {
		return deliveryOrder;
	}



	public void setDeliveryOrder(XinyiPurchaseReturnParmDto deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}



	public List<XinyiPurchaseRefundOrderLinesDto> getOrderLines() {
		return orderLines;
	}



	public void setOrderLines(List<XinyiPurchaseRefundOrderLinesDto> orderLines) {
		this.orderLines = orderLines;
	}
}
