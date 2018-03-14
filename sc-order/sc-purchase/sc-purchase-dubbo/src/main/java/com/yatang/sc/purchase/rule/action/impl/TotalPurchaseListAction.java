/**
 * 
 */
package com.yatang.sc.purchase.rule.action.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.promotion.dto.OrderRuleDto;
import com.yatang.sc.promotion.dto.RuleConditionsDto;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.dubboservice.util.PricingUtil;
import com.yatang.sc.purchase.order.PricingModel;

/**
 * 整个购买列表
 * 
 * @author dengdongshan
 *
 */
@Service
public class TotalPurchaseListAction extends PurchaseConditionsAction {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yatang.sc.purchase.action.service.PromotionAction#apply(com.yatang.sc
	 * .purchase.dto.OrderPriceInfoDto, com.yatang.sc.purchase.dto.OrderDto,
	 * java.util.List, java.util.Map)
	 */
	@Override
	public void apply(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		for (Iterator<PricingModel> pricingModelIterators = pricingModels.iterator(); pricingModelIterators
				.hasNext();) {
			PricingModel pricingModel = pricingModelIterators.next();
			PromotionRuleDto promotionRule = pricingModel.getPromotionRule();
			//判断是否是整个购买列表的促销
			if (!promotionRule.isUseConditionRule()) {
				continue;
			}
			if (!PromotionRuleDto.RuleName.TOTALPUCHASELIST.getName().equals(promotionRule.getRuleName())) {
				continue;
			}
			if (promotionRule.getTotalPurchaseListRule() == null) {
				continue;
			}
			if(promotionRule.getTotalPurchaseListRule().getRule()==null||promotionRule.getTotalPurchaseListRule().getRule().getPreferentialValue()==null){
	    		logger.error("promotion id {} data error",pricingModel.getId());
	    		return;
	    	}

			List<RuleConditionsDto> conditions = promotionRule.getTotalPurchaseListRule().getConditions();
			OrderRuleDto rule = promotionRule.getTotalPurchaseListRule().getRule();
			if (conditions == null || conditions.isEmpty()) {
				continue;
			}
			
			List<CommerceItemDto> items = order.getItems();
			List<CommerceItemDto> applyItems = getApplyItems(conditions, items,contentMap);
			for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
				CommerceItemDto item = iterator.next();
				if (!isContainItem(applyItems, item.getProductId())) {
					continue;
				}
				OrderPriceInfoDto priceInfoDto = order.getPriceInfoDto();
				if (OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName().equals(rule.getPreferentialWay())) {
					double discountAmount = rule.getPreferentialValue().doubleValue();
					priceInfoDto.setDiscountAmount(
							PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
					priceInfoDto.setAmount(priceInfoDto.getAmount() - discountAmount);
					double qualifiedAmount = item.getItemPrice().getAmount();
					discountShareToRelevantProduct(order, qualifiedAmount, discountAmount, item.getProductId());
					addAdjustment(discountAmount, order, pricingModel, contentMap);
				}
				if (OrderRuleDto.PreferentialWay.PERCENTAGE.getName().equals(rule.getPreferentialWay())) {
					double qualifiedAmount = item.getItemPrice().getAmount();
					double discountAmount = qualifiedAmount * rule.getPreferentialValue().doubleValue() / 100;
					priceInfoDto.setDiscountAmount(
							PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
					priceInfoDto.setAmount(priceInfoDto.getAmount() - discountAmount);
					discountShareToRelevantProduct(order, qualifiedAmount, discountAmount, item.getProductId());
					addAdjustment(discountAmount, order, pricingModel, contentMap);
				}
			}
		}

	}

	private List<CommerceItemDto> getApplyItems(List<RuleConditionsDto> conditions, List<CommerceItemDto> items,Map contentMap) {
		List<CommerceItemDto> applyItems = new ArrayList<>();
		for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
			CommerceItemDto item = iterator.next();
			if (!item.isSelected()) {
				continue;
			}
			for (Iterator<RuleConditionsDto> ruleConditionIterators = conditions.iterator(); ruleConditionIterators
					.hasNext();) {
				RuleConditionsDto ruleCondition = ruleConditionIterators.next();
				String purchaseType = ruleCondition.getPurchaseType();
				if (RuleConditionsDto.PurchaseType.CATEGORY.getName().equals(purchaseType)) {
					boolean validateCategory = validateCategory(ruleCondition, item.getProductId(),contentMap);
					if (validateCategory && !isContainItem(applyItems, item.getProductId())) {
						applyItems.add(item);
					}
				}
				if (RuleConditionsDto.PurchaseType.PRODUCT.getName().equals(purchaseType)) {
					String productId = ruleCondition.getPromoProduct().getProductId();
					if (item.getProductId().equals(productId) && !isContainItem(applyItems, item.getProductId())&&!isProtectPrice(contentMap,productId)) {
						applyItems.add(item);
					}
				}
				if (RuleConditionsDto.PurchaseType.ALL.getName().equals(purchaseType)) {
					if (!isContainItem(applyItems, item.getProductId())&&!isProtectPrice(contentMap,item.getProductId())) {
						applyItems.add(item);
					}
				}
			}
		}
		return applyItems;
	}

	private boolean isContainItem(List<CommerceItemDto> applyItems, String productId) {
		for (Iterator<CommerceItemDto> iterator = applyItems.iterator(); iterator.hasNext();) {
			CommerceItemDto commerceItemDto = iterator.next();
			if (commerceItemDto.getProductId().equals(productId)) {
				return true;
			}
		}
		return false;
	}

	private void discountShareToRelevantProduct(OrderDto order, double qualifiedAmount, double discountAmount,
			String productId) {
		// 分摊打折金额到商品项上
		List<CommerceItemDto> items = order.getItems();
		for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {

			CommerceItemDto commerceItemDto = iterator.next();
			if (!commerceItemDto.isSelected()) {
				continue;
			}
			if (productId == null) {
				continue;
			}
			if (!productId.equals(commerceItemDto.getProductId())) {
				continue;
			}
			double orderDiscountShare = commerceItemDto.getItemPrice().getOrderDiscountShare();
			double amount = commerceItemDto.getItemPrice().getAmount();
			double itemShareAmount = amount / qualifiedAmount * discountAmount;
			commerceItemDto.getItemPrice().setOrderDiscountShare(orderDiscountShare + itemShareAmount);
		}
	}

