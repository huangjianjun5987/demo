package com.yatang.sc.operation.vo;



import com.yatang.sc.operation.vo.supplier.EachConditionGiveOnceVo;
import com.yatang.sc.promotion.dto.TotalPurchaseListRuleDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 促销规则配置类
 * @author dengdongshan
 *
 */
public class PromotionRuleVo implements Serializable {
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
    @Valid
    private OrderRuleVo orderRule;

    /**
     * 下拉框选择购买条件
     */
    @Valid
    private PurchaseConditionsRuleVo purchaseConditionsRule;

    /**
     * 奖励列表
     */
    @Valid
    private RewardListRuleVo rewardListRule;

    /**
     * 整个奖励列表
     */
    @Valid
    private TotalPurchaseListRuleVo totalPurchaseListRule;

    /**
     * 每满足条件赠送一次
     */
    @Valid
    private EachConditionGiveOnceVo eachConditionGiveOnce;

    public EachConditionGiveOnceVo getEachConditionGiveOnce() { return eachConditionGiveOnce; }

    public void setEachConditionGiveOnce(EachConditionGiveOnceVo eachConditionGiveOnce) { this.eachConditionGiveOnce = eachConditionGiveOnce; }

    public TotalPurchaseListRuleVo getTotalPurchaseListRule() {
        return totalPurchaseListRule;
    }

    public void setTotalPurchaseListRule(TotalPurchaseListRuleVo totalPurchaseListRule) {
        this.totalPurchaseListRule = totalPurchaseListRule;
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

    public OrderRuleVo getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(OrderRuleVo orderRule) {
        this.orderRule = orderRule;
    }

    public PurchaseConditionsRuleVo getPurchaseConditionsRule() {
        return purchaseConditionsRule;
    }

    public void setPurchaseConditionsRule(PurchaseConditionsRuleVo purchaseConditionsRule) {
        this.purchaseConditionsRule = purchaseConditionsRule;
    }

    public RewardListRuleVo getRewardListRule() {
        return rewardListRule;
    }

    public void setRewardListRule(RewardListRuleVo rewardListRule) {
        this.rewardListRule = rewardListRule;
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

