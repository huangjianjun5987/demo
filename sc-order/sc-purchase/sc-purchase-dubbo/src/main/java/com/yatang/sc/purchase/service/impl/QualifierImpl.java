package com.yatang.sc.purchase.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.order.states.PromotionStates;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.service.Qualifier;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductSCDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来校验订单是否满足优惠券或者促销
 * Created by qiugang on 9/19/2017.
 */
@Service("qualifier")
public class QualifierImpl implements Qualifier {

    private Logger log = LoggerFactory.getLogger(QualifierImpl.class);
    @Autowired
    private ProductSCDubboService productSCDubboService;// 商品查询dubboservice（供应链）
    @Autowired
    private PromotionService promotionService;



    /**
     * 用来校验优惠券或者促销是否在当前订单可以用
     * @param priceInfo
     * @param order
     * @param pricingModel
     * @param contentMap
     * @return
     */
    public Map qualifyCoupon(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap){
        Map result = new HashMap();
        boolean qualified = false;
        if(!(PromotionStates.RELEASED.equals(pricingModel.getStatus()))){
            log.info("pricingModel status is {}", pricingModel.getStatus());
            result.put("qualifyResult", Boolean.valueOf(qualified));
            return result;
        }
        Date curentDate = new Date(System.currentTimeMillis());
        if(curentDate.before(pricingModel.getStartDate())){
            log.info("pricingModel startDate is {}", pricingModel.getStartDate());
            result.put("qualifyResult", Boolean.valueOf(qualified));
            return result;
        }
        if(curentDate.after(pricingModel.getEndDate())){
            log.info("pricingModel endDate is {}", pricingModel.getStartDate());
            result.put("qualifyResult", Boolean.valueOf(qualified));
            return result;
        }
        if(order.isElectronicOrder()){
            log.info("虚拟商品不能使用优惠券");
            result.put("qualifyResult", Boolean.valueOf(qualified));
            return result;
        }
        //与优惠券叠加不使用以前逻辑，暂时注释
//        List<PriceAdjustmentDto> adjustmentDtos = priceInfo.getAdjustments();
//        if(!CollectionUtils.isEmpty(adjustmentDtos)){
//            for(PriceAdjustmentDto adjustmentDto : adjustmentDtos){
//                if(!StringUtils.isEmpty(adjustmentDto.getPricingModel())
//                        && adjustmentDto.getPricingModelType() != null
//                        && adjustmentDto.getPricingModelType() == 0){
//                    PromotionPo prompo = promotionService.queryById(adjustmentDto.getPricingModel());
//                    if(prompo != null) {
//                        Integer promotionSuperpose = prompo.getIsSuperposeProOrCouDiscount();
//                        if (promotionSuperpose == null || promotionSuperpose == 0){
//                            result.put("qualifyResult", Boolean.valueOf(qualified));
//                            return result;
//                        }
//                        if(pricingModel.getSuperposePromo()== null || pricingModel.getSuperposePromo()==false){
//                            result.put("qualifyResult", Boolean.valueOf(qualified));
//                            return result;
//                        }
//                    }
//                }
//            }
//        }
        double qualifiedAmount = 0.0;
        String branchCompanyId = (String) contentMap.get("branchCompanyId");
        String storeId = (String)contentMap.get("storeId");
        if(!validateBranchCompany(pricingModel, branchCompanyId)){
            result.put("qualifyResult", qualified);
            return result;
        }
        if(!validateStore(pricingModel, storeId)){
            result.put("qualifyResult", qualified);
            return result;
        }
        if(CollectionUtils.isEmpty(pricingModel.getCategories1()) && CollectionUtils.isEmpty(pricingModel.getCategories2())
                && CollectionUtils.isEmpty(pricingModel.getCategories3()) && CollectionUtils.isEmpty(pricingModel.getCategories4())){
        	if(priceInfo==null){
        		return result;
        	}
            qualifiedAmount = priceInfo.getAmount();
            if(priceInfo.getCouponDiscountAmount() > 0){
                qualifiedAmount = qualifiedAmount + priceInfo.getCouponDiscountAmount();
            }
            if(priceInfo.getUserDiscountAmount() > 0){
                qualifiedAmount = qualifiedAmount + priceInfo.getUserDiscountAmount();
            }
            log.info("pricingModel {} qualifiedAmount all order:{}!", pricingModel.getId(), qualifiedAmount);
            if(qualifiedAmount >= pricingModel.getQuanifyAmount()){
                qualified = true;
            }

        }else{
            //按照分类计算金额
            List<CommerceItemDto> commerceItems = order.getItems();
            for(CommerceItemDto item : commerceItems) {
                if(item.isSelected() && validateCategory(pricingModel, item.getProductId())){
                    qualifiedAmount += item.getItemPrice().getAmount();
                }
            }
            log.info("pricingModel {} qualifiedAmount {}!", pricingModel.getId(), qualifiedAmount);
            if(qualifiedAmount >= pricingModel.getQuanifyAmount()){
                qualified = true;
            }
        }
        result.put("qualifyResult", Boolean.valueOf(qualified));
        result.put("qualifiedAmount", qualifiedAmount);
        return result;


    }
    /**
     * 校验优惠券或者促销是否可以跳过
     * @param priceInfo
     * @param order
     * @param pricingModel
     * @return
     */
    public boolean validatePricingModel(OrderPriceInfoDto priceInfo, OrderDto order,PricingModel pricingModel){

        if(!(PromotionStates.RELEASED.equals(pricingModel.getStatus()))){
            log.info("pricingModel status is {}", pricingModel.getStatus());
            return false;
        }
        Date curentDate = new Date(System.currentTimeMillis());
        if(curentDate.before(pricingModel.getStartDate())){
            log.info("pricingModel startDate is {}", pricingModel.getStartDate());
            return false;
        }
        if(curentDate.after(pricingModel.getEndDate())){
            log.info("pricingModel endDate is {}", pricingModel.getStartDate());
            return false;
        }

        boolean result = true;
        List<PriceAdjustmentDto> adjustmentDtos = priceInfo.getAdjustments();
        if(CollectionUtils.isEmpty(adjustmentDtos)){
            return result;
        }
        for(PriceAdjustmentDto adjustmentDto : adjustmentDtos){
            if(!StringUtils.isEmpty(adjustmentDto.getPricingModel())){
                if(adjustmentDto.getPricingModelType() == null
                        && adjustmentDto.getPricingModelType().equals(pricingModel.getType())){
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

        return result;
    }


    /**
     * 用来校验优惠券或者促销是否在当前订单可以用
     * @param priceInfo
     * @param order
     * @param pricingModel
     * @param contentMap
     * @return
     */
    public Map qualifyOrder(OrderPriceInfoDto priceInfo, OrderDto order, PricingModel pricingModel, Map contentMap){

        log.info("qualify pricingModel: {}", JSON.toJSONString(pricingModel));
        Map result = new HashMap();
        boolean qualified = false;
        if(!validatePricingModel(priceInfo, order, pricingModel)){
            log.info("pricingModel {} skipped!", JSON.toJSONString(pricingModel));
            result.put("qualifyResult", qualified);
            return result;
        }
        double qualifiedAmount = 0.0;
        String branchCompanyId = (String) contentMap.get("branchCompanyId");
        String storeId = (String)contentMap.get("storeId");
        if(!validateBranchCompany(pricingModel, branchCompanyId)){
            result.put("qualifyResult", qualified);
            return result;
        }
        if(!validateStore(pricingModel, storeId)){
            result.put("qualifyResult", qualified);
            return result;
        }
        if(CollectionUtils.isEmpty(pricingModel.getCategories1()) && CollectionUtils.isEmpty(pricingModel.getCategories2())
                && CollectionUtils.isEmpty(pricingModel.getCategories3()) && CollectionUtils.isEmpty(pricingModel.getCategories4())){
            qualifiedAmount = priceInfo.getAmount();
            log.info("pricingModel {} qualifiedAmount all order:{}!", pricingModel.getId(), qualifiedAmount);

            if(qualifiedAmount >= pricingModel.getQuanifyAmount()){
                qualified = true;
            }

        }else{
            //按照分类计算金额
            List<CommerceItemDto> commerceItems = order.getItems();
            for(CommerceItemDto item : commerceItems) {
                if(item.isSelected() && validateCategory(pricingModel, item.getProductId())){
                    qualifiedAmount += item.getItemPrice().getAmount();
                }
            }
            log.info("pricingModel {} qualifiedAmount {}!", pricingModel.getId(), qualifiedAmount);
            if(qualifiedAmount >= pricingModel.getQuanifyAmount()){
                qualified = true;
            }
        }
        result.put("qualifyResult", Boolean.valueOf(qualified));
        result.put("qualifiedAmount", qualifiedAmount);
        return result;
    }

    /**
     * 校验分公司
     * @param pricingModel
     * @param branchCompanyId
     * @return
     */
    public boolean validateBranchCompany(PricingModel pricingModel, String branchCompanyId){

        List<String> branchCompanies = pricingModel.getCompanies();
        if(branchCompanies!= null && branchCompanies.size() > 0){

            if(branchCompanyId == null){
                log.info("branchCompanyId is null!");
                return false;
            }
            if(!branchCompanies.contains(branchCompanyId)){
                log.info("promotion {} not support branchCompanyId {}!", pricingModel.getId(), branchCompanyId);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验门店
     * @param pricingModel
     * @param storeId
     * @return
     */
    public  boolean validateStore(PricingModel pricingModel, String storeId){
        List<String> stores = pricingModel.getStores();
        if(stores!= null && stores.size() > 0){
            if(storeId == null){
                log.info("storeId is null!");
                return false;
            }
            if(!stores.contains(storeId)){
                log.info("promotion {} not support store {}!", pricingModel.getId(), storeId);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验分类
     * @param pricingModel
     * @param productId
     * @return
     */
    public boolean validateCategory(PricingModel pricingModel, String productId){
        Response<ProductDto> productRes = productSCDubboService.queryByProductId(productId);
        if(productRes == null || !productRes.isSuccess() || productRes.getResultObject() == null){
            log.warn("can't find product by id {}", productId);
        }
        ProductDto productDto  = productRes.getResultObject();
        if(!CollectionUtils.isEmpty(pricingModel.getCategories1())){
            String firstCategory = productDto.getFirstLevelCategoryId();
            if(!isContains(pricingModel.getCategories1(), firstCategory)){
                log.info("promotion {} not support firstCategory {}!", pricingModel.getId(), firstCategory);
                return false;
            }
        }

        if(!CollectionUtils.isEmpty(pricingModel.getCategories2())){
            String secondCategory = productDto.getSecondLevelCategoryId();
            if(!isContains(pricingModel.getCategories2(), secondCategory)){
                log.info("promotion {} not support secondCategory {}!", pricingModel.getId(), secondCategory);
                return false;
            }
        }

        if(!CollectionUtils.isEmpty(pricingModel.getCategories3())){
            String thirdCategory = productDto.getThirdLevelCategoryId();
            if(!isContains(pricingModel.getCategories3(), thirdCategory)){
                log.info("promotion {} not support thirdCategory {}!", pricingModel.getId(), thirdCategory);
                return false;
            }
        }

        if(!CollectionUtils.isEmpty(pricingModel.getCategories4())){
            String fourthCategory = productDto.getFourthCategoryId();
            if(!isContains(pricingModel.getCategories4(), fourthCategory)){
                log.info("promotion {} not support fourthCategory {}!", pricingModel.getId(), fourthCategory);
                return false;
            }
        }
        return true;
    }


    public boolean isContains(List<String> collection, String value){
        if(value == null){
            return false;
        }
        if(collection.contains(value)){
            return true;
        }
        return false;
    }


}
