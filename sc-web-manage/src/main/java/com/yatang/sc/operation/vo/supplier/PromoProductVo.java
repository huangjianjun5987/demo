package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;

public class PromoProductVo implements Serializable {

	private static final long serialVersionUID = 1694159723090723357L;

	/**
	 * 分类ID
	 * */
	private String              productId;

	/**
	 * 类名
	 * */
	private String              productName;


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