//	private void addAdjustment(double discountAmount, OrderDto order, PricingModel pricingModel) {
//		OrderPriceInfoDto priceInfo = order.getPriceInfoDto();
//		List<PriceAdjustmentDto> adjustments = priceInfo.getAdjustments();
//		if(adjustments.isEmpty()){
//			createNewAdjustment(discountAmount, pricingModel, priceInfo);
//			return;
//		}
//		boolean isExisting=false;
//		for (Iterator<PriceAdjustmentDto> iterator = adjustments.iterator(); iterator.hasNext();) {
//			PriceAdjustmentDto priceAdjustmentDto = iterator.next();
//			String pricingModelId = priceAdjustmentDto.getPricingModel();
//			if(pricingModel.getId().equals(pricingModelId)){
//				priceAdjustmentDto.setAdjustment(priceAdjustmentDto.getAdjustment()+discountAmount);
//				isExisting=true;
//				logger.info("pricingModel {} promotion  discountAmount {} to order whose franchiseeId{}, franchiseeStoreId  {}!",pricingModel.getId(),discountAmount, order.getFranchiseeId(),order.getFranchiseeStoreId());
//			}
//		}
//		
//		if(!isExisting){
//			createNewAdjustment(discountAmount, pricingModel, priceInfo);
//			logger.info("pricingModel {} promotion  discountAmount {} to order whose franchiseeId{}, franchiseeStoreId  {}!",pricingModel.getId(),discountAmount, order.getFranchiseeId(),order.getFranchiseeStoreId());
//		}
//	}
//	private void createNewAdjustment(double discountAmount, PricingModel pricingModel, OrderPriceInfoDto priceInfo) {
//		PriceAdjustmentDto adjustmentDto = new PriceAdjustmentDto();
//		adjustmentDto.setAdjustmentDescription(pricingModel.getPromotionName());
//		adjustmentDto.setPricingModelType(pricingModel.getType());
//		adjustmentDto.setPricingModel(pricingModel.getId());
//		adjustmentDto.setAdjustment(discountAmount);
//		adjustmentDto.setTotalAdjustment(discountAmount);
//		priceInfo.getAdjustments().add(adjustmentDto);
//	}
}
