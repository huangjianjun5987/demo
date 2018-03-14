/**
 * 
 */
package com.yatang.sc.purchase.rule.action;

import java.util.List;
import java.util.Map;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;

/**
 * @author dengdongshan
 *
 */
public interface ActionExecutor {
	public void excute(OrderDto order,List<PricingModel> pricingModels, Map contentMap);
}
