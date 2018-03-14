package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;

/**
 * @description: 商品品牌值清单vo
 * @author: yinyuxin
 * @date: 2017/10/19 17:35
 * @version: v1.0
 */
public class PmPurchaseRefundBrandVo implements Serializable{

	private static final long serialVersionUID = -3909450731449939166L;

	/**
	 * 品牌id
	 */
	private String productBrandId;

	/**
	 * 品牌名称
	 */
	private String productBrandName;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public String getProductBrandId() {
		return productBrandId;
	}



	public void setProductBrandId(String productBrandId) {
		this.productBrandId = productBrandId;
	}



	public String getProductBrandName() {
		return productBrandName;
	}



	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}
}
