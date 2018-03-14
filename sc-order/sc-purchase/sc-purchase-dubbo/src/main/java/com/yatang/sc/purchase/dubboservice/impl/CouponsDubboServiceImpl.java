package com.yatang.sc.purchase.dubboservice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.coupon.dubboservice.CouponsQueryDubboService;
import com.yatang.sc.dto.GrantCouponDto;
import com.yatang.sc.order.domain.CouponActivityRecord;
import com.yatang.sc.order.domain.CouponsParamPo;
import com.yatang.sc.order.domain.CouponsPo;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.PromoCategoriesPo;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.CouponActivityService;
import com.yatang.sc.order.service.CouponsService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.purchase.dto.CouponActivityRecordDto;
import com.yatang.sc.purchase.dto.CouponsDto;
import com.yatang.sc.purchase.dto.CouponsParamDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.ShoppingCart;
import com.yatang.sc.purchase.dubboservice.CouponsDubboService;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.exception.PurchaseException;
import com.yatang.sc.purchase.order.PricingModel;
import com.yatang.sc.purchase.service.PricingEngineService;
import com.yatang.sc.purchase.service.PurchaseService;
import com.yatang.sc.purchase.service.Qualifier;

/**
 * @描述: 优惠券
 * @类名: CouponDubboServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/9/19 11:23
 * @版本: v1.0
 */
