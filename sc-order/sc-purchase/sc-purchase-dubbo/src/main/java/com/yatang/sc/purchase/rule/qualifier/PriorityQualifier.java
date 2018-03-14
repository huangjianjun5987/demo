package com.yatang.sc.purchase.rule.qualifier;

import java.util.List;
import java.util.Map;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;

/**
 * 
 * @author dengdongshan
 * 
 */
public  interface PriorityQualifier {

    public PricingModel getMaxPriorityPricingModels(OrderDto order, List<PricingModel> pricingModels, Map contentMap)  throws PricingException;
    
    public List<PricingModel> getPricingModels(OrderDto order, List<PricingModel> pricingModels, Map contentMap)  throws PricingException;

}
