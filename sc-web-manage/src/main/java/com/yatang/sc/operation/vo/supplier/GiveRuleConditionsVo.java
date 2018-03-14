package com.yatang.sc.operation.vo.supplier;

import javax.validation.Valid;
import java.io.Serializable;

public class GiveRuleConditionsVo implements Serializable {

	private static final long serialVersionUID = 4066287730453614632L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String purchaseType;

	private PromoProductVo promoProduct;
	@Valid
	private OrderRuleVo rule;

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public PromoProductVo getPromoProduct() {
		return promoProduct;
	}

	public void setPromoProduct(PromoProductVo promoProduct) {
		this.promoProduct = promoProduct;
	}

	public OrderRuleVo getRule() {
		return rule;
	}

	public void setRule(OrderRuleVo rule) {
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
