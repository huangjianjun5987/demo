package com.yatang.sc.operation.vo.supplier;

import com.yatang.sc.operation.vo.RuleConditionsVo;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class EachConditionGiveOnceVo implements Serializable{
	
	private static final long serialVersionUID = 1726274185889638349L;

	@Valid
	private List<RuleConditionsVo> conditions;
	@Valid
	private GiveRuleConditionsVo giveRuleCondition;

	public List<RuleConditionsVo> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleConditionsVo> conditions) {
		this.conditions = conditions;
	}

	public GiveRuleConditionsVo getGiveRuleCondition() {
		return giveRuleCondition;
	}

	public void setGiveRuleCondition(GiveRuleConditionsVo giveRuleCondition) {
		this.giveRuleCondition = giveRuleCondition;
	}
}
