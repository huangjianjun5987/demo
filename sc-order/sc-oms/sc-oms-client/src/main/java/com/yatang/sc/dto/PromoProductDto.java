package com.yatang.sc.dto;

import java.io.Serializable;

/**
 *  促销商品
 * @author dengdongshan
 */
public class PromoProductDto implements Serializable {

	private static final long serialVersionUID = 4290274745488125333L;

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
