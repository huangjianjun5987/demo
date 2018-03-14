/**
 * 
 */
package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 整个购买列表
 * 
 * @author dengdongshan
 *
 */
public class TotalPurchaseListRulePo implements Serializable {

	private static final long serialVersionUID = -8548515106703495091L;

	private List<RuleConditionsPo> conditions;

	private OrderRulePo rule;

	public List<RuleConditionsPo> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleConditionsPo> conditions) {
		this.conditions = conditions;
	}

	public OrderRulePo getRule() {
		return rule;
	}

	public void setRule(OrderRulePo rule) {
		this.rule = rule;
	}

}
