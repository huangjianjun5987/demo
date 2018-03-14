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
public class CompaniesRule implements PromotionRuleValidator {
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
		String branchCompanyId = (String) contentMap.get("branchCompanyId");

		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel =  iterator.next();
			if (!validateBranchCompany(pricingModel, branchCompanyId)) {
				iterator.remove();
			}
		}

	}

	/**
	 * 校验分公司
	 * 
	 * @param pricingModel
	 * @param branchCompanyId
	 * @return
	 */
	public boolean validateBranchCompany(PricingModel pricingModel, String branchCompanyId) {

		List<String> branchCompanies = pricingModel.getCompanies();
		if (branchCompanies != null && !branchCompanies.isEmpty()) {

			if (branchCompanyId == null) {
				logger.info("branchCompanyId is null!");
				return false;
			}
			if (!branchCompanies.contains(branchCompanyId)) {
				logger.info("promotion {} not support branchCompanyId {}!", pricingModel.getId(), branchCompanyId);
				return false;
			}
		}
		return true;
	}
}
