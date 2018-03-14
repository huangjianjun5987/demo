/**
 * 
 */
package com.yatang.sc.promotion.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 每满
 * 
 * @author dengdongshan
 *
 */
public class EachConditionGiveOnceDto implements Serializable {

	private static final long serialVersionUID = 6175960616599386163L;

	private List<RuleConditionsDto> conditions;

	private GiveRuleConditionsDto giveRuleCondition;

	public List<RuleConditionsDto> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleConditionsDto> conditions) {
		this.conditions = conditions;
	}

	public GiveRuleConditionsDto getGiveRuleCondition() {
		return giveRuleCondition;
	}

	public void setGiveRuleCondition(GiveRuleConditionsDto giveRuleCondition) {
		this.giveRuleCondition = giveRuleCondition;
	}

}
