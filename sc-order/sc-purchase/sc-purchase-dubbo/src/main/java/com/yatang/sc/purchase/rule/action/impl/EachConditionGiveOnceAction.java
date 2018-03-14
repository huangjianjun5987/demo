/**
 * 
 */
package com.yatang.sc.purchase.rule.action.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.promotion.dto.GiveRuleConditionsDto;
import com.yatang.sc.promotion.dto.RuleConditionsDto;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.ItemPriceInfoDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.service.ItemPricingEngine;
import com.yatang.sc.purchase.service.impl.PurchaseServiceImpl;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;

/**
 * 每满足一次触发一次规则
 * 
 * @author dengdongshan
 *
 */
@Service
public class EachConditionGiveOnceAction extends PurchaseConditionsAction {
    @Autowired
    ItemPricingEngine itemPricingEngine;
    @Autowired
    PurchaseServiceImpl purchaseService;
    Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yatang.sc.purchase.action.service.PromotionAction#apply(com.yatang.sc
	 * .purchase.dto.OrderPriceInfoDto, com.yatang.sc.purchase.dto.OrderDto,
	 * java.util.List, java.util.Map)
	 */
	@Override
	public void apply(OrderDto order, List<PricingModel> pricingModels, Map contentMap) {
		String branchCompanyId = (String) contentMap.get("branchCompanyId");
		if(StringUtils.isEmpty(branchCompanyId)){
			return;
		}
		for (Iterator<PricingModel> pricingModelIterators = pricingModels.iterator(); pricingModelIterators
				.hasNext();) {
			PricingModel pricingModel = pricingModelIterators.next();
			PromotionRuleDto promotionRule = pricingModel.getPromotionRule();
			if (!promotionRule.isUseConditionRule()) {
				continue;
			}
			if (!PromotionRuleDto.RuleName.EACHCONDITIONGIVEONCE.getName().equals(promotionRule.getRuleName())) {
				continue;
			}
			if (promotionRule.getEachConditionGiveOnce().getGiveRuleCondition() == null) {
				continue;
			}
			if (promotionRule.getEachConditionGiveOnce().getGiveRuleCondition().getPromoProduct() == null) {
				continue;
			}
			
	        //赠送商品
			List<RuleConditionsDto> conditions = promotionRule.getEachConditionGiveOnce().getConditions();
			GiveRuleConditionsDto rule = promotionRule.getEachConditionGiveOnce().getGiveRuleCondition();
			if (conditions == null || conditions.isEmpty()) {
				continue;
			}
			int promtionCount = getPromotionGiveCount(order, contentMap, conditions);
			String productId = rule.getPromoProduct().getProductId();
	   		CommerceItemDto freeItem = createFreeItem(pricingModel, rule, promtionCount, productId, branchCompanyId);
	   		if(freeItem!=null){
		   		order.getItems().add(freeItem);
		   		addAdjustment(freeItem.getItemPrice().getAmount(), order, pricingModel, contentMap);
		   		log.info("promotion {} give free item {} with promtionCount {} to FranchiseeId {} ,FranchiseeStoreId{}",pricingModel.getId(),freeItem.getProductId(),promtionCount,order.getFranchiseeId(),order.getFranchiseeStoreId());
	   		}
		}

	}

	private CommerceItemDto createFreeItem(PricingModel pricingModel, GiveRuleConditionsDto rule, int promtionCount,
			String productId,String branchCompanyId) {
        Response<ProductDto> productRes = productSCDubboService.queryByProductId(productId);
        if(!productRes.isSuccess()||productRes==null||productRes.getResultObject()==null){
        	log.info("no free gift product found by productId{}",productId);
        	return null;
        }
        ProdSellPriceInfoDto sellPrice = getProductPriceInfo(productId,branchCompanyId);
        Integer	salesInsideNumber = 1;
		String productName = rule.getPromoProduct().getProductName();
		CommerceItemDto freeItem=new CommerceItemDto();
		freeItem.setItemPrice(new ItemPriceInfoDto());

        if (sellPrice!=null){
            salesInsideNumber = sellPrice.getSalesInsideNumber();
            if(sellPrice.getSellSectionPrices()!=null&&!sellPrice.getSellSectionPrices().isEmpty()){
   			 for(ProdSellSectionPriceDto rangePrice : sellPrice.getSellSectionPrices()){
   		        	freeItem.getItemPrice().setListPrice(rangePrice.getPrice().doubleValue());
   		        	freeItem.getItemPrice().setSalePrice(rangePrice.getPrice().doubleValue());
   		        }
            }
        }

		freeItem.setProductName(productRes.getResultObject().getName());
		freeItem.setProductCode(productRes.getResultObject().getProductCode());
		freeItem.setProductImg(productRes.getResultObject().getThumbnailImage());
		freeItem.setSkuId(productId);
		freeItem.setProductId(productId);
		freeItem.setUnitQuantity(salesInsideNumber);
		freeItem.setSelected(true);
		freeItem.getItemPrice().setAdjustments(null);
		if (productRes.getResultObject().getSellFullCase() != null && productRes.getResultObject().getSellFullCase() == 1) {
			freeItem.setQuantity(rule.getRule().getPreferentialValue().longValue() * promtionCount * salesInsideNumber);
			freeItem.setSellFullCase(1);
		} else {
			freeItem.setQuantity(rule.getRule().getPreferentialValue().longValue() * promtionCount);
			freeItem.setSellFullCase(0);
		}
		freeItem.getItemPrice().setAmount(freeItem.getItemPrice().getSalePrice()*freeItem.getQuantity());
		freeItem.setSaleQuantity(rule.getRule().getPreferentialValue().longValue()*promtionCount);
		freeItem.setPriceModel(pricingModel.getId());
		freeItem.setType(CommerceItemTypes.PROMOTION);
		freeItem.setProductId(productId);
		freeItem.setProductName(productName);
		try {
			purchaseService.setCommerceItemProductInfo(freeItem, branchCompanyId);
		} catch (PricingException e) {
			log.warn("EachConditionGiveOnceAction: set commerceItem properties error for productId {}, branchCompanyId {}",productId,branchCompanyId);
		}
		return freeItem;
	}
	
    public double getPrice(CommerceItemDto item, Map contentMap) throws PricingException {
        if(goodsPriceDubboService == null){
            throw new PricingException("服务器异常，查询商品价格失败！");
        }
        String companyCode = (String) contentMap.get("branchCompanyId");
        ProdSellPriceInfoDto sellPrice = getProductPriceInfo(item.getProductId(), companyCode);
        double price = 0.0;
        long quantity = item.getQuantity();
        if(sellPrice == null ) {
            //throw new PricingException("商品价格查询失败，productI的:" + item.getProductId() + ",companyCode:" + companyCode);
            return 0.0;
        }
//        if(quantity < sellPrice.getMinNumber()){
//            //throw new PricingException("商品数量小于最小起订数量，productI的:" + item.getProductId() );
//        }
        for(ProdSellSectionPriceDto rangePrice : sellPrice.getSellSectionPrices()){
            if(quantity >= rangePrice.getStartNumber() && (rangePrice.getEndNumber() == null
                    || quantity <= rangePrice.getEndNumber())){
                price = rangePrice.getPrice().doubleValue();
            }
        }
        return price;
    }
	
	/**
	 * 获取赠送的次数
	 * @param order
	 * @param contentMap
	 * @param conditions
	 * @return
	 */
	private int getPromotionGiveCount(OrderDto order, Map contentMap, List<RuleConditionsDto> conditions) {
		int promtionCount=0;
		for (int i=0;i<conditions.size();i++) {
			int tempPromtionCount;
			if(i==0){
				promtionCount = calPromotionCount(order,conditions.get(i), contentMap);
				continue;
			}
			tempPromtionCount = calPromotionCount(order,conditions.get(i), contentMap);
			if(promtionCount>tempPromtionCount){
				promtionCount=tempPromtionCount;
			}
		}
		return promtionCount;
	}
	
	public ProdSellPriceInfoDto getProductPriceInfo(String productId, String branchCompanyId) {
	        return itemPricingEngine.getProductPriceInfo(productId, branchCompanyId);
	}
	
}
