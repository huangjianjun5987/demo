/**
 * 
 */
package com.yatang.sc.purchase.rule.action.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.action.ActionExecutor;
import com.yatang.sc.purchase.rule.action.PromotionAction;

/**
 * @author dengdongshan
 *
 */
@Service
public class PricingModelActionExecutorImpl implements ActionExecutor {
	private List<PromotionAction> promotionActions = new ArrayList<>();
	@Autowired
	private OrderRuleAction orderAction;
	@Autowired
	private PurchaseConditionsAction purchaseConditionsAction;
	@Autowired
	private RewardListAction rewardListAction;
	@Autowired
	private TotalPurchaseListAction totalPurchaseListAction;
	@Autowired
	private EachConditionGiveOnceAction eachConditionGiveOnceAction;

	@PostConstruct
	public void fillRuleList() {
		promotionActions.add(orderAction);
		promotionActions.add(purchaseConditionsAction);
		promotionActions.add(rewardListAction);
		promotionActions.add(totalPurchaseListAction);
		promotionActions.add(eachConditionGiveOnceAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yatang.sc.purchase.action.service.ActionExecutor#excute(com.yatang.sc.purchase.dto.OrderPriceInfoDto, com.yatang.sc.purchase.dto.OrderDto, java.util.List, java.util.Map)
	 */
	@Override
	public void excute(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		//判断是否有不能叠加的促销
		hasSuperPosePromotionInOrder(order, pricingModels);
		
		//如果使用优惠券并且有不能叠加的促销则不应用促销
		if(order.isUseCoupon()&&order.isHasNotSuperposedPromotion()){
			return;
		}
		for (PromotionAction promotionAction : promotionActions) {
			promotionAction.apply(order, pricingModels, contentMap);
		}
		
		
	}

	private void hasSuperPosePromotionInOrder(OrderDto order, List<PricingModel> pricingModels) {
		
		if(pricingModels==null||pricingModels.isEmpty()){
			order.setHasNotSuperposedPromotion(false);
			return;
		}
		for (Iterator iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel = (PricingModel) iterator.next();
			Boolean superposePromo = pricingModel.getSuperposePromo();
			order.setHasNotSuperposedPromotion(!superposePromo);
		}
	}

}
