package com.yatang.sc.purchase.calculator.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.pricing.PromotionDiscountType;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.purchase.calculator.OrderDiscountPriceCalculator;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.dubboservice.util.PricingUtil;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.action.ActionExecutor;
import com.yatang.sc.purchase.rule.qualifier.PriorityQualifier;
import com.yatang.sc.purchase.service.Qualifier;

/**
 * Created by qiugang on 9/4/2017.
 */
@Service("orderDiscountPriceCalculator")
public class OrderDiscountCalculatorImpl implements OrderDiscountPriceCalculator {

    private Logger log = LoggerFactory.getLogger(OrderDiscountCalculatorImpl.class);

    @Autowired
    Qualifier qualifier;
    @Autowired
    private PromotionService promotionService;

    @Autowired
    PriorityQualifier priorityQualifier;
    
    @Autowired
    ActionExecutor actionExecutor;
    
    @Override
    public void price(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap) {

        if(pricingModel == null){
            log.warn("pricingModel is null!");
            return;
        }
        Map qualifyResult = qualifier.qualifyOrder(priceInfo, order, pricingModel, contentMap);
        Boolean result = (Boolean)qualifyResult.get("qualifyResult");
        if(result){
            targetDiscount(qualifyResult, priceInfo, order, pricingModel, contentMap);
        }else{
            log.info("order don't qualify with promotion {}", pricingModel.getId());
        }

    }

    /**
     *
     * @param priceInfo
     * @param order
     * @param pricingModel
     * @return
     */
    public boolean targetable(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel){

        boolean result = true;
        List<PriceAdjustmentDto> adjustmentDtos = priceInfo.getAdjustments();
        if(CollectionUtils.isEmpty(adjustmentDtos)){
            return result;
        }
        for(PriceAdjustmentDto adjustmentDto : adjustmentDtos){
            if(!StringUtils.isEmpty(adjustmentDto.getPricingModel())){
                if(adjustmentDto.getPricingModelType() != null ){
                    if(adjustmentDto.getPricingModelType() != null &&
                            adjustmentDto.getPricingModelType().equals(pricingModel.getType())){
                        result = false;
                        break;
                    }
                    PromotionPo prompo = promotionService.queryById(adjustmentDto.getPricingModel());
                    if(prompo != null) {
                        Integer promotionSuperpose = prompo.getIsSuperposeProOrCouDiscount();
                        if (promotionSuperpose == null || promotionSuperpose == 0){
                            result = false;
                            break;
                        }
                    }
                    if(pricingModel.getSuperposePromo()== null || pricingModel.getSuperposePromo()==false){
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;

    }


    public void targetDiscount(Map qualifyResult, OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap){
        if(!targetable(priceInfo, order, pricingModel)){
            return;
        }
        double discountAmount =getDiscountAmount(qualifyResult, pricingModel);
        applyDiscount(discountAmount, priceInfo, order, pricingModel, contentMap);

    }


    public double getDiscountAmount(Map qualifyResult, PricingModel pricingModel) {
        String discountType = pricingModel.getDiscountType();
        double discountAmount = 0.0;
        Double qualifiedAmount = (Double)qualifyResult.get("qualifiedAmount");
        if(PromotionDiscountType.PERCENTAGE.equals(discountType)){
            discountAmount = qualifiedAmount * pricingModel.getDiscount() / 100;
        } else {
            discountAmount = pricingModel.getDiscount();
        }
        discountAmount = PricingUtil.roundPrice(discountAmount);
        return discountAmount;
    }



    public void applyDiscount(double discountAmount, OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap){

        PriceAdjustmentDto adjustmentDto = new PriceAdjustmentDto();
        adjustmentDto.setAdjustmentDescription(pricingModel.getPromotionName());
        adjustmentDto.setPricingModelType(pricingModel.getType());
        adjustmentDto.setPricingModel(pricingModel.getId());
        adjustmentDto.setAdjustment(discountAmount);
        adjustmentDto.setTotalAdjustment(discountAmount);
        priceInfo.getAdjustments().add(adjustmentDto);
        double oldAmount = priceInfo.getAmount();
        priceInfo.setDiscountAmount(PricingUtil.roundPrice(priceInfo.getDiscountAmount() + discountAmount));
        priceInfo.setAmount(PricingUtil.roundPrice(oldAmount - discountAmount));
    }

	@Override
	public void price(OrderDto order, List<PricingModel> pPricingModels, Map contentMap) throws PricingException {
		 List<PricingModel> oldPromotions=new ArrayList<>();
		 List<PricingModel> newPromotions=new ArrayList<>();
		 for (Iterator<PricingModel> iterator = pPricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel =  iterator.next();
			if(pricingModel.getPromotionRule()==null){
				oldPromotions.add(pricingModel);
			}
			if(pricingModel.getPromotionRule()!=null){
				newPromotions.add(pricingModel);
			}
		}
		boolean isOldPromotionApply=false;
		if(!oldPromotions.isEmpty()){
			double oldAmount = order.getPriceInfoDto().getAmount();
			for (Iterator<PricingModel> iterator = oldPromotions.iterator(); iterator.hasNext();) {
				PricingModel pricingModel =iterator.next();
				price(order.getPriceInfoDto(), order, pricingModel, contentMap);
				if(oldAmount!=order.getPriceInfoDto().getAmount()){
					isOldPromotionApply=true;
				};
			}
		}
		if(!newPromotions.isEmpty()&&!isOldPromotionApply){
			actionExecutor.excute(order, priorityQualifier.getPricingModels(order, newPromotions, contentMap), contentMap);
		}
		
	}


}
