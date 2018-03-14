package com.yatang.sc.purchase.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.dto.PromotionRuleDto;
import com.yatang.sc.order.domain.AvailableCouponActivityPo;
import com.yatang.sc.order.domain.CouponActivityPo;
import com.yatang.sc.order.domain.PromoCategoriesPo;
import com.yatang.sc.order.domain.PromoCompaniesPo;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.CouponActivityService;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.purchase.calculator.OrderDiscountPriceCalculator;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dubboservice.util.PricingUtil;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.service.ItemPricingEngine;
import com.yatang.sc.purchase.service.PricingEngineService;
import com.yatang.sc.purchase.service.Qualifier;
import com.yatang.sc.purchase.service.ShippingPricingEngine;

/**
 * Created by qiugang on 7/10/2017.
 */
@Service("pricingEngineService")
public class PricingEngineServiceImpl implements PricingEngineService {
    private Logger log = LoggerFactory.getLogger(PricingEngineServiceImpl.class);

    @Autowired
    ItemPricingEngine itemPricingEngine;
    @Autowired
    ShippingPricingEngine shippingPricingEngine;

    @Resource(name = "couponDiscountCalculator")
    OrderDiscountPriceCalculator couponDiscountCalculator;

    @Resource(name = "orderDiscountPriceCalculator")
    OrderDiscountPriceCalculator orderDiscountPriceCalculator;

    @Resource(name = "userOrderDiscountPriceCalculator")
    OrderDiscountPriceCalculator userOrderDiscountPriceCalculator;


    @Autowired
    CouponActivityService couponActivityService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    Qualifier qualifier;
    
    @Override
    public void pricingOrderTotal(OrderDto order, Map contentMap,boolean isMock)  throws PricingException {

        log.info("start PricingEngineServiceImpl.pricingOrderTotal!!!");
        clearPromotionFreeItems(order);
        clearHasNotSuperposedPromotion(order);
        itemPricingEngine.pricingItems(order.getItems(), contentMap,isMock);
       
        boolean hasSelected=false;
        hasSelected = setOrderPriceInfo(order);
        //没有选中项则不进行价格计算
        if(!hasSelected){
        	return;
        }
        if(!order.isElectronicOrder()){
        	initCoupons(order,contentMap);
            //订单促销
            applyPricingModels(order, contentMap);
            //优惠券
            applyCoupons(order.getPriceInfoDto(), order, contentMap);
            //会员折扣
            userOrderDiscountPriceCalculator.price(order.getPriceInfoDto(), order,null,contentMap);
        }
        shippingPricingEngine.pricingShippingGroup(order);
        double shipping = order.getShippingGroupDto().getShippingPriceInfoDto().getAmount(); //by
        order.getPriceInfoDto().setShipping(shipping);
        order.getPriceInfoDto().setTotal(PricingUtil.roundPrice( order.getPriceInfoDto().getAmount() + shipping));
    }


    @Override
    public void pricingOrderAmount(OrderDto order, Map contentMap,boolean isMock)  throws PricingException {
    	clearPromotionFreeItems(order);
    	clearHasNotSuperposedPromotion(order);
        itemPricingEngine.pricingItems(order.getItems(), contentMap,isMock);
        boolean hasSelected=false;
        hasSelected = setOrderPriceInfo(order);

        //没有选中项则不进行价格计算
        if(!hasSelected){
        	return;
        }        //订单促销
        applyPricingModels(order, contentMap);
    }
    
    
	private boolean setOrderPriceInfo(OrderDto order) {
		 OrderPriceInfoDto orderPriceInfo = new OrderPriceInfoDto();
		boolean hasSelected=false;
		double orderAmount = 0.0d;
       for(CommerceItemDto item : order.getItems()){
           if(item.isSelected()){
               orderAmount+= item.getItemPrice().getAmount();
               hasSelected=true;
           }
       }
       orderPriceInfo.setAmount(PricingUtil.roundPrice(orderAmount));
       orderPriceInfo.setRawSubtotal(PricingUtil.roundPrice(orderAmount));
       order.setPriceInfoDto(orderPriceInfo);
		return hasSelected;
	}

	private void clearPromotionFreeItems(OrderDto order){
   	List<CommerceItemDto> items = order.getItems();
   	if(items==null||items.isEmpty()){
   		return;
   	}
   	for (Iterator<CommerceItemDto> iterator = items.iterator(); iterator.hasNext();) {
			CommerceItemDto commerceItem = iterator.next();
			String type = commerceItem.getType();
			if(CommerceItemTypes.PROMOTION.equals(type)){
				iterator.remove();
			}
		}
   }
   
