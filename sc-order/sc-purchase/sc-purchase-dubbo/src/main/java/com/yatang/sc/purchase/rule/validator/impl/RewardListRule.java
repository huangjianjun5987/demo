/**
 * 
 */
package com.yatang.sc.purchase.rule.validator.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.promotion.dto.RuleConditionsDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;

/**
 * 
 * 奖励列表规则
 * 
 * 需要满足购买的条件，对配置商品进行促销应用
 * @author dengdongshan
 *
 */
@Service
public class RewardListRule extends PurchaseConditionsRule {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void validation(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		String storeId = (String) contentMap.get("storeId");
		String branchCompanyId = (String) contentMap.get("branchCompanyId");
		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel = iterator.next();
			PromotionRuleDto promotionRule = pricingModel.getPromotionRule();
			if(!promotionRule.isUseConditionRule()){
				continue;
			};
			if(!PromotionRuleDto.RuleName.REWARDLIST.getName().equals(promotionRule.getRuleName())){
				continue;
			}
			if(promotionRule.getRewardListRule()==null||promotionRule.getRewardListRule().getPurchaseConditionsRule()==null||promotionRule.getRewardListRule().getPurchaseConditionsRule().getCondition()==null||promotionRule.getRewardListRule().getConditions()==null){
				iterator.remove();
				logger.warn("promotion {} rule data error",pricingModel.getId());
				continue;
			}
			if (!isPass(order, promotionRule.getRewardListRule().getConditions(), contentMap)) {
				iterator.remove();
				logger.info("promotion {} rule not pass so removed, branchCompanyId {}, storeId{}.",pricingModel.getId(),branchCompanyId,storeId);
				continue;
			}
		}
	}

	public boolean isPass(OrderDto order, List<RuleConditionsDto> conditions, Map contentMap){
		for (Iterator<RuleConditionsDto> iterator = conditions.iterator(); iterator.hasNext();) {
			RuleConditionsDto ruleConditionsDto =  iterator.next();
			if (!super.isPass(order, ruleConditionsDto, contentMap)) {
				return false;
			}
		}
		return true;
	}
}
