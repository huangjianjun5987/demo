/**
 * 
 */
package com.yatang.sc.order.domain;

import java.io.Serializable;

/**
 * 
 * 促销购买条件优惠种类
 * @author dengdongshan
 *
 */
public class PurchaseConditionsRulePo implements Serializable{

	private static final long serialVersionUID = 2356246936419422811L;
	private RuleConditionsPo condition;
    private OrderRulePo rule;
    
	public RuleConditionsPo getCondition() {
		return condition;
	}

	public void setCondition(RuleConditionsPo condition) {
		this.condition = condition;
	}

	public OrderRulePo getRule() {
		return rule;
	}

	public void setRule(OrderRulePo rule) {
		this.rule = rule;
	}


}
