package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月9日
 */
public class kiddPurchaseRefundConfirmOrderLinesDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String				orderLineNo;				// 50 必填 商品行号
	private String				itemCode;					// 必填 商品编码
	private String				name;						// 商品名称
	private String				unit;						// 最小规格单位箱、瓶、
	private Integer				quantity;					// 商品计划数量
	private Integer				actualQty;					// 必填 商品实际发货数量
	private String				inventoryTy;				// ng 50 库存类型，ZP=正品,
															// CC=残次,JS=机损, XS=
															// 箱损,
															// ZT=在途库存，默认为正品
	private String				productDate;				// 20
															// 商品生产日期YYYY-MM-DD
	private String				expireDate;					// 1
															// 商品过期日期YYYY-MM-DD
	private String				produceCode;				// 50 生产批号
	private String				batchCode;					// 批次编码



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



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public Integer getQuantity() {
		return quantity;
	}



	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}



	public Integer getActualQty() {
		return actualQty;
	}



	public void setActualQty(Integer actualQty) {
		this.actualQty = actualQty;
	}



	public String getInventoryTy() {
		return inventoryTy;
	}



	public void setInventoryTy(String inventoryTy) {
		this.inventoryTy = inventoryTy;
	}



	public String getProductDate() {
		return productDate;
	}



	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}



	public String getExpireDate() {
		return expireDate;
	}



	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}



	public String getProduceCode() {
		return produceCode;
	}



	public void setProduceCode(String produceCode) {
		this.produceCode = produceCode;
	}



	public String getBatchCode() {
		return batchCode;
	}



	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

}
