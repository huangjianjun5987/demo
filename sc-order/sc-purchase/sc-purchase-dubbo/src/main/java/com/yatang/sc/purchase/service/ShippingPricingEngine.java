package com.yatang.sc.purchase.service;

import com.yatang.sc.purchase.dto.OrderDto;

/**
 * Created by qiugang on 7/10/2017.
 */
public interface ShippingPricingEngine {

    public void pricingShippingGroup(OrderDto order);
}
