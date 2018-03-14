package com.yatang.sc.purchase.calculator.impl;

import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.dubboservice.util.PricingUtil;
import com.yatang.sc.purchase.order.PricingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by qiugang on 9/19/2017.
 */
@Service("couponDiscountCalculator")
public class CouponDiscountCalculatorImpl extends OrderDiscountCalculatorImpl {


    private Logger logger = LoggerFactory.getLogger(CouponDiscountCalculatorImpl.class);


    @Override
    public void applyDiscount(double discountAmount, OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap){

        PriceAdjustmentDto adjustmentDto = new PriceAdjustmentDto();
        adjustmentDto.setAdjustmentDescription(pricingModel.getPromotionName());
        adjustmentDto.setPricingModelType(pricingModel.getType());
        adjustmentDto.setPricingModel(pricingModel.getId());
        adjustmentDto.setAdjustment(discountAmount);
        adjustmentDto.setTotalAdjustment(discountAmount);
        priceInfo.getAdjustments().add(adjustmentDto);
        double oldAmount = priceInfo.getAmount();
        double totalCouponDiscount = priceInfo.getCouponDiscountAmount();
        if(discountAmount > oldAmount){
            priceInfo.setAmount(0.0);
            totalCouponDiscount = totalCouponDiscount + oldAmount;
        }else{
            priceInfo.setAmount(PricingUtil.roundPrice(oldAmount - discountAmount));
            totalCouponDiscount= totalCouponDiscount + discountAmount;
        }
        priceInfo.setCouponDiscountAmount(PricingUtil.roundPrice(totalCouponDiscount));
        logger.info("CouponDiscountCalculatorImpl {} apply coupon activityId:{}", order.getProfileId(), (String)contentMap.get("activityId"));
        order.getCouponActivities().add((String)contentMap.get("activityId"));
    }


}