   /**
    * 清除pricingOrderTotal时存储的数据
    * @param order
    */
	private void clearHasNotSuperposedPromotion(OrderDto order){
   	order.setHasNotSuperposedPromotion(false);
   }

	/**
	 * 通过传入的优惠券ID或者优惠券对象初始化促销PricingModel信息
	 * @param order
	 * @param contentMap
	 */
	private void initCoupons(OrderDto order,Map contentMap){
		 List<String> couponActivityIds = (List<String>)contentMap.get("activeCoupons");
		 List<AvailableCouponActivityPo> couponPos =null;
		 if(contentMap.get("couponPos")!=null){
			 couponPos=(List<AvailableCouponActivityPo>)contentMap.get("couponPos");
		 }
		
	        if(CollectionUtils.isEmpty(couponActivityIds)){
	        	//优惠券已无效
	        	order.setUseCoupon(false);
	        	log.info("applyCoupons.couponActivityIds is null");
	            return;
	        }
	        Map<String,PricingModel> couponPromotions=initCouponPromotions(order, contentMap, couponActivityIds, couponPos);
	       
	       if(couponPromotions.isEmpty()){
	    	   //没有优惠券找到
	        	order.setUseCoupon(false);
	        	log.info("applyCoupons.couponActivityIds is null");
	       }
	       contentMap.put("couponPromotions", couponPromotions);
	}

	/**
	 * 判断是否已存在优惠券对象以减少查询次数
	 * @param order
	 * @param contentMap
	 * @param couponActivityIds
	 * @param couponPos
	 * @param couponPromotions
	 */
	private Map<String, PricingModel> initCouponPromotions(OrderDto order, Map contentMap, List<String> couponActivityIds,
			List<AvailableCouponActivityPo> couponPos) {
		Map<String, PricingModel> couponPromotions=new HashMap<String,PricingModel>();
        Map<String, PromotionPo> couponsMap=new HashMap<String,PromotionPo>();
        Map<String, Boolean> couponsResultMap=new HashMap<String,Boolean>();
		if(!CollectionUtils.isEmpty(couponPos)){
        	for(AvailableCouponActivityPo couponActivityPo : couponPos){
                if(couponsResultMap.containsKey(couponActivityPo.getCouponId())){
                    continue;
                }
                PromotionPo coupon = couponsMap.get(couponActivityPo.getCouponId());
                if(coupon == null){
                    coupon = promotionService.queryById(couponActivityPo.getCouponId());
                    if(coupon != null){
                        couponsMap.put(couponActivityPo.getCouponId(), coupon);
                    }
                }
 	            if(coupon == null){
 	                continue;
 	            }
                PricingModel pricingModel = convertPromotionToPricingModel(coupon);
                Map qualifyResult = qualifier.qualifyOrder(order.getPriceInfoDto(), order, pricingModel, contentMap);
                Boolean result = (Boolean)qualifyResult.get("qualifyResult");
                couponsResultMap.put(couponActivityPo.getCouponId(), result);
 	            if(result){
                    couponPromotions.put(couponActivityPo.getCouponActivityId(), pricingModel);
                    break;
 	            }
        	 }

	    } else if(!CollectionUtils.isEmpty(couponActivityIds)){

            for (String activityId : couponActivityIds) {
                CouponActivityPo activityPo = couponActivityService.selectByPrimaryKey(Long.parseLong(activityId));
                if (activityPo == null) {
                    continue;
                }
                if(couponsResultMap.containsKey(activityPo.getPromoId())){
                    continue;
                }
                PromotionPo coupon = couponsMap.get(activityPo.getPromoId());
                if(coupon == null){
                    coupon = promotionService.queryById(activityPo.getPromoId());
                    if(coupon != null){
                        couponsMap.put(activityPo.getPromoId(), coupon);
                    }
                }
                if (coupon == null) {
                    continue;
                }
                PricingModel pricingModel = convertPromotionToPricingModel(coupon);
                Map qualifyResult = qualifier.qualifyOrder(order.getPriceInfoDto(), order, pricingModel, contentMap);
                Boolean result = (Boolean) qualifyResult.get("qualifyResult");
                couponsResultMap.put(activityPo.getPromoId(), result);
                if (result) {
                    couponPromotions.put(activityId, pricingModel);
                    break;
                }
            }
        }
        return couponPromotions;
	}
	
    @Override
    public List<AvailableCouponActivityPo> getAvailableCouponActivities(String storeId) {
    
        List<AvailableCouponActivityPo> couponActivityPoList = promotionService.queryAvailableCouponActivity(storeId);
        if(CollectionUtils.isEmpty(couponActivityPoList)){
            log.info("getAvailableCouponActivities.couponActivityPoList is null");
            return null;
        }
        return couponActivityPoList;
    }

