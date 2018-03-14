package com.yatang.sc.purchase.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.ItemPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.service.ItemPricingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by qiugang on 7/10/2017.
 */
@Service("itemPricingEngine")
public class ItemPricingEngineImpl implements ItemPricingEngine {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoodsPriceQueryDubboService goodsPriceDubboService;

    public void pricingItems(List<CommerceItemDto> items, Map contentMap,boolean isMock)  throws PricingException  {

        for (CommerceItemDto item : items) {
            if(isMock){
                mockPricingItem(item);
            }else {
                pricingItem(item, contentMap);
            }
        }
    }


    @Override
    public void pricingItem(CommerceItemDto item, Map contentMap)  throws PricingException {
        ItemPriceInfoDto priceInfo = new ItemPriceInfoDto();
        double price = getPrice(item, contentMap);
        logger.info("price:{},item:{}",price,item.getSkuId());
        double amount = PricingUtil.roundPrice(price * item.getQuantity());
        priceInfo.setListPrice(price);
        priceInfo.setSalePrice(price);
        priceInfo.setAmount(amount);
        priceInfo.setRawTotalPrice(amount);
        PriceAdjustmentDto adjustment = createItemPriceAdjment("ListPrice Amount", item.getQuantity(), price, amount);
        priceInfo.getAdjustments().add(adjustment);
        item.setItemPrice(priceInfo);
        
        //计算套餐子项价格
        List<CommerceItemDto> subItems = item.getSubItems();
        if(CommerceItemTypes.BUNDLE.equals(item.getType())&&!subItems.isEmpty()){
        		pricingComboItem(item,subItems, contentMap);
        }
    }
    
    public void pricingComboItem(CommerceItemDto comboItem, List<CommerceItemDto> subItems, Map contentMap)  throws PricingException {
       
        Map<String,Double> productPriceMap=new HashMap<>();
        double totalListPrice=0.0d;
        for (Iterator<CommerceItemDto> iterator = subItems.iterator(); iterator.hasNext();) {
			CommerceItemDto item = iterator.next();
			 double price = getPrice(item, contentMap);
			 productPriceMap.put(item.getSkuId(), price);
			 totalListPrice=totalListPrice+price*(PricingUtil.div(item.getQuantity(), comboItem.getQuantity(), 2));
		}
        if(comboItem.getItemPrice()==null){
        	return;
        }
        double comboSalePrice = comboItem.getItemPrice().getSalePrice();
        double rate = PricingUtil.div(comboSalePrice, totalListPrice, 10);
        
		for (Iterator<CommerceItemDto> iterator = subItems.iterator(); iterator.hasNext();) {
			 ItemPriceInfoDto priceInfo = new ItemPriceInfoDto();
			CommerceItemDto item = iterator.next();
			Double price = productPriceMap.get(item.getSkuId());
			logger.info("price:{},item:{}",price,item.getSkuId());
	        double amount = PricingUtil.roundPrice(rate*price * item.getQuantity());
	       // double rawAmount = PricingUtil.roundPrice(price * item.getQuantity());
	        priceInfo.setListPrice(price);
	        priceInfo.setSalePrice(rate*price);
	        priceInfo.setAmount(amount);
	        priceInfo.setRawTotalPrice(amount);
	        PriceAdjustmentDto adjustment = createItemPriceAdjment("ListPrice Amount", item.getQuantity(), price, amount);
	        priceInfo.getAdjustments().add(adjustment);
	        item.setItemPrice(priceInfo);
		}        
    }
    
    public void mockPricingItem(CommerceItemDto item)  throws PricingException {
        ItemPriceInfoDto priceInfo = new ItemPriceInfoDto();
        priceInfo.setListPrice(0D);
        priceInfo.setSalePrice(0D);
        priceInfo.setAmount(0D);
        priceInfo.setRawTotalPrice(0D);
        PriceAdjustmentDto adjustment = createItemPriceAdjment("ListPrice Amount", item.getQuantity(), 0D, 0D);
        priceInfo.getAdjustments().add(adjustment);
        item.setItemPrice(priceInfo);
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


    public static PriceAdjustmentDto createItemPriceAdjment(String description, long quantity, double adjustment, double amount){
        PriceAdjustmentDto adjustmentDto = new PriceAdjustmentDto();
        adjustmentDto.setAdjustment(adjustment);
        adjustmentDto.setQuantityAdjusted(quantity);
        adjustmentDto.setTotalAdjustment(amount);
        adjustmentDto.setAdjustmentDescription(description);
        return adjustmentDto;
    }


    @LocalCache(group = "purchasePriceInfo", expireAfterWrite = 5, maximumSize = 10000)
    public ProdSellPriceInfoDto getProductPriceInfo(String productId, String branchCompanyId){
        if(StringUtils.isEmpty(productId)){
            return null;
        }
        Response<ProdSellPriceInfoDto> response = goodsPriceDubboService.getGoodsSellPrice(productId, branchCompanyId);
        if (response == null || !response.isSuccess()) {
            return null;
        }
        return response.getResultObject();
    }
}
