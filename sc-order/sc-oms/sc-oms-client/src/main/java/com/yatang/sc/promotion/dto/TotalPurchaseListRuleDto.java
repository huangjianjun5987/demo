/**
 * 
 */
package com.yatang.sc.promotion.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 整个购买列表
 * 
 * @author dengdongshan
 *
 */
public class TotalPurchaseListRuleDto implements Serializable {

	private static final long serialVersionUID = -8548515106703495091L;

	private List<RuleConditionsDto> conditions;

	private OrderRuleDto rule;

	public List<RuleConditionsDto> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleConditionsDto> conditions) {
		this.conditions = conditions;
	}

	public OrderRuleDto getRule() {
		return rule;
	}

	public void setRule(OrderRuleDto rule) {
		this.rule = rule;
	}

}
