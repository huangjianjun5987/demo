/**
 * 
 */
package com.yatang.sc.purchase.rule.action.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.dto.PromoCategoriesDto;
import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.promotion.dto.OrderRuleDto;
import com.yatang.sc.promotion.dto.PurchaseConditionsRuleDto;
import com.yatang.sc.promotion.dto.RuleConditionsDto;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.dubboservice.util.PricingUtil;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.rule.action.PromotionAction;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductSCDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;

/**
 * 购买条件规则执行类
 * @author dengdongshan
 *
 */
@Service
public class PurchaseConditionsAction implements PromotionAction {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    protected ProductSCDubboService productSCDubboService;// 商品查询dubboservice（供应链）
    
	@Autowired
	protected GoodsPriceQueryDubboService goodsPriceDubboService;

	/* (non-Javadoc)
	 * @see com.yatang.sc.purchase.action.service.PromotionAction#apply(com.yatang.sc.purchase.dto.OrderPriceInfoDto, com.yatang.sc.purchase.dto.OrderDto, java.util.List, java.util.Map)
	 */
	@Override
	public void apply(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		for (Iterator<PricingModel> iterator = pricingModels.iterator(); iterator.hasNext();) {
			PricingModel pricingModel = iterator.next();
			if(pricingModel.getPromotionRule()==null||!pricingModel.getPromotionRule().isUseConditionRule()){
				continue;
			}
			if(pricingModel.getPromotionRule().getPurchaseConditionsRule()==null){
				continue;
			}
			if(!PromotionRuleDto.RuleName.PURCHASECONDITION.getName().equals(pricingModel.getPromotionRule().getRuleName())){
				continue;
			}
			PurchaseConditionsRuleDto purchaseConditionsRule = pricingModel.getPromotionRule().getPurchaseConditionsRule();
			
			if(purchaseConditionsRule.getRule()==null||purchaseConditionsRule.getRule().getPreferentialValue()==null){
	    		logger.error("promotion id {} data error",pricingModel.getId());
	    		return;
	    	}
			String purchaseType = purchaseConditionsRule.getCondition().getPurchaseType();
			if(RuleConditionsDto.PurchaseType.CATEGORY.getName().equals(purchaseType)){
				applyCategory(order,purchaseConditionsRule,pricingModel,contentMap);
			}
			if(RuleConditionsDto.PurchaseType.PRODUCT.getName().equals(purchaseType)){
				applyProduct(order, purchaseConditionsRule,pricingModel,contentMap);
			}
			if(RuleConditionsDto.PurchaseType.ALL.getName().equals(purchaseType)){
				applyAll(order, purchaseConditionsRule,pricingModel,contentMap);
			}
		}

	}
	protected void applyAll(OrderDto order, PurchaseConditionsRuleDto purchaseConditionsRule,PricingModel pricingModel,Map contentMap) {
		
        OrderPriceInfoDto priceInfoDto = order.getPriceInfoDto();
        if(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	double discountAmount = purchaseConditionsRule.getRule().getPreferentialValue().doubleValue();
        	double qualifiedAmount = getQualifiedAmount(order,contentMap);
        	priceInfoDto.setAmount(priceInfoDto.getAmount()-discountAmount);
        	priceInfoDto.setDiscountAmount(PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
        	discountShareToAllItems(order,qualifiedAmount,discountAmount,contentMap);
        	addAdjustment(discountAmount, order, pricingModel, contentMap);
        }
        if(OrderRuleDto.PreferentialWay.PERCENTAGE.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	double qualifiedAmount = getQualifiedAmount(order,contentMap);
        	double discountAmount = qualifiedAmount*purchaseConditionsRule.getRule().getPreferentialValue().doubleValue()/100;
        	priceInfoDto.setDiscountAmount(PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
			priceInfoDto.setAmount(priceInfoDto.getAmount()-discountAmount);
			discountShareToAllItems(order, qualifiedAmount,discountAmount,contentMap);
			addAdjustment(discountAmount, order, pricingModel, contentMap);
        }
	}
	
	private double getQualifiedAmount(OrderDto order,Map contentMap){
		List<CommerceItemDto> items = order.getItems();
		double sumAmount=0.0d;
		for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
			CommerceItemDto commerceItem =  iterator.next();
			String productId = commerceItem.getProductId();
			if(isProtectPrice(contentMap, productId)){
				continue;
			}
			sumAmount+=commerceItem.getItemPrice().getAmount();
		}
		return sumAmount;
	}
	
	protected void applyCategory(OrderDto order, PurchaseConditionsRuleDto purchaseConditionsRule,PricingModel pricingModel,Map contentMap) {
		//按照分类计算金额
        OrderPriceInfoDto priceInfoDto = order.getPriceInfoDto();
        if(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	double discountAmount = purchaseConditionsRule.getRule().getPreferentialValue().doubleValue();
        	priceInfoDto.setDiscountAmount(PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
        	priceInfoDto.setAmount(priceInfoDto.getAmount()-discountAmount);
        	double qualifiedAmount = getQulifiedAmount(order, purchaseConditionsRule,contentMap);
            discountShareToRelevantCategory(order,qualifiedAmount,discountAmount,purchaseConditionsRule,contentMap);
            addAdjustment(discountAmount, order, pricingModel, contentMap);
        }
        if(OrderRuleDto.PreferentialWay.PERCENTAGE.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	double qualifiedAmount = getQulifiedAmount(order, purchaseConditionsRule,contentMap);
        	double discountAmount = qualifiedAmount*purchaseConditionsRule.getRule().getPreferentialValue().doubleValue()/100;
        	priceInfoDto.setDiscountAmount(PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
			priceInfoDto.setAmount(priceInfoDto.getAmount()-discountAmount);
            discountShareToRelevantCategory(order, qualifiedAmount,discountAmount,purchaseConditionsRule,contentMap);
            addAdjustment(discountAmount, order, pricingModel, contentMap);
        }

	}


	private void discountShareToRelevantCategory(OrderDto order,double qualifiedAmount,double discountAmount,PurchaseConditionsRuleDto purchaseConditionsRule,Map contentMap) {
		//分摊打折金额到商品项上
		List<CommerceItemDto> items = order.getItems();
		for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
			
			CommerceItemDto commerceItemDto = iterator.next();
			if(!commerceItemDto.isSelected() ){
				 continue;
			}
			if(!validateCategory(purchaseConditionsRule.getCondition(), commerceItemDto.getProductId(),contentMap)){
				continue;
			}
			double orderDiscountShare = commerceItemDto.getItemPrice().getOrderDiscountShare();
			double amount = commerceItemDto.getItemPrice().getAmount();
			double itemShareAmount = amount/qualifiedAmount*discountAmount;
			if(qualifiedAmount<=0||discountAmount<=0){
				continue;
			}
			commerceItemDto.getItemPrice().setOrderDiscountShare(orderDiscountShare+itemShareAmount);
		}
	}

	
	protected void applyProduct(OrderDto order, PurchaseConditionsRuleDto purchaseConditionsRule,PricingModel pricingModel,Map contentMap) {
		
        OrderPriceInfoDto priceInfoDto = order.getPriceInfoDto();
		if(OrderRuleDto.PreferentialWay.DISCOUNTAMOUNT.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	double discountAmount = purchaseConditionsRule.getRule().getPreferentialValue().doubleValue();
        	priceInfoDto.setDiscountAmount(PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
        	priceInfoDto.setAmount(priceInfoDto.getAmount()-discountAmount);
        	double qualifiedAmount = getRelevantProductQulifiedAmount(order, purchaseConditionsRule,contentMap);
        	discountShareToRelevantProduct(order, qualifiedAmount,discountAmount,purchaseConditionsRule,contentMap);
        	addAdjustment(discountAmount, order, pricingModel, contentMap);
        }
        if(OrderRuleDto.PreferentialWay.PERCENTAGE.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	double qualifiedAmount = getRelevantProductQulifiedAmount(order, purchaseConditionsRule,contentMap);
        	double discountAmount = qualifiedAmount*purchaseConditionsRule.getRule().getPreferentialValue().doubleValue()/100;
        	priceInfoDto.setDiscountAmount(PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
			priceInfoDto.setAmount(priceInfoDto.getAmount()-discountAmount);
			discountShareToRelevantProduct(order, qualifiedAmount,discountAmount,purchaseConditionsRule,contentMap);
			addAdjustment(discountAmount, order, pricingModel, contentMap);
        }
        if(OrderRuleDto.PreferentialWay.FIXEDPRICE.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	List<CommerceItemDto> commerceItems = order.getItems();
        	double discountAmount=0d;
        	double qualifiedAmount=0d;
        	//计算被应用的产品总金额和打折金额
            for(CommerceItemDto item : commerceItems) {
            	if(purchaseConditionsRule.getCondition().getPromoProduct()==null||purchaseConditionsRule.getCondition().getPromoProduct().getProductId()==null){
      				 continue;
   	   			}
   	   			String productId = purchaseConditionsRule.getCondition().getPromoProduct().getProductId();
   	   			if(!item.isSelected() ||!productId.equals(item.getProductId())){
   	   				continue;
   	   			}
   	   			if(isProtectPrice(contentMap, productId)){
   	   				continue;
   	   			}
                double promotionAmount=item.getSaleQuantity()*purchaseConditionsRule.getRule().getPreferentialValue().doubleValue();
                discountAmount=discountAmount+(item.getItemPrice().getAmount()-promotionAmount);
                qualifiedAmount=qualifiedAmount+item.getItemPrice().getAmount();
            }
        	priceInfoDto.setDiscountAmount(PricingUtil.roundPrice(priceInfoDto.getDiscountAmount() + discountAmount));
        	priceInfoDto.setAmount(priceInfoDto.getAmount()-discountAmount);
        	
        	for(CommerceItemDto item : commerceItems) {
            	if(purchaseConditionsRule.getCondition().getPromoProduct()==null||purchaseConditionsRule.getCondition().getPromoProduct().getProductId()==null){
      				 continue;
   	   			}
   	   			String productId = purchaseConditionsRule.getCondition().getPromoProduct().getProductId();
   	   			if(!item.isSelected() ||!productId.equals(item.getProductId())){
   	   				continue;
   	   			}
   	   			if(isProtectPrice(contentMap, productId)){
	   				continue;
	   			}
   	   			double orderDiscountShare = item.getItemPrice().getOrderDiscountShare();
   				double amount = item.getItemPrice().getAmount();
   				if(qualifiedAmount<=0||discountAmount<=0){
   					continue;
   				}
   				double itemShareAmount = amount/qualifiedAmount*discountAmount;
   	   			item.getItemPrice().setOrderDiscountShare(orderDiscountShare+itemShareAmount);
            }
        	addAdjustment(discountAmount, order, pricingModel, contentMap);
        }
		if (OrderRuleDto.PreferentialWay.GIVESAMEPRODUCT.getName().equals(purchaseConditionsRule.getRule().getPreferentialWay())){
        	List<CommerceItemDto> commerceItems = order.getItems();
        	List<CommerceItemDto> cwal=new ArrayList<CommerceItemDto>(commerceItems);
        	double discountAmount=0d;
        	for (Iterator<CommerceItemDto> iterator = cwal.iterator(); iterator.hasNext();) {
				CommerceItemDto item = iterator.next();
    			if(purchaseConditionsRule.getCondition().getPromoProduct()==null||purchaseConditionsRule.getCondition().getPromoProduct().getProductId()==null){
   				 continue;
	   			}
	   			String productId = purchaseConditionsRule.getCondition().getPromoProduct().getProductId();
   	   			if(!item.isSelected() ||!productId.equals(item.getProductId())){
	   				continue;
	   			}
                double freeAmount = item.getItemPrice().getSalePrice()*purchaseConditionsRule.getRule().getPreferentialValue().doubleValue();
				discountAmount=discountAmount+freeAmount;
   	   			double orderDiscountShare = item.getItemPrice().getOrderDiscountShare();
   	   			item.getItemPrice().setOrderDiscountShare(orderDiscountShare+freeAmount);

   	   			CommerceItemDto freeItem=BeanConvertUtils.convert(item, CommerceItemDto.class);
   	   			
   	   			freeItem.getItemPrice().setAdjustments(null);
   	   			freeItem.getItemPrice().setAmount(0d);
   	   			freeItem.setQuantity(purchaseConditionsRule.getRule().getPreferentialValue().longValue()*freeItem.getUnitQuantity());
   	   			freeItem.setSaleQuantity(purchaseConditionsRule.getRule().getPreferentialValue().longValue());
   	   			freeItem.setPriceModel(pricingModel.getId());
   	   			freeItem.setType(CommerceItemTypes.PROMOTION);
   	   			order.getItems().add(freeItem);
   	   			createNewAdjustment(discountAmount, pricingModel, order.getPriceInfoDto());
	   	   		String branchCompanyId = (String) contentMap.get("branchCompanyId");
	   			if(!StringUtils.isEmpty(branchCompanyId)){
	   				logger.info("promotion {} give free item {} to branchCompanyId {}",pricingModel.getId(),freeItem.getProductId(),branchCompanyId);
	   			}
		   		
            }
        }
	}
	
	
	private void discountShareToAllItems(OrderDto order,double qualifiedAmount,double discountAmount,Map contentMap) {
		//分摊打折金额到所有项上
		List<CommerceItemDto> items = order.getItems();
		for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
			
			CommerceItemDto commerceItemDto = iterator.next();
			if(!commerceItemDto.isSelected() ){
				 continue;
			}
			double orderDiscountShare = commerceItemDto.getItemPrice().getOrderDiscountShare();
			double amount = commerceItemDto.getItemPrice().getAmount();
			if(isProtectPrice(contentMap, commerceItemDto.getProductId())){
				continue;
			}
			if(qualifiedAmount<=0||discountAmount<=0){
				continue;
			}
			double itemShareAmount = amount/qualifiedAmount*discountAmount;
			commerceItemDto.getItemPrice().setOrderDiscountShare(orderDiscountShare+itemShareAmount);
		}
	}
	
	private void discountShareToRelevantProduct(OrderDto order,double qualifiedAmount,double discountAmount,PurchaseConditionsRuleDto purchaseConditionsRule,Map contentMap) {
		//分摊打折金额到商品项上
		List<CommerceItemDto> items = order.getItems();
		for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
			
			CommerceItemDto commerceItemDto = iterator.next();
			if(!commerceItemDto.isSelected() ){
				 continue;
			}
			if(purchaseConditionsRule.getCondition().getPromoProduct()==null||purchaseConditionsRule.getCondition().getPromoProduct().getProductId()==null){
				 continue;
			}
			String productId = purchaseConditionsRule.getCondition().getPromoProduct().getProductId();
			if(!productId.equals(commerceItemDto.getProductId())){
				continue;
			}
			if(isProtectPrice(contentMap,productId)){
				continue;
			}
			double orderDiscountShare = commerceItemDto.getItemPrice().getOrderDiscountShare();
			double amount = commerceItemDto.getItemPrice().getAmount();
			if(qualifiedAmount<=0||discountAmount<=0){
				continue;
			}
			double itemShareAmount = amount/qualifiedAmount*discountAmount;
			commerceItemDto.getItemPrice().setOrderDiscountShare(orderDiscountShare+itemShareAmount);
		}
	}
	
	private double getQulifiedAmount(OrderDto order, PurchaseConditionsRuleDto purchaseConditionsRule,Map contentMap) {
		double qualifiedAmount=0.0d;
        List<CommerceItemDto> commerceItems = order.getItems();
        for(CommerceItemDto item : commerceItems) {
            if(item.isSelected() && validateCategory(purchaseConditionsRule.getCondition(), item.getProductId(),contentMap)){
                qualifiedAmount += item.getItemPrice().getAmount();
            }
        }
		return qualifiedAmount;
	}
	
	private double getRelevantProductQulifiedAmount(OrderDto order, PurchaseConditionsRuleDto purchaseConditionsRule,Map contentMap) {
		double qualifiedAmount=0.0d;
        List<CommerceItemDto> commerceItems = order.getItems();
        for(CommerceItemDto item : commerceItems) {
        	if(purchaseConditionsRule.getCondition().getPromoProduct()==null||purchaseConditionsRule.getCondition().getPromoProduct().getProductId()==null){
				 continue;
			}
			String productId = purchaseConditionsRule.getCondition().getPromoProduct().getProductId();
			if(isProtectPrice(contentMap,productId)){
				continue;
			}
            if(item.isSelected() && productId.equals(item.getProductId())){
                qualifiedAmount += item.getItemPrice().getAmount();
            }
        }
		return qualifiedAmount;
	}

	protected boolean isProtectPrice(Map contentMap,String productId){
		String companyCode = (String) contentMap.get("branchCompanyId");
        Response<ProdSellPriceInfoDto> goodsSellPrice = goodsPriceDubboService.getGoodsSellPrice(productId, companyCode);
        if(goodsSellPrice==null || !goodsSellPrice.isSuccess() || goodsSellPrice.getResultObject() == null){
        	logger.warn("can't find goodsSellPrice by id {}, companyCode {}", productId,companyCode);
        	return false;
        }
        ProdSellPriceInfoDto prodSellPrice = goodsSellPrice.getResultObject();
        if(prodSellPrice.isPriceProtection()){
        	logger.warn("goodsSellPrice by product id {} and companyCode {} whose isPriceProtection is {}", productId,companyCode, true);
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
	public boolean validateCategory(RuleConditionsDto condition, String productId, Map contentMap){
        Response<ProductDto> productRes = productSCDubboService.queryByProductId(productId);
        if(productRes == null || !productRes.isSuccess() || productRes.getResultObject() == null){
        	logger.warn("can't find product by id {}", productId);
        }
        ProductDto productDto  = productRes.getResultObject();
        if(isProtectPrice(contentMap,productId)){
        	return false;
		}
        
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
	
	protected void addAdjustment(double discountAmount, OrderDto order, PricingModel pricingModel, Map contentMap) {
		String storeId = (String) contentMap.get("storeId");
		String branchCompanyId = (String) contentMap.get("branchCompanyId");

		OrderPriceInfoDto priceInfo = order.getPriceInfoDto();
		List<PriceAdjustmentDto> adjustments = priceInfo.getAdjustments();
		if(adjustments.isEmpty()){
			createNewAdjustment(discountAmount, pricingModel, priceInfo);
			logger.info("pricingModel {} promotion  discountAmount {} to order whose amount {} and branchCompanyId {}, storeId {}!",pricingModel.getId(),discountAmount,priceInfo.getAmount(), branchCompanyId,storeId);
			return;
		}
		boolean isExisting=false;
		for (Iterator<PriceAdjustmentDto> iterator = adjustments.iterator(); iterator.hasNext();) {
			PriceAdjustmentDto priceAdjustmentDto = iterator.next();
			String pricingModelId = priceAdjustmentDto.getPricingModel();
			if(pricingModel.getId().equals(pricingModelId)){
				priceAdjustmentDto.setAdjustment(priceAdjustmentDto.getAdjustment()+discountAmount);
				isExisting=true;
				logger.info("pricingModel {} promotion  discountAmount {} to order whose amount {} and branchCompanyId {}, storeId {}!",pricingModel.getId(),discountAmount,priceInfo.getAmount(), branchCompanyId,storeId);
			}
		}
		
		if(!isExisting){
			createNewAdjustment(discountAmount, pricingModel, priceInfo);
			logger.info("pricingModel {} promotion  discountAmount {} to order whose amount {} and branchCompanyId {}, storeId {}!",pricingModel.getId(),discountAmount,priceInfo.getAmount(), branchCompanyId,storeId);
		}
	}
	
	protected void createNewAdjustment(double discountAmount, PricingModel pricingModel, OrderPriceInfoDto priceInfo) {
		PriceAdjustmentDto adjustmentDto = new PriceAdjustmentDto();
		adjustmentDto.setAdjustmentDescription(pricingModel.getPromotionName());
		adjustmentDto.setPricingModelType(pricingModel.getType());
		adjustmentDto.setPricingModel(pricingModel.getId());
		adjustmentDto.setAdjustment(discountAmount);
		adjustmentDto.setTotalAdjustment(discountAmount);
		priceInfo.getAdjustments().add(adjustmentDto);
	}
	
	
	protected int calPromotionCount(OrderDto order, RuleConditionsDto condition, Map contentMap){
		
		String purchaseType = condition.getPurchaseType();
		int count=0;
		if(RuleConditionsDto.PurchaseType.CATEGORY.getName().equals(purchaseType)){
			return calCategoryCount(order, condition,contentMap);
		}
		if(RuleConditionsDto.PurchaseType.PRODUCT.getName().equals(purchaseType)){
			return calProductCount(order, condition);
		}
		if(RuleConditionsDto.PurchaseType.ALL.getName().equals(purchaseType)){
			return calAllCount(order, condition);
		}
		return count;
	}
	
	private int calAllCount(OrderDto order, RuleConditionsDto condition) {
		double qualifiedAmount=0.0d;
		long qty=0L;
		List<CommerceItemDto> commerceItems = order.getItems();
		for(CommerceItemDto item : commerceItems) {
			if(!item.isSelected()){
				continue;
			}
			qualifiedAmount += item.getItemPrice().getAmount();
			 //qty=qty+item.getQuantity();
            qty=qty+item.getSaleQuantity();
		}
	    if(qualifiedAmount >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.AMOUNT.getName().equals(condition.getConditionType())){
	    	return (int) (qualifiedAmount/condition.getConditionValue().doubleValue());
        }
        if(qty >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.QUANTITY.getName().equals(condition.getConditionType())){
        	return (int) (qty/condition.getConditionValue().doubleValue());
        }
		return 0;
	}
	
	private int calProductCount(OrderDto order, RuleConditionsDto condition) {
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
                //qty=qty+item.getQuantity();
                qty=qty+item.getSaleQuantity();
                hasProduct=true;
			}
		}
		if(!hasProduct){
			return 0;
		}
	    if(qualifiedAmount >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.AMOUNT.getName().equals(condition.getConditionType())){
	    	return (int) (qualifiedAmount/condition.getConditionValue().doubleValue());
        }
        if(qty >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.QUANTITY.getName().equals(condition.getConditionType())){
        	return (int) (qty/condition.getConditionValue().doubleValue());
        }
		return 0;
	}
	
	private int calCategoryCount(OrderDto order, RuleConditionsDto condition,Map contentMap) {
		//按照分类计算金额
		double qualifiedAmount=0.0d;
		long qty=0L;
        List<CommerceItemDto> commerceItems = order.getItems();
        boolean hasCategory=false;
        for(CommerceItemDto item : commerceItems) {
            if(item.isSelected() && validateCategory(condition, item.getProductId(),contentMap)){
                qualifiedAmount += item.getItemPrice().getAmount();
                qty=qty+item.getSaleQuantity();
                hasCategory=true;
            }
        }
        if(!hasCategory){
			return 0;
		}
        if(qualifiedAmount >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.AMOUNT.getName().equals(condition.getConditionType())){
            return (int) (qualifiedAmount/condition.getConditionValue().doubleValue());
        }
        if(qty >= condition.getConditionValue().doubleValue()&&RuleConditionsDto.ConditionType.QUANTITY.getName().equals(condition.getConditionType())){
            return (int) (qty/condition.getConditionValue().doubleValue());
        }
        return 0;
	}
	
	
}
