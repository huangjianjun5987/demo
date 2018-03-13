package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月9日
 */
public class kiddPurchaseRefundConfirmPackagesItemsDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String				itemCode;					// 商品编码
	private String				itemId;						// 商品仓储系统编码
	private Integer				quantity;					// 包裹内该商品的数量



	public String getItemCode() {
		return itemCode;
	}



	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}



	public String getItemId() {
		return itemId;
	}



	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



	public Integer getQuantity() {
		return quantity;
	}



	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
