/**
 * 
 */
package com.yatang.sc.purchase.rule.validator.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.yatang.sc.dto.PromoCategoriesDto;
import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.promotion.dto.RuleConditionsDto;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.validator.PromotionRuleValidator;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductSCDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;

/**
 * 
 * 购买条件规则
 * 
 * 对配置商品进行促销应用
 * @author dengdongshan
 *
 */
@Service
public class PurchaseConditionsRule implements PromotionRuleValidator {
    private Logger logger = LoggerFactory.getLogger(PurchaseConditionsRule.class);
    @Autowired
    private ProductSCDubboService productSCDubboService;// 商品查询dubboservice（供应链）

	/* (non-Javadoc)
	 * @see com.yatang.sc.purchase.qualifier.rule.service.IPromotionRule#apply(com.yatang.sc.purchase.dto.OrderPriceInfoDto, com.yatang.sc.purchase.dto.OrderDto, java.util.List, java.util.Map)
	 */
	@Override
	public void validation(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		String storeId = (String) contentMap.get("storeId");
		String branchCompanyId = (String) contentMap.get("branchCompanyId");
		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel = iterator.next();
			PromotionRuleDto promotionRule = pricingModel.getPromotionRule();
			if(!promotionRule.isUseConditionRule()){
				continue;
			};
			if(!PromotionRuleDto.RuleName.PURCHASECONDITION.getName().equals(promotionRule.getRuleName())){
				continue;
			}
			if(promotionRule.getPurchaseConditionsRule()==null||promotionRule.getPurchaseConditionsRule().getCondition()==null){
				logger.warn("promotion {} rule data error",pricingModel.getId());
				iterator.remove();
				continue;
			}
			if (!isPass(order, promotionRule.getPurchaseConditionsRule().getCondition(), contentMap)) {
				iterator.remove();
				logger.info("promotion {} rule not pass so removed, branchCompanyId {}, storeId{}.",pricingModel.getId(),branchCompanyId,storeId);
				continue;
			}
		}

	}
	protected boolean isPass(OrderDto order, RuleConditionsDto condition, Map contentMap){
		
		String purchaseType = condition.getPurchaseType();
		if(RuleConditionsDto.PurchaseType.CATEGORY.getName().equals(purchaseType)){
			if(validateCategory(order, condition)){
				return true;
			}
		}
		if(RuleConditionsDto.PurchaseType.PRODUCT.getName().equals(purchaseType)){
	        if(validateProduct(order, condition)){
				return true;
			}
		}
		if(RuleConditionsDto.PurchaseType.ALL.getName().equals(purchaseType)){
			if(validateALL(order, condition)){
				return true;
			}
		}
		return false;
	}
	
	private boolean validateALL(OrderDto order, RuleConditionsDto condition) {
		double qualifiedAmount=0.0d;
		long qty=0L;
		List<CommerceItemDto> commerceItems = order.getItems();
		for(CommerceItemDto item : commerceItems) {
			if(!item.isSelected()){
				continue;
			}
			qualifiedAmount += item.getItemPrice().getAmount();
            qty=qty+item.getSaleQuantity();
		}
	    if(qualifiedAmount >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.AMOUNT.getName().equals(condition.getConditionType())){
            return true;
        }
        if(qty >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.QUANTITY.getName().equals(condition.getConditionType())){
            return true;
        }
		return false;
	}
	
	private boolean validateProduct(OrderDto order, RuleConditionsDto condition) {
		double qualifiedAmount=0.0d;
		long qty=0L;
		List<CommerceItemDto> commerceItems = order.getItems();
		boolean hasProduct=false;
		for(CommerceItemDto item : commerceItems) {
			if(!item.isSelected()){
				continue;
			}
			String productId2 = condition.getPromoProduct().getProductId();
			String productId = item.getProductId();
			
			if(productId!=null&&productId.equals(productId2)){
                qualifiedAmount += item.getItemPrice().getAmount();
                qty=qty+item.getSaleQuantity();
                hasProduct=true;
			}
		}
		if(!hasProduct){
			return false;
		}
	    if(qualifiedAmount >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.AMOUNT.getName().equals(condition.getConditionType())){
            return true;
        }
        if(qty >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.QUANTITY.getName().equals(condition.getConditionType())){
            return true;
        }
		return false;
	}
	
	private boolean validateCategory(OrderDto order, RuleConditionsDto condition) {
		//按照分类计算金额
		double qualifiedAmount=0.0d;
		long qty=0L;
        List<CommerceItemDto> commerceItems = order.getItems();
        boolean hasCategory=false;
        for(CommerceItemDto item : commerceItems) {
            if(item.isSelected() && validateCategory(condition, item.getProductId())){
                qualifiedAmount += item.getItemPrice().getAmount();
                qty=qty+item.getSaleQuantity();
                hasCategory=true;
            }
        }
        if(!hasCategory){
			return false;
		}
        if(qualifiedAmount >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.AMOUNT.getName().equals(condition.getConditionType())){
            return true;
        }
        if(qty >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.QUANTITY.getName().equals(condition.getConditionType())){
            return true;
        }
        return false;
	}
	
	/**
     * 校验分类
     * @param pricingModel
     * @param productId
     * @return
     */
	private boolean validateCategory(RuleConditionsDto condition, String productId){
        Response<ProductDto> productRes = productSCDubboService.queryByProductId(productId);
        if(productRes == null || !productRes.isSuccess() || productRes.getResultObject() == null){
        	logger.warn("can't find product by id {}", productId);
        	return false;
        }
        ProductDto productDto  = productRes.getResultObject();
        
        PromoCategoriesDto promoCategories = condition.getPromoCategories();
		Integer categoryLevel = promoCategories.getCategoryLevel();
        
        String productCategory="";
        if(categoryLevel==1){
        	productCategory=productDto.getFirstLevelCategoryId();
        }
        if(categoryLevel==2){
        	productCategory=productDto.getSecondLevelCategoryId();
        }
        if(categoryLevel==3){
        	productCategory=productDto.getThirdLevelCategoryId();
        }
        if(categoryLevel==4){
        	productCategory=productDto.getFourthCategoryId();
        }
        String promoCategory = promoCategories.getCategoryId();
        List<String> categories = new ArrayList<String>();
        categories.add(productCategory);
        
        if(!isContains(categories, promoCategory)){
        	logger.info("promotion not support productId {}!", productId);
            return false;
        }
        return true;
    }
    

	private boolean isContains(List<String> collection, String value){
        if(value == null){
            return false;
        }
        if(collection.contains(value)){
            return true;
        }
        return false;
    }
}
