/**
 * 
 */
package com.yatang.sc.purchase.rule.qualifier.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.qualifier.PriorityQualifier;
import com.yatang.sc.purchase.rule.validator.RuleExecutor;

/**
 * @author dengdongshan
 *
 */
@Service
public class PriorityQualifierImpl implements PriorityQualifier {
	@Autowired
	private RuleExecutor ruleExecutor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yatang.sc.purchase.qualifier.service.PriorityQualifierService#
	 * getMaxPriorityPricingModels(com.yatang.sc.purchase.dto.OrderPriceInfoDto,
	 * com.yatang.sc.purchase.dto.OrderDto, java.util.List, java.util.Map)
	 */
	@Override
	public PricingModel getMaxPriorityPricingModels(OrderDto order, List<PricingModel> pricingModels, Map contentMap)
			throws PricingException {
		if (pricingModels == null) {
			return null;
		}
		if (pricingModels.isEmpty()) {
			return null;
		}
		Collections.sort(pricingModels, new Comparator<PricingModel>() {
			@Override
			public int compare(PricingModel o1, PricingModel o2) {
				// 根据优先级排序
				return (int) (o1.getPriority()-o2.getPriority());
			}

		});
		ruleExecutor.excute(order, pricingModels, contentMap);
		if(pricingModels.isEmpty()){
			return null;
		}
		return pricingModels.get(0);
	}

	@Override
	public List<PricingModel> getPricingModels(OrderDto order, List<PricingModel> pricingModels, Map contentMap)
			throws PricingException {
		List<PricingModel> returnPMs = new ArrayList<>();
		PricingModel maxPriorityPricingModels = getMaxPriorityPricingModels(order, pricingModels, contentMap);
		if(maxPriorityPricingModels==null){
			return returnPMs;
		}
		returnPMs.add(maxPriorityPricingModels);
		return returnPMs;
	}

}
