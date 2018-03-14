/**
 * 
 */
package com.yatang.sc.promotion.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengdongshan
 *
 */
public class RewardListRuleDto implements Serializable {

	private static final long serialVersionUID = 836847968597065549L;

	private List<RuleConditionsDto> conditions;

	private PurchaseConditionsRuleDto purchaseConditionsRule;

	public List<RuleConditionsDto> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleConditionsDto> conditions) {
		this.conditions = conditions;
	}

	public PurchaseConditionsRuleDto getPurchaseConditionsRule() {
		return purchaseConditionsRule;
	}

	public void setPurchaseConditionsRule(PurchaseConditionsRuleDto purchaseConditionsRule) {
		this.purchaseConditionsRule = purchaseConditionsRule;
	}

	
}
