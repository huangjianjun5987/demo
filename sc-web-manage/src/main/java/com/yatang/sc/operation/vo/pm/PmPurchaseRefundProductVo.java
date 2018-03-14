package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;

/**
 * @description: 商品值清单vo
 * @author: yinyuxin
 * @date: 2017/10/19 17:04
 * @version: v1.0
 */
public class PmPurchaseRefundProductVo implements Serializable{

	private static final long serialVersionUID = 4986674393577410287L;

	private String productCode;

	private String productName;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public String getProductCode() {
		return productCode;
	}



	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}
}
