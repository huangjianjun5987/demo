/**
 * 
 */
package com.yatang.sc.dto;

import java.io.Serializable;

import com.yatang.sc.promotion.dto.EachConditionGiveOnceDto;
import com.yatang.sc.promotion.dto.OrderRuleDto;
import com.yatang.sc.promotion.dto.PurchaseConditionsRuleDto;
import com.yatang.sc.promotion.dto.RewardListRuleDto;
import com.yatang.sc.promotion.dto.TotalPurchaseListRuleDto;

/**
 * 促销规则配置类
 * @author dengdongshan
 *
 */
public class PromotionRuleDto implements Serializable {
	private static final long serialVersionUID = 5918499476745561567L;
	/**
	 * 使用条件
	 */
	private boolean useConditionRule;
	/**
	 *优惠种类
	 */
	private String ruleName;

	/**
	 * 使用条件不限制，优惠方式
	 */
	private OrderRuleDto orderRule;

	/**
	 * 下拉框选择购买条件时存的值
	 */
	private PurchaseConditionsRuleDto purchaseConditionsRule;
	
	
	/**
	 * 奖励列表
	 */
	private RewardListRuleDto rewardListRule;
	
	/**
	 * 整个奖励列表
	 */
	private TotalPurchaseListRuleDto totalPurchaseListRule;
	
	/**
	 * 每满足条件赠送一次
	 */
	private  EachConditionGiveOnceDto eachConditionGiveOnce;
	
	public EachConditionGiveOnceDto getEachConditionGiveOnce() {
		return eachConditionGiveOnce;
	}

	public void setEachConditionGiveOnce(EachConditionGiveOnceDto eachConditionGiveOnce) {
		this.eachConditionGiveOnce = eachConditionGiveOnce;
	}

	public boolean isUseConditionRule() {
		return useConditionRule;
	}

	public void setUseConditionRule(boolean useConditionRule) {
		this.useConditionRule = useConditionRule;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public OrderRuleDto getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(OrderRuleDto orderRule) {
		this.orderRule = orderRule;
	}

	public PurchaseConditionsRuleDto getPurchaseConditionsRule() {
		return purchaseConditionsRule;
	}

	public void setPurchaseConditionsRule(PurchaseConditionsRuleDto purchaseConditionsRule) {
		this.purchaseConditionsRule = purchaseConditionsRule;
	}

	public RewardListRuleDto getRewardListRule() {
		return rewardListRule;
	}

	public void setRewardListRule(RewardListRuleDto rewardListRule) {
		this.rewardListRule = rewardListRule;
	}

	public TotalPurchaseListRuleDto getTotalPurchaseListRule() {
		return totalPurchaseListRule;
	}

	public void setTotalPurchaseListRule(TotalPurchaseListRuleDto totalPurchaseListRule) {
		this.totalPurchaseListRule = totalPurchaseListRule;
	}

	/**
     * 規則名稱枚举
     * PURCHASECONDITION,REWARDLIST 优惠种类下来框
     * @author dengdongshan
     *
     */
    public enum RuleName {

    	PURCHASECONDITION("PURCHASECONDITION"), REWARDLIST("REWARDLIST"),TOTALPUCHASELIST("TOTALPUCHASELIST"),EACHCONDITIONGIVEONCE("EACHCONDITIONGIVEONCE");
        private String name;

        private RuleName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
