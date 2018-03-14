package com.yatang.sc.operation.vo;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author dengdongshan。奖励列表
 *
 */
public class RewardListRuleVo implements Serializable {

    private static final long serialVersionUID = 836847968597065549L;

    /**
     * 购买条件集合
     */
    @Valid
    private List<RuleConditionsVo> conditions;


    private PurchaseConditionsRuleVo purchaseConditionsRule;

    public List<RuleConditionsVo> getConditions() {
        return conditions;
    }

    public void setConditions(List<RuleConditionsVo> conditions) {
        this.conditions = conditions;
    }

    public PurchaseConditionsRuleVo getPurchaseConditionsRule() {
        return purchaseConditionsRule;
    }

    public void setPurchaseConditionsRule(PurchaseConditionsRuleVo purchaseConditionsRule) {
        this.purchaseConditionsRule = purchaseConditionsRule;
    }


}
