package com.yatang.sc.purchase.service;

import com.yatang.sc.order.domain.AvailableCouponActivityPo;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;

import java.util.List;
import java.util.Map;

/**
 * Created by qiugang on 7/10/2017.
 */
public interface PricingEngineService {

    public void pricingOrderTotal(OrderDto order,  Map contentMap,boolean isMock)  throws PricingException;

    public void pricingOrderAmount(OrderDto order,  Map contentMap,boolean isMock)  throws PricingException ;

    public List<AvailableCouponActivityPo> getAvailableCouponActivities(String storeId);

    void applyCoupons(OrderPriceInfoDto orderPriceInfo, OrderDto order, Map contentMap);

    void applyPricingModels(OrderDto order, Map contentMap) throws PricingException;

    public PricingModel convertPromotionToPricingModel(PromotionPo po);
}
