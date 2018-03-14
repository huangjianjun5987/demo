/**
 * 
 */
package com.yatang.sc.purchase.rule.action.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.promotion.dto.OrderRuleDto;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.dubboservice.util.PricingUtil;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.action.PromotionAction;

/**
 * 
 * 不限制情况下优惠执行类
 * @author dengdongshan
 *
 */
@Service
public class OrderRuleAction implements PromotionAction {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see com.yatang.sc.purchase.action.service.PromotionAction#apply(com.yatang.sc.purchase.dto.OrderPriceInfoDto, com.yatang.sc.purchase.dto.OrderDto, java.util.List, java.util.Map)
	 */
	@Override
	public void apply(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel = iterator.next();
			PromotionRuleDto promotionRule = pricingModel.getPromotionRule();
			if(promotionRule.isUseConditionRule()){
				continue;
			}
			
			OrderRuleDto orderRule = promotionRule.getOrderRule();
			String preferentialWay = orderRule.getPreferentialWay();
			BigDecimal preferentialValue = orderRule.getPreferentialValue();
			if(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName().equals(preferentialWay)){
				applyDiscount(preferentialValue.doubleValue(), order, pricingModel, contentMap);
			}
			if(OrderRuleDto.PreferentialWay.PERCENTAGE.getName().equals(preferentialWay)){
				applyDiscount(order.getPriceInfoDto().getAmount()*preferentialValue.doubleValue()/100, order, pricingModel, contentMap);
			}
		}

	}

   public void applyDiscount(double discountAmount, OrderDto order, PricingModel pricingModel, Map contentMap){
        addAdjustment(discountAmount, order, pricingModel, contentMap);
        OrderPriceInfoDto priceInfo = order.getPriceInfoDto();
        double oldAmount = priceInfo.getAmount();
        priceInfo.setDiscountAmount(PricingUtil.roundPrice(priceInfo.getDiscountAmount() + discountAmount));
        priceInfo.setAmount(PricingUtil.roundPrice(oldAmount - discountAmount));
        //分摊打折金额到商品项上
        List<CommerceItemDto> items = order.getItems();
        for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
			CommerceItemDto commerceItem = iterator.next();
			if(!commerceItem.isSelected()){
				continue;
			}
			double orderDiscountShare = commerceItem.getItemPrice().getOrderDiscountShare();
			double amount = commerceItem.getItemPrice().getAmount();
			double itemShareAmount = amount/oldAmount*discountAmount;
			commerceItem.getItemPrice().setOrderDiscountShare(orderDiscountShare+itemShareAmount);
		}
    }

	private void addAdjustment(double discountAmount, OrderDto order, PricingModel pricingModel, Map contentMap) {
		String storeId = (String) contentMap.get("storeId");
		String branchCompanyId = (String) contentMap.get("branchCompanyId");
		PriceAdjustmentDto adjustmentDto = new PriceAdjustmentDto();
		adjustmentDto.setAdjustmentDescription(pricingModel.getPromotionName());
		adjustmentDto.setPricingModelType(pricingModel.getType());
		adjustmentDto.setPricingModel(pricingModel.getId());
		adjustmentDto.setAdjustment(discountAmount);
		adjustmentDto.setTotalAdjustment(discountAmount);
		OrderPriceInfoDto priceInfo = order.getPriceInfoDto();
		priceInfo.getAdjustments().add(adjustmentDto);
		logger.info("pricingModel {} promotion  discountAmount {} to order whose amount {} and branchCompanyId {}, storeId {}!",pricingModel.getId(),discountAmount,priceInfo.getAmount(), branchCompanyId,storeId);
	}
   
	
}
