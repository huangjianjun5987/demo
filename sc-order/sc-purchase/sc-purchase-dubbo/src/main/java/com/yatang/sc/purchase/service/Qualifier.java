package com.yatang.sc.purchase.service;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.order.PricingModel;

import java.util.Map;

/**
 * 用来校验订单是否满足优惠券或者促销
 * Created by qiugang on 9/19/2017.
 */
public interface Qualifier {

    public Map qualifyOrder(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap);

    public Map qualifyCoupon(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap);

}
