package com.yatang.sc.glink.dto.purchase;

import java.io.Serializable;

public class PurchaseReturnOrderLinesDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String				orderLineNo;				// 退货单明细ID
	private String				itemCode;					// 退货单商品编码
	private String				itemName;					// 退货单商品名称
	private String				unit;						// 退货单商品单位
	private Double				planQty;					// 退货单商品退货数量
	private String				ownerCode;



	public String getOwnerCode() {
		return ownerCode;
	}



	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}



	public String getOrderLineNo() {
		return orderLineNo;
	}



	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}



	public String getItemCode() {
		return itemCode;
	}



	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}



	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public Double getPlanQty() {
		return planQty;
	}



	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}

}
