package com.yatang.sc.purchase.calculator;

import java.util.List;
import java.util.Map;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;

/**
 * Created by qiugang on 9/4/2017.
 */
public interface OrderDiscountPriceCalculator {

    public void price(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap);
    
    public void price(OrderDto order, List<PricingModel> pricingModel, Map contentMap) throws PricingException;

}
