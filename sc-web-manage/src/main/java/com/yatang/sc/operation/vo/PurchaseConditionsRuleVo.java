package com.yatang.sc.operation.vo;

import javax.validation.Valid;
import java.io.Serializable;

/**
 *
 * 促销购买条件优惠种类
 * @author dengdongshan
 *
 */
public class PurchaseConditionsRuleVo implements Serializable {

    private static final long serialVersionUID = 2356246936419422811L;

    @Valid
    private RuleConditionsVo condition;

    @Valid
    private OrderRuleVo rule;

    public RuleConditionsVo getCondition() {
        return condition;
    }

    public void setCondition(RuleConditionsVo condition) {
        this.condition = condition;
    }

    public OrderRuleVo getRule() {
        return rule;
    }

    public void setRule(OrderRuleVo rule) {
        this.rule = rule;
    }


}

