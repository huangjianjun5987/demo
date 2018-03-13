package com.yatang.sc.juban.dto.purchase;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 心怡出库单创建接口请求参数dto
 * @author: yinyuxin
 * @date: 2017/11/15 15:14
 * @version: v1.0
 */
public class JubanPurchaseRefundStockoutCreateDto implements Serializable {

	private static final long serialVersionUID = -4972633595074595873L;


	private JubanPurchaseReturnParmDto deliveryOrder;

	/**
	 * 货品详情
	 */
	private List<JubanPurchaseRefundOrderLinesDto> orderLines;

	public JubanPurchaseReturnParmDto getDeliveryOrder() {
		return deliveryOrder;
	}

	public void setDeliveryOrder(JubanPurchaseReturnParmDto deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}

	public List<JubanPurchaseRefundOrderLinesDto> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<JubanPurchaseRefundOrderLinesDto> orderLines) {
		this.orderLines = orderLines;
	}
}


