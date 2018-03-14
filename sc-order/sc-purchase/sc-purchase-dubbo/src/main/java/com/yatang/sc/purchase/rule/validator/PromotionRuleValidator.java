/**
 * 
 */
package com.yatang.sc.purchase.rule.validator;

import java.util.List;
import java.util.Map;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;

/**
 * @author dengdongshan
 *
 */
public interface PromotionRuleValidator {
	 /**
     * 对订单进行应用规则查看是否满足应用条件,不滿足過濾掉此促銷
     */
    public void validation(OrderDto order,  List<PricingModel> pricingModels, Map contentMap);

}