@Service("couponsDubboService")
public class CouponsDubboServiceImpl implements CouponsDubboService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PricingEngineService pricingEngineService;

    @Autowired
    private Qualifier qualifier;
    @Autowired
    private CouponsQueryDubboService couponsQueryDubboService;
    @Autowired
    private OrderService orderService;

    /**
     * 我的优惠券领取是否使用
     * @param storeId 门店ID
     * @param state 状态
     * @return
     */
    @Override
    public Response<List<CouponActivityRecordDto>> queryStoreCouponActivities(String storeId, String state) {
        log.info("queryMyCouponActivity=>storeId:{}, state:{}", storeId,state);
        Response<List<CouponActivityRecordDto>> response = new Response<List<CouponActivityRecordDto>>();
        List<CouponActivityRecordDto> list = new ArrayList();
        try {
            List<CouponActivityRecord> couponActivityList = couponActivityService.queryStoreCouponActivities(storeId, state);
            if (couponActivityList.size() > 0){
                for(CouponActivityRecord couponActivity:couponActivityList) {
                    PromotionPo promotionPo = couponActivity.getPromotion();

                    CouponActivityRecordDto couponActivityRecordDto = new CouponActivityRecordDto();
                    couponActivityRecordDto.setId(couponActivity.getId());
                    couponActivityRecordDto.setPromoId(couponActivity.getPromoId());
                    couponActivityRecordDto.setStoreId(couponActivity.getStoreId());
                    couponActivityRecordDto.setState(couponActivity.getState());

                    couponActivityRecordDto.setpId(promotionPo!=null? promotionPo.getId():null);
                    couponActivityRecordDto.setPromotionName(promotionPo!=null? promotionPo.getPromotionName():null);
                    couponActivityRecordDto.setQuanifyAmount(promotionPo!=null? promotionPo.getQuanifyAmount():null);
                    couponActivityRecordDto.setPromotionType(promotionPo!=null? promotionPo.getPromotionType():null);
                    couponActivityRecordDto.setStatus(promotionPo!=null? promotionPo.getStatus():null);
                    couponActivityRecordDto.setStartDate(promotionPo!=null? promotionPo.getStartDate():null);
                    couponActivityRecordDto.setEndDate(promotionPo!=null? promotionPo.getEndDate():null);
                    couponActivityRecordDto.setPromotionDiscription(promotionPo!=null? promotionPo.getPromotionDiscription():null);
                    couponActivityRecordDto.setDiscountType(promotionPo!=null? promotionPo.getDiscountType():null);
                    couponActivityRecordDto.setDiscount(promotionPo!=null? promotionPo.getDiscount():null);
                    couponActivityRecordDto.setNote(promotionPo!=null? promotionPo.getNote():null);
                    PromoCategoriesPo promoCategoriesPo = couponActivity.getPromotion().getPromoCategoriesPo();
                    couponActivityRecordDto.setCategoryId(promoCategoriesPo !=null ? promoCategoriesPo.getCategoryId():null);
                    couponActivityRecordDto.setCategoryName(promoCategoriesPo !=null ? promoCategoriesPo.getCategoryName():null);
                    list.add(couponActivityRecordDto);
                }
                response.setResultObject(list);
                //List<CouponActivityRecordDto> couponActivityDtos = BeanConvertUtils.convertList(couponActivityList,CouponActivityRecordDto.class);
                //response.setResultObject(couponActivityDtos);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
            }
        }catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setResultObject(null);
            response.setSuccess(false);
            log.error( ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 已过期的我的优惠券领取记录
     * @param storeId
     * @return
     */
    @Override
    public Response<List<CouponActivityRecordDto>> queryOverdueCouponActivityItems(String storeId) {
        log.info("queryMyCouponActivity=>storeId:{}", storeId);
        Response<List<CouponActivityRecordDto>> response = new Response<List<CouponActivityRecordDto>>();
        List<CouponActivityRecordDto> list = new ArrayList();
        try {
            Date nowDate = new Date();
            List<CouponActivityRecord> couponActivityList = couponActivityService.queryOverdueCouponActivityItems(storeId);

            if (couponActivityList.size() > 0){
                for(CouponActivityRecord couponActivity:couponActivityList) {
                    PromotionPo promotionPo = couponActivity.getPromotion();

                    CouponActivityRecordDto couponActivityRecordDto = new CouponActivityRecordDto();
                    couponActivityRecordDto.setId(couponActivity.getId());
                    couponActivityRecordDto.setPromoId(couponActivity.getPromoId());
                    couponActivityRecordDto.setStoreId(couponActivity.getStoreId());
                    couponActivityRecordDto.setState(couponActivity.getState());

                    couponActivityRecordDto.setpId(promotionPo!=null? promotionPo.getId():null);
                    couponActivityRecordDto.setPromotionName(promotionPo!=null? promotionPo.getPromotionName():null);
                    couponActivityRecordDto.setQuanifyAmount(promotionPo!=null? promotionPo.getQuanifyAmount():null);
                    couponActivityRecordDto.setPromotionType(promotionPo!=null? promotionPo.getPromotionType():null);
                    couponActivityRecordDto.setStatus(promotionPo!=null? promotionPo.getStatus():null);
                    couponActivityRecordDto.setStartDate(promotionPo!=null? promotionPo.getStartDate():null);
                    couponActivityRecordDto.setEndDate(promotionPo!=null? promotionPo.getEndDate():null);
                    couponActivityRecordDto.setPromotionDiscription(promotionPo!=null? promotionPo.getPromotionDiscription():null);
                    couponActivityRecordDto.setDiscountType(promotionPo!=null? promotionPo.getDiscountType():null);
                    couponActivityRecordDto.setDiscount(promotionPo!=null? promotionPo.getDiscount():null);
                    couponActivityRecordDto.setNote(promotionPo!=null? promotionPo.getNote():null);
                    PromoCategoriesPo promoCategoriesPo = couponActivity.getPromotion().getPromoCategoriesPo();
                    couponActivityRecordDto.setCategoryId(promoCategoriesPo !=null ? promoCategoriesPo.getCategoryId():null);
                    couponActivityRecordDto.setCategoryName(promoCategoriesPo !=null ? promoCategoriesPo.getCategoryName():null);
                    list.add(couponActivityRecordDto);
                }
                response.setResultObject(list);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
            }
        }catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setResultObject(null);
            response.setSuccess(false);
            log.error( ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


//    /**
//     * 选择优惠券列表
//     * @return
//     * @throws PurchaseException
//     * @throws PricingException
//     */
//    public Response<List<SelectCouponsDto>> queryAvailableCouponActivities(String userId, String storeId) {
//        Response<List<SelectCouponsDto>> response = new Response<List<SelectCouponsDto>>();
//        List<SelectCouponsPo> selectCouponsPos = couponActivityService.queryAvailableCouponActivities(storeId);//查询未过期且未使用的优惠券
//        try {
//            ShoppingCart shoppingCart = purchaseService.loadShoppingCart(userId);
//            OrderDto orderDto = shoppingCart.getCurrentOrder();
//            OrderPriceInfoDto orderPriceInfoDto = orderDto.getPriceInfoDto();//购物车价格
//            for(SelectCouponsPo couponsPo : selectCouponsPos){
//                PricingModel pricingModel = pricingEngineService.convertPromotionToPricingModel(couponsPo.getPromotion());
//                Map contentMap = purchaseService.createPriceContentMap(shoppingCart);
//                Map qualifyResult = qualifier.qualifyOrder(orderPriceInfoDto, orderDto, pricingModel, contentMap);
//                if(Boolean.TRUE.equals(qualifyResult.get(qualifyResult))){
//                    couponsPo.setEnabled(true);
//                }else{
//                    couponsPo.setEnabled(false);
//                }
//            }
//        }catch (Exception e){
//            log.error(e.getMessage(), e);
//        }
//        List<SelectCouponsDto> selectCouponsDtos = BeanConvertUtils.convertList(selectCouponsPos,SelectCouponsDto.class);
//        response.setSuccess(true);
//        response.setResultObject(selectCouponsDtos);
//        return response;
//    }


    /**
     * 选择优惠券列表
     * @return
     * @throws PurchaseException
     * @throws PricingException
     */
    @Override
    public Response<PageInfo<CouponActivityRecordDto>> queryAvailableCouponActivities(String userId, String storeId,int pageNum) {
        Response<PageInfo<CouponActivityRecordDto>> response = new Response<PageInfo<CouponActivityRecordDto>>();
        PageInfo<CouponActivityRecord> selectCouponsPos = couponActivityService.queryAvailableCouponActivities(storeId,pageNum);//查询未过期且未使用的优惠券
        List<CouponActivityRecordDto> list = new ArrayList();
        try {
            ShoppingCart shoppingCart = purchaseService.loadShoppingCart(userId);
            OrderDto orderDto = shoppingCart.getCurrentOrder();
            OrderPriceInfoDto orderPriceInfoDto = orderDto.getPriceInfoDto();//购物车价格
            List<String> selectedCouponIds = orderDto.getCouponActivities();
            log.info("selectedCouponIds:{}", selectedCouponIds);
            for(CouponActivityRecord couponsPo : selectCouponsPos.getList()){
                PromotionPo promotionPo = couponsPo.getPromotion();
                log.info("couponsPo:{}", couponsPo.getId());
                CouponActivityRecordDto couponActivityRecordDto = new CouponActivityRecordDto();
                couponActivityRecordDto.setId(couponsPo.getId());
                couponActivityRecordDto.setPromoId(couponsPo.getPromoId());
                couponActivityRecordDto.setStoreId(couponsPo.getStoreId());
                couponActivityRecordDto.setState(couponsPo.getState());

                couponActivityRecordDto.setpId(promotionPo!=null? promotionPo.getId():null);
                couponActivityRecordDto.setPromotionName(promotionPo!=null? promotionPo.getPromotionName():null);
                couponActivityRecordDto.setQuanifyAmount(promotionPo!=null? promotionPo.getQuanifyAmount():null);
                couponActivityRecordDto.setPromotionType(promotionPo!=null? promotionPo.getPromotionType():null);
                couponActivityRecordDto.setStatus(promotionPo!=null? promotionPo.getStatus():null);
                couponActivityRecordDto.setStartDate(promotionPo!=null? promotionPo.getStartDate():null);
                couponActivityRecordDto.setEndDate(promotionPo!=null? promotionPo.getEndDate():null);
                couponActivityRecordDto.setPromotionDiscription(promotionPo!=null? promotionPo.getPromotionDiscription():null);
                couponActivityRecordDto.setDiscountType(promotionPo!=null? promotionPo.getDiscountType():null);
                couponActivityRecordDto.setDiscount(promotionPo!=null? promotionPo.getDiscount():null);
                couponActivityRecordDto.setNote(promotionPo!=null? promotionPo.getNote():null);
                PromoCategoriesPo promoCategoriesPo = couponsPo.getPromotion().getPromoCategoriesPo();
                couponActivityRecordDto.setCategoryId(promoCategoriesPo !=null ? promoCategoriesPo.getCategoryId():null);
                couponActivityRecordDto.setCategoryName(promoCategoriesPo !=null ? promoCategoriesPo.getCategoryName():null);
                if(selectedCouponIds.contains(couponsPo.getId())){
                    couponActivityRecordDto.setEnabled(true);
                }else {
                    PricingModel pricingModel = pricingEngineService.convertPromotionToPricingModel(couponsPo.getPromotion());
                    Map contentMap = purchaseService.createPriceContentMap(shoppingCart);
                    Map qualifyResult = qualifier.qualifyCoupon(orderPriceInfoDto, orderDto, pricingModel, contentMap);
                    log.info("user id:{} coupon: {}, qualifyResult:{}", userId, couponsPo.getPromoId(), qualifyResult);
                    Boolean result = (Boolean) qualifyResult.get("qualifyResult");
                    if (result != null && result.booleanValue()) {
                        log.info("qualifyResult true");
                        couponActivityRecordDto.setEnabled(true);
                    } else {
                        log.info("qualifyResult false");
                        couponActivityRecordDto.setEnabled(false);
                    }
                }
                list.add(couponActivityRecordDto);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        PageInfo<CouponActivityRecordDto> pageInfo = new PageInfo<CouponActivityRecordDto>();
        pageInfo.setOrderBy(selectCouponsPos.getOrderBy());
        pageInfo.setStartRow(selectCouponsPos.getStartRow());
        pageInfo.setEndRow(selectCouponsPos.getEndRow());
        pageInfo.setPages(selectCouponsPos.getPages());
        pageInfo.setFirstPage(selectCouponsPos.getFirstPage());
        pageInfo.setPrePage(selectCouponsPos.getPrePage());
        pageInfo.setNextPage(selectCouponsPos.getNextPage());
        pageInfo.setLastPage(selectCouponsPos.getLastPage());
        pageInfo.setIsFirstPage(selectCouponsPos.isIsFirstPage());
        pageInfo.setIsLastPage(selectCouponsPos.isIsLastPage());
        pageInfo.setHasPreviousPage(selectCouponsPos.isHasNextPage());
        pageInfo.setHasPreviousPage(selectCouponsPos.isHasPreviousPage());
        pageInfo.setPageNum(selectCouponsPos.getPageNum());
        pageInfo.setPageSize(selectCouponsPos.getPageSize());
        pageInfo.setTotal(selectCouponsPos.getTotal());
        pageInfo.setList(list);
        response.setSuccess(true);
        response.setResultObject(pageInfo);
        return response;
    }

    @Override
    public Response<PageResult<CouponsDto>> queryPersonalCoupons(CouponsParamDto couponsParamDto) {
        log.info("------------couponsDubboService-->queryPersonalCoupons(),param:"+ JSONObject.toJSONString(couponsParamDto)+"------------");
        Response<PageResult<CouponsDto>> response=new Response<PageResult<CouponsDto>>();
        PageResult<CouponsDto> pageResult=new PageResult<CouponsDto>();
        List<CouponsDto> couponsDtoList= Lists.newArrayList();
        try {
            //0:验证参数是否为null
            if (null==couponsParamDto){
                throw new RuntimeException("请求参数为null");
            }
            //1:调用service查询数据
            couponsParamDto.setPageNum(
                    null==couponsParamDto.getPageNum()? Integer.valueOf( CommonsEnum.RESPONSE_PAGE_NUM.getCode()) :couponsParamDto.getPageNum());
            couponsParamDto.setPageSize(
                    null==couponsParamDto.getPageSize()? Integer.valueOf( CommonsEnum.RESPONSE_PAGE_SIZE.getCode()) :couponsParamDto.getPageSize());
            CouponsParamPo couponsParamPo=BeanConvertUtils.convert(couponsParamDto,CouponsParamPo.class);
            couponsParamPo.setGrantChannel("personal");
            PageInfo<CouponsPo> pageInfo = couponsService.queryAliveCouponsList(couponsParamPo);
            //2:封装response
            if (pageInfo!=null && pageInfo.getList()!=null && pageInfo.getList().size()>0){
                couponsDtoList=BeanConvertUtils.convertList(pageInfo.getList(),CouponsDto.class);
                
                String[] promotionIds= new String[couponsDtoList.size()];
                for (int i=0;i<couponsDtoList.size();i++){
                	CouponsDto couponsDto = couponsDtoList.get(i);
                	promotionIds[i]=couponsDto.getId();
                }
                List<String> findByPromotionIds = couponActivityService.findByPromotionIds(promotionIds, couponsParamDto.getStoreId());
                List<Map<String, Object>> couponReceiveCount = couponActivityService.getCouponReceiveCount(promotionIds,couponsParamDto.getStoreId());
                //2.1:查询优惠券的领取情况
                for (CouponsDto couponsDto:couponsDtoList){
                    if (couponsDto.getTotalQuantity()-couponsDto.getGrantQty()>0){
                        if(findByPromotionIds.contains(couponsDto.getId())){
                        	Long receiveCount = 0L;
                        	for(Map<String, Object> cr:couponReceiveCount){
                        		String promotion = (String)cr.get("key");
                        		if(promotion!=null&&promotion.equals(couponsDto.getId())){
                        			receiveCount=(Long) cr.get("value");
                        		}
                        	}
                        	if(receiveCount>=couponsDto.getPersonQty()){
                        		couponsDto.setCouPonStatus(2);
                        		continue;
                        	}
                        	 couponsDto.setCouPonStatus(1);
                        }
                        couponsDto.setCouPonStatus(1);
                    }else {
                        couponsDto.setCouPonStatus(0);
                    }
                }
            }
            pageResult.setData(couponsDtoList);
            pageResult.setPageNum(pageInfo.getPageNum());
            pageResult.setPageSize(pageInfo.getPageSize());
            pageResult.setTotal(pageInfo.getTotal());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(pageResult);
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            log.error("------------couponsDubboService-->queryPersonalCoupons(),error:"+ e.toString()+"------------");
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            return response;
        }
    }



    @Override
    public Response<Integer> receiveCoupon(CouponsParamDto couponsParamDto) {
        log.info("------------couponsDubboService-->receiveCoupon(),param:"+ JSONObject.toJSONString(couponsParamDto)+"------------");
        Response<Integer> response=new Response<>();
        //0:验证参数是否为null
        if (null==couponsParamDto){
            throw new RuntimeException("请求参数为null");
        }
        try {
            //1:校验该优惠券是否还可以领取
            CouponsPo couponsPo = couponsService.queryById(couponsParamDto.getId());
            Date date=new Date();
            if ((!"released".equals(couponsPo.getStatus()))||couponsPo.getEndDate()==null||(couponsPo.getEndDate().getTime()-date.getTime()<0)){
				throw new RuntimeException("当前优惠券不支持领取");
			}
			if (couponsPo.getTotalQuantity()-couponsPo.getGrantQty()<0){
                response.setResultObject(0);
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage("该优惠券已抢光");
                response.setSuccess(true);
                return  response;
            }
            //2：校验当前用户是否领取完
            int count = couponsService.selectCountByStoreIdAndPromoId(couponsParamDto.getStoreId(), couponsParamDto.getId());
            if (count>=couponsPo.getPersonQty()){
                response.setResultObject(2);
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage("该优惠券您已达到最大可领取数量");
                response.setSuccess(true);
                return  response;
			}

            //3:领取优惠券
            GrantCouponDto grantCouponDto=new GrantCouponDto();
            String[] storeIds=new String[]{couponsParamDto.getStoreId()};
            String[] promoIds=new String[]{couponsParamDto.getId()};
            grantCouponDto.setPromoIds(promoIds);
            grantCouponDto.setStoreIds(storeIds);
            couponActivityService.grantCoupon(storeIds,promoIds,null);
            response.setErrorMessage("领取成功");
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setResultObject(1);
            return response;
        } catch (Exception e) {
            log.error("------------couponsDubboService-->receiveCoupon(),error:"+ e.toString()+"------------");
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            return response;
        }
    }
    @Override
    public Response<Double> calcCouponValue(String orderId) {
        Order order = orderService.selectByPrimaryKey(orderId);
        if(order == null){
            throw new RuntimeException("订单为空");
        }
        return couponsQueryDubboService.calcCouponAftPay(order.getPriceInfo());
    }

    @Override
    public Response<Double> calcGiveCoupon(Double price, String userId) {
        return couponsQueryDubboService.calcGiveCoupon(price, userId);
    }
}
