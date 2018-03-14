package com.yatang.sc.order.domain;

import java.io.Serializable;

public class GiveRuleConditionsPo implements Serializable {

	private static final long serialVersionUID = 6098213903406512638L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String purchaseType;

	private PromoProductPo promoProduct;

	private OrderRulePo rule;

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public PromoProductPo getPromoProduct() {
		return promoProduct;
	}

	public void setPromoProduct(PromoProductPo promoProduct) {
		this.promoProduct = promoProduct;
	}

	public OrderRulePo getRule() {
		return rule;
	}

	public void setRule(OrderRulePo rule) {
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
