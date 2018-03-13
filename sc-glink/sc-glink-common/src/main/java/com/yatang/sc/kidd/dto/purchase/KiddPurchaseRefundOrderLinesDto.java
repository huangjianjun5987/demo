package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;

/**
 * @description: 采购退货单货品详情(所有字段必填)
 * @author: yinyuxin
 * @date: 2017/11/8 19:25
 * @version: v1.0
 */
public class KiddPurchaseRefundOrderLinesDto implements Serializable {

	private static final long	serialVersionUID	= 3666909998603793126L;

	/**
	 * 退货单明细ID
	 */
	private String				orderLineNo;

	/**
	 * 退货单仓库编码(逻辑仓编码)在“仓库信息表—逻辑仓”中的所属子公司编码--心怡专用
	 */
	private String				ownerCode;

	/**
	 * 退货单商品编码
	 */
	private String				itemCode;

	/**
	 * 退货单商品名称
	 */
	private String				itemName;

	/**
	 * 退货单商品退货数量
	 */
	private Integer				planQty;
	/**
	 * 退货单商品单位--际链专用
	 */
	private String				unit;



	public String getOrderLineNo() {
		return orderLineNo;
	}



	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}



	public String getOwnerCode() {
		return ownerCode;
	}



	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
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



	public Integer getPlanQty() {
		return planQty;
	}



	public void setPlanQty(Integer planQty) {
		this.planQty = planQty;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}

}
