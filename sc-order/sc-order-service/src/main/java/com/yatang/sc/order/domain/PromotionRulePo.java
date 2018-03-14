/**
 * 
 */
package com.yatang.sc.order.domain;
import java.io.Serializable;

/**
 * 促销规则配置类
 * @author dengdongshan
 *
 */
public class PromotionRulePo implements Serializable {
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
	private OrderRulePo orderRule;

	/**
	 * 下拉框选择购买条件时存的值
	 */
	private PurchaseConditionsRulePo purchaseConditionsRule;
	
	
	/**
	 * 奖励列表
	 */
	private RewardListRulePo rewardListRule;
	
	/**
	 * 整个奖励列表
	 */
	private TotalPurchaseListRulePo totalPurchaseListRule;

	/**
	 * 每满足条件赠送一次
	 */
	private  EachConditionGiveOncePo eachConditionGiveOnce;

	public EachConditionGiveOncePo getEachConditionGiveOnce() { return eachConditionGiveOnce; }

	public void setEachConditionGiveOnce(EachConditionGiveOncePo eachConditionGiveOnce) { this.eachConditionGiveOnce = eachConditionGiveOnce; }

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

	public OrderRulePo getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(OrderRulePo orderRule) {
		this.orderRule = orderRule;
	}

	public PurchaseConditionsRulePo getPurchaseConditionsRule() {
		return purchaseConditionsRule;
	}

	public void setPurchaseConditionsRule(PurchaseConditionsRulePo purchaseConditionsRule) {
		this.purchaseConditionsRule = purchaseConditionsRule;
	}

	public RewardListRulePo getRewardListRule() {
		return rewardListRule;
	}

	public void setRewardListRule(RewardListRulePo rewardListRule) {
		this.rewardListRule = rewardListRule;
	}

	public TotalPurchaseListRulePo getTotalPurchaseListRule() {
		return totalPurchaseListRule;
	}

	public void setTotalPurchaseListRule(TotalPurchaseListRulePo totalPurchaseListRule) {
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
