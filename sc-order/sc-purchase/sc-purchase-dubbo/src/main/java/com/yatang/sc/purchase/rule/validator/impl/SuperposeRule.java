/**
 * 
 */
package com.yatang.sc.purchase.rule.validator.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.order.states.PromotionStates;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.validator.PromotionRuleValidator;

/**
 * @author dengdongshan
 *
 */
@Service
public class SuperposeRule implements PromotionRuleValidator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PromotionService promotionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yatang.sc.purchase.qualifier.rule.service.IPromotionRule#apply(com.
	 * yatang.sc.purchase.dto.OrderPriceInfoDto,
	 * com.yatang.sc.purchase.dto.OrderDto, java.util.List, java.util.Map)
	 */
	@Override
	public void validation(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel = iterator.next();
			if(!validatePricingModel(order, pricingModel)){
				logger.info("pricingModel {} skipped!", JSON.toJSONString(pricingModel));
				iterator.remove();
	        }
		}

	}

	public boolean validatePricingModel(OrderDto order, PricingModel pricingModel) {

		if (!(PromotionStates.RELEASED.equals(pricingModel.getStatus()))) {
			logger.info("pricingModel status is {}", pricingModel.getStatus());
			return false;
		}
		Date curentDate = new Date(System.currentTimeMillis());
		if (curentDate.before(pricingModel.getStartDate())) {
			logger.info("pricingModel startDate is {}", pricingModel.getStartDate());
			return false;
		}
		if (curentDate.after(pricingModel.getEndDate())) {
			logger.info("pricingModel endDate is {}", pricingModel.getStartDate());
			return false;
		}

		boolean result = true;
		List<PriceAdjustmentDto> adjustmentDtos = order.getPriceInfoDto().getAdjustments();
		if (CollectionUtils.isEmpty(adjustmentDtos)) {
			return result;
		}
		for (PriceAdjustmentDto adjustmentDto : adjustmentDtos) {
			if (!StringUtils.isEmpty(adjustmentDto.getPricingModel())) {
				if (adjustmentDto.getPricingModelType() == null
						&& adjustmentDto.getPricingModelType().equals(pricingModel.getType())) {
					result = false;
					break;
				}
				PromotionPo prompo = promotionService.queryById(adjustmentDto.getPricingModel());
				if (prompo != null) {
					Integer promotionSuperpose = prompo.getIsSuperposeProOrCouDiscount();
					if (promotionSuperpose == null || promotionSuperpose == 0) {
						result = false;
						break;
					}
				}
				if (pricingModel.getSuperposePromo() == null || !pricingModel.getSuperposePromo()) {
					result = false;
					break;
				}
			}
		}

		return result;
	}
}
