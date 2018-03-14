package com.yatang.sc.purchase.calculator.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.order.domain.FranchiseeLevelDiscountCfg;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.FranchiseeLevelDiscountCfgService;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.purchase.calculator.OrderDiscountPriceCalculator;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.xc.mbd.biz.org.dto.FranchinessMemberPointsDto;
import com.yatang.xc.mbd.biz.org.dubboservice.FranchinessMemberPointsDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 9/18/2017.
 */
@Service("userOrderDiscountPriceCalculator")
public class UserOrderDiscountPriceCalculatorImpl implements OrderDiscountPriceCalculator {

    protected Logger logger = LoggerFactory.getLogger(UserOrderDiscountPriceCalculatorImpl.class);
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private FranchiseeLevelDiscountCfgService franchiseeLevelDiscountCfgService;

    @Autowired
    private FranchinessMemberPointsDubboService franchinessMemberPointsDubboService;


    @Override
    public void price(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap) {
        logger.info("会员 {} 折扣计算开始。",order.getProfileId());

        if(!userDiscountAvailable(priceInfo, order, contentMap)){
            logger.info("促销不支持与会员折扣叠加，直接返回。");
            return;
        }
        String franchiseeId = (String)contentMap.get("franchiseeId");
        Response<FranchinessMemberPointsDto> franchinessMemberPointsDtoResponse =franchinessMemberPointsDubboService.queryFranchinessMemberPointsByFranchinessId(franchiseeId);
        if (!franchinessMemberPointsDtoResponse.isSuccess() || franchinessMemberPointsDtoResponse.getResultObject() == null) {
            logger.info("主数据加盟商会员服务调用失败，直接返回:{}", franchinessMemberPointsDtoResponse.getErrorMessage());
            return;
        }
        logger.info("会员 {} 等级id为{},会员称号{},会员折扣{}。",order.getProfileId(),franchinessMemberPointsDtoResponse.getResultObject().getGradeId(),franchinessMemberPointsDtoResponse.getResultObject().getGradeName(),franchinessMemberPointsDtoResponse.getResultObject().getDiscount());
        DecimalFormat df = new DecimalFormat("#.##");
        //会员折扣
        BigDecimal discount = franchinessMemberPointsDtoResponse.getResultObject().getDiscount();

        //折扣前金额
        BigDecimal oldAmount = new BigDecimal(df.format(priceInfo.getAmount()));
        //会员折扣金额
        BigDecimal userDiscountAmount =  new BigDecimal(df.format(oldAmount.multiply(discount).divide(new BigDecimal(100))));
        //折扣后金额
        BigDecimal amount  = new BigDecimal(df.format(oldAmount.subtract(userDiscountAmount)));
        logger.info("会员 {} 折扣前金额:{},折扣:{},折扣金额:{},折扣后金额:{}",order.getProfileId(),oldAmount,discount+"%",userDiscountAmount,amount);

        priceInfo.setAmount(amount.doubleValue());
        priceInfo.setUserDiscountAmount(userDiscountAmount.doubleValue());
        //折扣记录
        PriceAdjustmentDto priceAdjustmentDto = new PriceAdjustmentDto();
        priceAdjustmentDto.setAdjustmentDescription("会员折扣"+discount+"%");
        priceAdjustmentDto.setAdjustment(userDiscountAmount.doubleValue());
        priceAdjustmentDto.setQuantityAdjusted(1);
        priceAdjustmentDto.setTotalAdjustment(userDiscountAmount.doubleValue());
        priceInfo.getAdjustments().add(priceAdjustmentDto);
        logger.info("会员 {} 折扣计算结束。",order.getProfileId());
    }



    public boolean userDiscountAvailable(OrderPriceInfoDto priceInfo, OrderDto order, Map contentMap){
        List<PriceAdjustmentDto> adjustments = priceInfo.getAdjustments();
        if(CollectionUtils.isEmpty(adjustments)){
            return true;
        }
        for(PriceAdjustmentDto adjustment : adjustments){
            if(!StringUtils.isEmpty(adjustment.getPricingModel())){
                PromotionPo prompo = promotionService.queryById(adjustment.getPricingModel());
                if(prompo != null && prompo.getIsSuperposeUserDiscount() != null &&prompo.getIsSuperposeUserDiscount() == 0){
                    return false;
                }
            }
        }
        return true;

    }


    @Override
	public void price(OrderDto order, List<PricingModel> pricingModel, Map contentMap) {
		// TODO Auto-generated method stub
		
	}

}


