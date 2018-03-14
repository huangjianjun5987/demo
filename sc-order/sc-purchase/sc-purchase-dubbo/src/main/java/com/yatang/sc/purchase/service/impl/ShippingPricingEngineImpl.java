package com.yatang.sc.purchase.service.impl;

import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.dto.ShippingGroupDto;
import com.yatang.sc.purchase.dto.ShippingPriceInfoDto;
import com.yatang.sc.purchase.service.ShippingPricingEngine;
import org.springframework.stereotype.Service;

/**
 * Created by qiugang on 7/10/2017.
 */
@Service("shippingPricingEngine")
public class ShippingPricingEngineImpl implements ShippingPricingEngine{
    @Override
    public void pricingShippingGroup(OrderDto order) {

        ShippingGroupDto shippingGroup =  order.getShippingGroupDto();
        ShippingPriceInfoDto shippingPriceInfo = new ShippingPriceInfoDto();
        shippingPriceInfo.setAmount(0.0);
        PriceAdjustmentDto adjustmentDto = createShippingPriceAdjment("基本运费", 0.0);
        shippingPriceInfo.getAdjustments().add(adjustmentDto);
        shippingGroup.setShippingPriceInfoDto(shippingPriceInfo);
    }

    public static PriceAdjustmentDto createShippingPriceAdjment(String description, double amount){
        PriceAdjustmentDto adjustmentDto = new PriceAdjustmentDto();
        adjustmentDto.setAdjustment(amount);
        adjustmentDto.setTotalAdjustment(amount);
        adjustmentDto.setAdjustmentDescription(description);
        return adjustmentDto;
    }


}