    @Override
    public void applyCoupons(OrderPriceInfoDto orderPriceInfo, OrderDto order, Map contentMap){
    	order.getCouponActivities().clear();
    	//如果不使用优惠券并且有不能叠加的促销则不应用优惠券
    	if(!order.isUseCoupon()&&order.isHasNotSuperposedPromotion()){
    		return;
    	}
    	Map<String,PricingModel> couponPromotions = (Map<String,PricingModel>)contentMap.get("couponPromotions");
        if(CollectionUtils.isEmpty(couponPromotions)){
        	 return;
        }
        for(String couponId : couponPromotions.keySet()){
            PricingModel couponPromotion = couponPromotions.get(couponId);
            if(!order.getPriceInfoDto().getAdjustments().isEmpty()&&!couponPromotion.getSuperposePromo()){
            	continue;
            }
            contentMap.put("activityId",couponId);
			couponDiscountCalculator.price(orderPriceInfo, order, couponPromotion, contentMap);
        }

    }

    @Override
    public void applyPricingModels(OrderDto order, Map contentMap) throws PricingException{
        List<PricingModel> pricingModels = queryOrderGlobalPromotions();
        if(CollectionUtils.isEmpty(pricingModels)){
            log.info("applyPricingModels.pricingModels is null");
            return;
        }
       orderDiscountPriceCalculator.price(order, pricingModels, contentMap);

    }


    public List<PricingModel> queryOrderGlobalPromotions(){

        List<PromotionPo> availablePromotions = promotionService.listAvailablePromotions("order_promo");
        if(CollectionUtils.isEmpty(availablePromotions)){
            log.info("queryOrderGlobalPromotions.availablePromotions is null");
            return null;
        }
        List<PricingModel> pricingModels = new ArrayList<PricingModel>();
        for(PromotionPo promotionPo : availablePromotions){
            pricingModels.add(convertPromotionToPricingModel(promotionPo));
        }
        return pricingModels;
    }


    @Override
    public PricingModel convertPromotionToPricingModel(PromotionPo po){
        PricingModel model = new PricingModel();
        model.setId(po.getId());
        model.setType(po.getType());
        model.setPromotionName(po.getPromotionName());
        model.setDiscountType(po.getDiscountType());
        model.setStatus(po.getStatus());
        model.setStartDate(po.getStartDate());
        model.setEndDate(po.getEndDate());
        model.setPromotionDiscription(po.getPromotionDiscription());
        if(po.getPromotionRuleJson()!=null){
        	model.setPromotionRule(JSONUtils.toObject(po.getPromotionRuleJson(), PromotionRuleDto.class));
        }
        if(po.getPromotionRule()!=null){
        	PromotionRuleDto prd = BeanConvertUtils.convert(po.getPromotionRule(), PromotionRuleDto.class);
        	model.setPromotionRule(prd);
        }
        if(po.getPriority()!=null){
        	model.setPriority(po.getPriority());
        }
        if(po.getIsSuperposeUserDiscount() != null &&  po.getIsSuperposeUserDiscount() == 1){
            model.setSuperposeUserDiscount(true);
        }else{
            model.setSuperposeUserDiscount(false);
        }
        if(po.getIsSuperposeProOrCouDiscount() != null &&  po.getIsSuperposeProOrCouDiscount() == 1){
            model.setSuperposePromo(true);
        }else{
            model.setSuperposePromo(false);
        }

        if(po.getQuanifyAmount() != null){
            model.setQuanifyAmount(po.getQuanifyAmount().doubleValue());
        }
        if(po.getDiscount()!= null){
            model.setDiscount(po.getDiscount().doubleValue());
        }
        if(!CollectionUtils.isEmpty(po.getCompaniesPoList())){
            List<String> companies = new ArrayList<String>();
            for(PromoCompaniesPo companiesPo : po.getCompaniesPoList()){
                companies.add(companiesPo.getCompanyId());
            }
            model.setCompanies(companies);
        }
        if(po.getPromoCategoriesPo() != null){
            PromoCategoriesPo categoriesPo = po.getPromoCategoriesPo();
            int level = 1;
            if(categoriesPo.getCategoryLevel() != null){
                level = categoriesPo.getCategoryLevel();
            }
            List<String> category = new ArrayList<String>();
            category.add(categoriesPo.getCategoryId());
            if(level == 1){
                model.setCategories1(category);
            } else if(level == 2){
                model.setCategories2(category);
            } else if(level == 3){
                model.setCategories3(category);
            } else if(level == 4){
                model.setCategories4(category);
            }
        }

        if(!StringUtils.isEmpty(po.getStores())){
            String[] stores = po.getStores().getStoreId().split(",");
            model.setStores(CollectionUtils.arrayToList(stores));
        }
        return model;
    }




}
