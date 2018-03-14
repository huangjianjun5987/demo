/**
 * 
 */
package com.yatang.sc.purchase.rule.validator.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.validator.PromotionRuleValidator;
import com.yatang.sc.purchase.rule.validator.RuleExecutor;

/**
 * @author dengdongshan
 *
 */
@Service
public class PricingModelRuleExecutorImpl implements RuleExecutor {

	private List<PromotionRuleValidator> promotionRuleValidators;
	@Autowired
	private CompaniesRule companiesRule;
	@Autowired
	private StoreRule storeRule;
	@Autowired
	private SuperposeRule superposeRule;
	@Autowired
	private PurchaseConditionsRule purchaseConditionsRule;
	@Autowired
	private RewardListRule rewardListRule;
	@Autowired
	private TotalPurchaseListRule totalPurchaseListRule;
	@Autowired
	private EachConditionGiveOnceRule eachConditionGiveOnceRule;

	@PostConstruct
	public void fillRuleList() {
		promotionRuleValidators = new ArrayList<>();
		promotionRuleValidators.add(companiesRule);
		promotionRuleValidators.add(storeRule);
		promotionRuleValidators.add(superposeRule);
		promotionRuleValidators.add(purchaseConditionsRule);
		promotionRuleValidators.add(rewardListRule);
		promotionRuleValidators.add(totalPurchaseListRule);
		promotionRuleValidators.add(eachConditionGiveOnceRule);
	}

	@Override
	public void excute(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		List<PricingModel> validtedPricingModels;
		boolean foundOne=false;
		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel = iterator.next();
			validtedPricingModels = new ArrayList<>();
			validtedPricingModels.add(pricingModel);
			for (PromotionRuleValidator promotionRuleValidator : promotionRuleValidators) {
				if (validtedPricingModels.isEmpty()) {
					break;
				}
				promotionRuleValidator.validation(order, validtedPricingModels, contentMap);
			}
			if(foundOne){
				iterator.remove();
				continue;
			}
			if (!validtedPricingModels.isEmpty()) {
				foundOne=true;
				continue;
			}
			if (validtedPricingModels.isEmpty()) {
				iterator.remove();
				continue;
			}
		}
		
	}

}
