/**
 * 
 */
package com.yatang.sc.promotion.dto;

import java.io.Serializable;

/**
 * 
 * 促销购买条件优惠种类
 * @author dengdongshan
 *
 */
public class PurchaseConditionsRuleDto implements Serializable{

	private static final long serialVersionUID = 2356246936419422811L;
	private RuleConditionsDto condition;
    private OrderRuleDto rule;
    
	public RuleConditionsDto getCondition() {
		return condition;
	}

	public void setCondition(RuleConditionsDto condition) {
		this.condition = condition;
	}

	public OrderRuleDto getRule() {
		return rule;
	}

	public void setRule(OrderRuleDto rule) {
		this.rule = rule;
	}


}
