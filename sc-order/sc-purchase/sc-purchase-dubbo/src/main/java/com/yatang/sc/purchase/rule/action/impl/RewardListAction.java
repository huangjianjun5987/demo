/**
 * 
 */
package com.yatang.sc.purchase.rule.action.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.promotion.dto.PurchaseConditionsRuleDto;
import com.yatang.sc.promotion.dto.RuleConditionsDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;

/**
 * 奖励列表执行类
 * @author dengdongshan
 *
 */
@Service
public class RewardListAction extends PurchaseConditionsAction {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see com.yatang.sc.purchase.action.service.PromotionAction#apply(com.yatang.sc.purchase.dto.OrderPriceInfoDto, com.yatang.sc.purchase.dto.OrderDto, java.util.List, java.util.Map)
	 */
	@Override
	public void apply(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
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
				continue;
			}
			if(promotionRule.getRewardListRule().getPurchaseConditionsRule().getRule()==null||promotionRule.getRewardListRule().getPurchaseConditionsRule().getRule().getPreferentialValue()==null){
	    		logger.error("promotion id {} data error",pricingModel.getId());
	    		return;
	    	}
			PurchaseConditionsRuleDto purchaseConditionsRule = promotionRule.getRewardListRule().getPurchaseConditionsRule();
			String purchaseType = purchaseConditionsRule.getCondition().getPurchaseType();
			if(RuleConditionsDto.PurchaseType.CATEGORY.getName().equals(purchaseType)){
				applyCategory(order,purchaseConditionsRule, pricingModel,contentMap);
			}
			if(RuleConditionsDto.PurchaseType.PRODUCT.getName().equals(purchaseType)){
				applyProduct(order, purchaseConditionsRule, pricingModel,contentMap);
			}
			if(RuleConditionsDto.PurchaseType.ALL.getName().equals(purchaseType)){
				applyAll(order, purchaseConditionsRule, pricingModel,contentMap);
			}
		}
	}

}
