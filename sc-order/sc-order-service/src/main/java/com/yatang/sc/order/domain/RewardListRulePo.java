/**
 * 
 */
package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengdongshan
 *
 */
public class RewardListRulePo implements Serializable {

	private static final long serialVersionUID = 836847968597065549L;

	private List<RuleConditionsPo> conditions;

	private PurchaseConditionsRulePo purchaseConditionsRule;

	public List<RuleConditionsPo> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleConditionsPo> conditions) {
		this.conditions = conditions;
	}

	public PurchaseConditionsRulePo getPurchaseConditionsRule() {
		return purchaseConditionsRule;
	}

	public void setPurchaseConditionsRule(PurchaseConditionsRulePo purchaseConditionsRule) {
		this.purchaseConditionsRule = purchaseConditionsRule;
	}

	
}
