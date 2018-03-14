package com.yatang.sc.operation.vo;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class TotalPurchaseListRuleVo implements Serializable {

    private static final long serialVersionUID = -8548515106703495091L;

    @Valid
    private List<RuleConditionsVo> conditions;

    private OrderRuleVo rule;

    public List<RuleConditionsVo> getConditions() {
        return conditions;
    }

    public void setConditions(List<RuleConditionsVo> conditions) {
        this.conditions = conditions;
    }

    public OrderRuleVo getRule() {
        return rule;
    }

    public void setRule(OrderRuleVo rule) {
        this.rule = rule;
    }
}
