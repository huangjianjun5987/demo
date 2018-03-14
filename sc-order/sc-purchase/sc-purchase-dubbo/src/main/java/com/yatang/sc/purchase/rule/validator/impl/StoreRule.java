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

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.validator.PromotionRuleValidator;

/**
 * @author dengdongshan
 *
 */
@Service
public class StoreRule implements PromotionRuleValidator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
		String storeId = (String) contentMap.get("storeId");

		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel =  iterator.next();
			if (!validateStore(pricingModel, storeId)) {
				iterator.remove();
			}
		}

	}

	/**
	 * 校验门店
	 * 
	 * @param pricingModel
	 * @param storeId
	 * @return
	 */
	public boolean validateStore(PricingModel pricingModel, String storeId) {
		List<String> stores = pricingModel.getStores();
		if (stores != null && !stores.isEmpty() ) {
			if (storeId == null) {
				logger.info("storeId is null!");
				return false;
			}
			if (!stores.contains(storeId)) {
				logger.info("promotion {} not support store {}!", pricingModel.getId(), storeId);
				return false;
			}
		}
		return true;
	}
}
