/**
 * 
 */
package com.yatang.sc.promotion.dto;

import java.io.Serializable;

import com.yatang.sc.dto.PromoProductDto;

/**
 * 条件
 * 
 * @author dengdongshan
 *
 */
public class GiveRuleConditionsDto implements Serializable {

	private static final long serialVersionUID = -711592110104675299L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String purchaseType;

	private PromoProductDto promoProduct;

	private OrderRuleDto rule;

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public PromoProductDto getPromoProduct() {
		return promoProduct;
	}

	public void setPromoProduct(PromoProductDto promoProduct) {
		this.promoProduct = promoProduct;
	}

	public OrderRuleDto getRule() {
		return rule;
	}

	public void setRule(OrderRuleDto rule) {
		this.rule = rule;
	}

	/**
	 * 购买类型枚举 CATEGORY,PRODUCT
	 * 
	 * @author dengdongshan
	 *
	 */
	public enum GivePurchaseType {
		PRODUCT("PRODUCT");

		private String name;

		private GivePurchaseType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
