package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.List;

public class EachConditionGiveOncePo implements Serializable {

	private static final long serialVersionUID = -7082528350375706792L;

	private List<RuleConditionsPo> conditions;

	private GiveRuleConditionsPo giveRuleCondition;

	public List<RuleConditionsPo> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleConditionsPo> conditions) {
		this.conditions = conditions;
	}

	public GiveRuleConditionsPo getGiveRuleCondition() {
		return giveRuleCondition;
	}

	public void setGiveRuleCondition(GiveRuleConditionsPo giveRuleCondition) {
		this.giveRuleCondition = giveRuleCondition;
	}
}
