package com.yatang.sc.app.purchase.action;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.app.action.AppBaseAction;
import com.yatang.sc.app.res.ActionResponse;
import com.yatang.sc.app.util.BeanConvertExpandUtils;
import com.yatang.sc.app.vo.CommerceItemOrderConfirmVo;
import com.yatang.sc.app.vo.InvoiceInfoVo;
import com.yatang.sc.app.vo.OrderConfirmVo;
import com.yatang.sc.app.vo.OrderPriceInfoVo;
import com.yatang.sc.app.vo.ShippingGroupVo;
import com.yatang.sc.app.vo.imgconfig.AppConfig;
import com.yatang.sc.coupon.dubboservice.CouponsQueryDubboService;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.purchase.dto.AddressDto;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.CouponActivityRecordDto;
import com.yatang.sc.purchase.dto.InvoiceInfoDto;
import com.yatang.sc.purchase.dto.OrderPriceInfoDto;
import com.yatang.sc.purchase.dto.OrderSubmitDto;
import com.yatang.sc.purchase.dto.ShippingGroupDto;
import com.yatang.sc.purchase.dto.ShoppingCart;
import com.yatang.sc.purchase.dto.SignOrderDto;
import com.yatang.sc.purchase.dubboservice.AddressInfoDubboService;
import com.yatang.sc.purchase.dubboservice.CouponsDubboService;
import com.yatang.sc.purchase.dubboservice.PurchaseDubboService;
import com.yatang.sc.purchase.dubboservice.QueryOrderDetailDubboService;
import com.yatang.sc.purchase.enums.InvoiceType;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.region.dto.RegionDTO;
import com.yatang.xc.mbd.biz.region.dubboservice.RegionDubboService;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.pi.solr.dubboservice.ScMobileSearchDubboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusongjie on 2017/7/15.
 */
@Slf4j
@RestController
@RequestMapping(value = "/sc/checkout")
public class OrderCheckOutAction extends AppBaseAction {

    @Autowired
    private PurchaseDubboService purchaseDubboService;

    @Autowired
    private AddressInfoDubboService addressInfoDubboService;

    @Autowired
    RedisAdapterServie<String, String> redisDubboServie;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RegionDubboService regionDubboService;

    @Autowired
    AppConfig appConfig;

    @Autowired
    private OrderFulfillerDubboService mOrderFulfillerDubboService;

    @Autowired
    private CouponsDubboService couponsDubboService;

    @Autowired
    private ScMobileSearchDubboService scMobileSearchDubboService;

    /**
     * 加载确认订单页面
     * @param request
     * @return
     */
    @RequestMapping(value="index",method= RequestMethod.GET)
    public Response<OrderConfirmVo> index(HttpServletRequest request){
        Response<OrderConfirmVo> result = new Response<OrderConfirmVo>();
        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            log.warn("The confirmed order page init. userId:{}", userId);
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误！");
            return ActionResponse.wrap(result);
        }

        String storeId = getStoreId(request);
        log.info("userId:{},storeId:{}", userId, storeId);
        Response<StoreDto> storeRes = organizationService.queryStoreById(storeId);
        if (storeRes == null || !storeRes.isSuccess()) {
            log.error("userId:{},storeId:{},storeRes is null", userId, storeId);
            result.setSuccess(false);
            result.setErrorMessage("门店不存在！");
            return ActionResponse.wrap(result);
        }
        log.info("storeId:{},storeRes:{}", storeId, JSON.toJSONString(storeRes.getResultObject()));

        AddressDto addressDto = this.convertToAddress(storeRes.getResultObject());
        Response<ShoppingCart>  shoppongCartResp = purchaseDubboService.checkout(userId, addressDto);
        if(shoppongCartResp.isSuccess()){
            result.setSuccess(true);
            result.setResultObject(this.convertToOrderFirmVo(shoppongCartResp.getResultObject()));
            //获取会员等级返券金额
            /*OrderPriceInfoVo priceInfoVo = result.getResultObject().getOrderPriceInfoVo();
            Response<Double> response1 = couponsDubboService.calcGiveCoupon(priceInfoVo.getAmount(), userId);
            priceInfoVo.setGiveAmount(response1.getResultObject());*/
        }else {
            result.setSuccess(false);
            result.setErrorMessage("应用地址失败！");
        }
        log.info("result:{}",JSON.toJSONString(result));
        return ActionResponse.wrap(result);
    }

    /**
     * 将选中的地址应用到购物车上
     * @param request, addressDto
     * @return
     */
    @RequestMapping(value="applyShippingAddress",method= RequestMethod.POST)
    public Response<OrderConfirmVo> applyShippingAddress(@RequestBody AddressDto addressDto, HttpServletRequest request){
        Response<OrderConfirmVo> result = new Response<OrderConfirmVo>();
        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误");
            return ActionResponse.wrap(result);
        }

        Response<ShoppingCart>  shoppongCartResp = purchaseDubboService.applyShippingAddress(userId, addressDto);
        if(shoppongCartResp.isSuccess()){
            result.setSuccess(true);
            result.setResultObject(this.convertToOrderFirmVo(shoppongCartResp.getResultObject()));
        }else {
            result.setSuccess(false);
            result.setErrorMessage("应用地址失败！");
        }
        return ActionResponse.wrap(result);
    }

    /**
     * 获取当前用户的所有门店地址
     * @param request
     * @return
     */
    @RequestMapping(value="getShippingAddresses",method= RequestMethod.GET)
    public Response<List<AddressDto>> getShippingAddresses(HttpServletRequest request){
        Response<List<AddressDto>> result = new Response<List<AddressDto>>();
        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误!");
            return ActionResponse.wrap(result);
        }

        Response<List<AddressDto>> addressInfoResp = addressInfoDubboService.queryAddressInfoByFranchiseeId(userId);
        if(addressInfoResp.isSuccess()){
            result.setSuccess(true);
            result.setResultObject(addressInfoResp.getResultObject());
        } else {
            result.setSuccess(false);
            result.setErrorMessage("获取地址信息失败");
        }
        return ActionResponse.wrap(result);
    }

    /**
     * 新增或更新发票信息
     * @param request, invoiceInfoDto
     * @return
     */
    @RequestMapping(value="updateInvoiceInfo",method= RequestMethod.POST)
    public Response<OrderConfirmVo> updateInvoiceInfo(@RequestBody InvoiceInfoDto invoiceInfoDto, HttpServletRequest request){
        Response<OrderConfirmVo> result = new Response<OrderConfirmVo>();

        result = this.validateInvoice(invoiceInfoDto);
        if(!result.isSuccess()){
            return ActionResponse.wrap(result);
        }

        String userId = getUserId(request);
        if(StringUtils.isEmpty(userId)){
            result.setSuccess(false);
            result.setErrorMessage("用户信息错误");
            return ActionResponse.wrap(result);
        }

        Response<ShoppingCart> shoppingCartResp = purchaseDubboService.updateInvoiceInfo(userId, invoiceInfoDto);
        if(shoppingCartResp.isSuccess()){
            result.setSuccess(true);
            result.setResultObject(this.convertToOrderFirmVo(shoppingCartResp.getResultObject()));
        } else {
            result.setSuccess(false);
            result.setErrorMessage("发票信息保存失败");
        }
        return ActionResponse.wrap(result);
    }


    @RequestMapping(value = "applyCoupon")
    public Response<OrderConfirmVo> applyCoupon(String couponActivityIds, HttpServletRequest request){

        Response<OrderConfirmVo> response = new Response<OrderConfirmVo>();
        if(StringUtils.isEmpty(couponActivityIds)){
            couponActivityIds = request.getParameter("couponActivityIds");
        }
        log.info("{}->apply coupon:{}", getUserId(), couponActivityIds);
        List<String> couponActivityList = new ArrayList<String>();
        if(!StringUtils.isEmpty(couponActivityIds) ){
            String[] couponActivityIdArr = couponActivityIds.split(",");
            couponActivityList = CollectionUtils.arrayToList(couponActivityIdArr);
        }
        Response<ShoppingCart> result = purchaseDubboService.applyCoupons( getUserId(), couponActivityList);
        if(result.isSuccess()){
            response.setSuccess(true);
            response.setResultObject(this.convertToOrderFirmVo(result.getResultObject()));
        } else {
            response.setSuccess(false);
            response.setErrorMessage("发票信息保存失败");
        }
        return ActionResponse.wrap(response);
    }



    /**
     * 提交订单
     * @param request
     * @return
     */
    @RequestMapping(value="submitOrder",method= RequestMethod.POST)
    public Response<OrderSubmitDto> submitOrder(HttpServletRequest request,@RequestBody Map requestMap){
    	boolean autoRemoveGiftItem=false;
    	if(requestMap!=null&&requestMap.get("autoRemoveGiftItem")!=null){
    		autoRemoveGiftItem=(boolean) requestMap.get("autoRemoveGiftItem");
    	}
        String userId = getUserId(request);
        Response<OrderSubmitDto> result = purchaseDubboService.commitOrder(userId, autoRemoveGiftItem);
        if (!result.isSuccess() && CommonsEnum.RESPONSE_10042.getCode().equalsIgnoreCase(result.getCode())) {
            return result;
        }
        return ActionResponse.wrap(result);
    }

    /**
     * 签收订单
     * @param request
     * @return
     */
    @RequestMapping(value="signOrder",method= RequestMethod.POST)
    public Response<Boolean> signOrder(@RequestBody SignOrderDto signOrderDto, HttpServletRequest request){
        OrderReceiveDto orderReceiveDto = new OrderReceiveDto();
        orderReceiveDto.setOrderId(signOrderDto.getOrderId());
        orderReceiveDto.setOperatorId(getUserId(request));
        Response<Boolean> result = mOrderFulfillerDubboService.orderReceive(orderReceiveDto);
        return ActionResponse.wrap(result);
    }

    private Response<OrderConfirmVo> validateInvoice(InvoiceInfoDto invoiceInfoDto) {
        Response<OrderConfirmVo> result = new Response<OrderConfirmVo>();
        String invoiceType = invoiceInfoDto.getInvoiceType();

        if(InvoiceType.valueOf("valueAdded").getValue().equals(invoiceType)){
            if(StringUtils.isEmpty(invoiceInfoDto.getCompanyName())){
                result.setErrorMessage("请输入公司名称");
                result.setSuccess(false);
            } else if (StringUtils.isEmpty(invoiceInfoDto.getTaxpayerIdentificationNumber())) {
                result.setErrorMessage("请输入纳税人识别码");
                result.setSuccess(false);
            } else if (StringUtils.isEmpty(invoiceInfoDto.getRegisteredAddress())) {
                result.setErrorMessage("请输入注册地址");
                result.setSuccess(false);
            } else if (StringUtils.isEmpty(invoiceInfoDto.getCompanyPhone())) {
                result.setErrorMessage("请输入公司电话");
                result.setSuccess(false);
            } else if (StringUtils.isEmpty(invoiceInfoDto.getDepositBank())) {
                result.setErrorMessage("请输入开户银行");
                result.setSuccess(false);
            } else if (invoiceInfoDto.getAccountNumber() == null) {
                result.setErrorMessage("请输入开户账号");
                result.setSuccess(false);
            } else {
                result.setSuccess(true);
            }
        } else if (InvoiceType.valueOf("common").getValue().equals(invoiceType)){
            if(StringUtils.isEmpty(invoiceInfoDto.getInvoiceTitle())){
                result.setSuccess(false);
                result.setErrorMessage("请输入发票抬头");
            } else {
                result.setSuccess(true);
            }
        } else {
            result.setSuccess(true);
        }
        return result;
    }

    private AddressDto convertToAddress(StoreDto store) {
        List<String> codes = new ArrayList<String>();
        List<RegionDTO> regionDTOs = new ArrayList<RegionDTO>();
        AddressDto addressDto = new AddressDto();

//        String proviceId = store.getProvinceId();
//        String cityId = store.getCityId();
//        String district = store.getDistrictId();
//        codes.add(proviceId);
//        codes.add(cityId);
//        codes.add(district);
//        Response<List<RegionDTO>> regionResp = regionDubboService.selectRegionListByMultiCode(codes);
//        if (regionResp.isSuccess()) {
//            regionDTOs = regionResp.getResultObject();
//        } else {
//            regionResp.setErrorMessage("获取省市区失败！");
//        }
//
//        for (RegionDTO regionDTO : regionDTOs) {
//            if (proviceId.equals(regionDTO.getCode())) {
//                addressDto.setProvince(regionDTO.getRegionName());
//            } else if (cityId.equals(regionDTO.getCode())) {
//                addressDto.setCity(regionDTO.getRegionName());
//            } else if (district.equals(regionDTO.getCode())) {
//                addressDto.setDistrict(regionDTO.getRegionName());
//            }
//        }
        addressDto.setProvince(store.getProvinceName());
        addressDto.setCity(store.getCityName());
        addressDto.setDistrict(store.getDistrictName());
        addressDto.setDetailAddress(store.getAddress());
        addressDto.setConsigneeName(store.getContact());
        String phoneNumber = store.getMobilePhone();
//        if(StringUtils.isEmpty(phoneNumber)){
//            phoneNumber = store.getPhone();
//        }
        addressDto.setPhoneNumber(phoneNumber);

        return addressDto;
    }

    private OrderConfirmVo convertToOrderFirmVo(ShoppingCart shoppingCart) {
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        List<CommerceItemOrderConfirmVo> commerceItemOrderConfirmVos = new ArrayList<CommerceItemOrderConfirmVo>();
        List<CommerceItemOrderConfirmVo> giftItemVos = new ArrayList<CommerceItemOrderConfirmVo>();
        ShippingGroupVo shippingGroupVo = this.convertToShippingGroupVo(shoppingCart.getCurrentOrder().getShippingGroupDto());
        InvoiceInfoVo invoiceInfoVo = BeanConvertExpandUtils.convertMoreParam(InvoiceInfoVo.class, shoppingCart.getCurrentOrder().getInvoiceInfoDto());
        OrderPriceInfoVo orderPriceInfoVo = this.convertToOrderPriceInfoVo(shoppingCart.getCurrentOrder().getPriceInfoDto());
        List<CommerceItemDto> items = shoppingCart.getCurrentOrder().getItems();
        for (CommerceItemDto item : items) {
            if (item.isSelected()) {
                CommerceItemOrderConfirmVo commerceItemOrderConfirmVo = this.convertToCommerceItemOrderConfirmVo(item);
                if(CommerceItemTypes.PROMOTION.equals(item.getType())){
                    giftItemVos.add(commerceItemOrderConfirmVo);
                } else {
                    commerceItemOrderConfirmVos.add(commerceItemOrderConfirmVo);
                }
            }
        }
        orderConfirmVo.setShippingGroupVo(shippingGroupVo);
        orderConfirmVo.setCommerceItemVos(commerceItemOrderConfirmVos);
        orderConfirmVo.setGiftItemVos(giftItemVos);
        orderConfirmVo.setInvoiceInfoVo(invoiceInfoVo);
        orderConfirmVo.setOrderPriceInfoVo(orderPriceInfoVo);
        orderConfirmVo.setLastOrderId(shoppingCart.getLastOrderId());
        orderConfirmVo.setSelectedCouponActivities(shoppingCart.getCurrentOrder().getCouponActivities());
        log.info("orderConfirmVo coupons:{}", orderConfirmVo.getSelectedCouponActivities());
        return orderConfirmVo;
    }

    private CommerceItemOrderConfirmVo convertToCommerceItemOrderConfirmVo(CommerceItemDto item) {
        CommerceItemOrderConfirmVo commerceItemOrderConfirmVo = new CommerceItemOrderConfirmVo();
        commerceItemOrderConfirmVo.setProductImg(appConfig.getAppImageUrl( item.getProductImg()));
        commerceItemOrderConfirmVo.setProductName(item.getProductName());
        commerceItemOrderConfirmVo.setProperties(item.getProperties());
        commerceItemOrderConfirmVo.setQuantity(item.getSaleQuantity());
        commerceItemOrderConfirmVo.setSkuId(item.getSkuId());
        commerceItemOrderConfirmVo.setAmount(item.getItemPrice().getAmount());
        commerceItemOrderConfirmVo.setSalePrice(item.getSaleUnitPrice());
        commerceItemOrderConfirmVo.setListPrice(item.getListUnitPrice());
        commerceItemOrderConfirmVo.setAvailable(item.isAvailable());
        commerceItemOrderConfirmVo.setSellFullCase(item.getSellFullCase());
        commerceItemOrderConfirmVo.setSaleQuantity(item.getSaleQuantity());
        commerceItemOrderConfirmVo.setUnitQuantity(item.getUnitQuantity());
        commerceItemOrderConfirmVo.setType(item.getType());
        if(CommerceItemTypes.BUNDLE.equals(item.getType())){
            List<CommerceItemDto> subItems = item.getSubItems();
            List<CommerceItemOrderConfirmVo> subItemVos = new ArrayList<CommerceItemOrderConfirmVo>();
            for(CommerceItemDto subItem : subItems){
                CommerceItemOrderConfirmVo subItemVo =  convertToCommerceItemOrderConfirmVo(subItem);
                subItemVos.add(subItemVo);
            }
            commerceItemOrderConfirmVo.setSubItems(subItemVos);
        }
        return commerceItemOrderConfirmVo;
    }

    private OrderPriceInfoVo convertToOrderPriceInfoVo(OrderPriceInfoDto priceInfoDto) {
        OrderPriceInfoVo orderPriceInfoVo = new OrderPriceInfoVo();
        orderPriceInfoVo.setAmount(priceInfoDto.getAmount());
        orderPriceInfoVo.setRawSubtotal(priceInfoDto.getRawSubtotal());//未执行折扣前的静额
        orderPriceInfoVo.setDiscountAmount(priceInfoDto.getDiscountAmount());
        orderPriceInfoVo.setShipping(priceInfoDto.getShipping());
        orderPriceInfoVo.setTotal(priceInfoDto.getTotal());
        orderPriceInfoVo.setUserDiscountAmount(priceInfoDto.getUserDiscountAmount());
        orderPriceInfoVo.setCouponDiscountAmount(priceInfoDto.getCouponDiscountAmount());
        return orderPriceInfoVo;
    }

    private ShippingGroupVo convertToShippingGroupVo(ShippingGroupDto shippingGroupDto) {
        ShippingGroupVo shippingGroupVo = new ShippingGroupVo();
        shippingGroupVo.setCellphone(shippingGroupDto.getCellphone());
        shippingGroupVo.setCity(shippingGroupDto.getCity());
        shippingGroupVo.setConsigneeName(shippingGroupDto.getConsigneeName());
        shippingGroupVo.setDetailAddress(shippingGroupDto.getDetailAddress());
        shippingGroupVo.setProvince(shippingGroupDto.getProvince());
        shippingGroupVo.setDistrict(shippingGroupDto.getDistrict());
        return  shippingGroupVo;
    }

    /**
     * 我的优惠券领取记录
     * @return
     */
    @RequestMapping(value="queryMyCouponActivityItems", method= RequestMethod.GET)
    private Response<List<CouponActivityRecordDto>> queryMyCouponActivityItems(@RequestParam String key, HttpServletRequest request) {
        Response<List<CouponActivityRecordDto>> couponActivities = new Response<>() ;
        String storeId = getStoreId(request);
        log.info("storeId:{},key:{}",  storeId,key);
        List<String> categoryIds = new ArrayList<>();
        if(key.equals( "1")){
            //未使用优惠券领取记录
            couponActivities = couponsDubboService.queryStoreCouponActivities(storeId,"active");
        }else if(key.equals("2")) {
            //已使用优惠券领取记录
            couponActivities = couponsDubboService.queryStoreCouponActivities(storeId,"used");
        }else {
            //已过期的我的优惠券领取记录
            couponActivities = couponsDubboService.queryOverdueCouponActivityItems(storeId);
        }
        List<CouponActivityRecordDto> coupons = couponActivities.getResultObject();
        if(!CollectionUtils.isEmpty(coupons)){
            for(CouponActivityRecordDto coupon:coupons) {
                categoryIds.add(coupon.getCategoryId());
            }
            if(categoryIds.size()>0) {
                Response<Map<String, String>> response = scMobileSearchDubboService.queryDimValIdMapByCategoryIds(categoryIds);
                Map<String, String> result = response.getResultObject();
                for(CouponActivityRecordDto coupon:coupons) {
                    coupon.setDiscountType(result.get(coupon.getCategoryId()));
                }
            }
        }

        return couponActivities;
    }

    /**
     * 选择可用优惠券列表
     * @return
     */
    @RequestMapping(value="queryAvailableCouponActivities", method= RequestMethod.GET)
    Response<Map<String,Object>> queryAvailableCouponActivities(HttpServletRequest request,@RequestParam int pageNum) {
        String userId = getUserId(request);
        String storeId = getStoreId(request);
        log.info("userId:{},storeId:{}",  userId,storeId);
        Response<Map<String,Object>> response = new Response<>() ;
        Map<String,Object> responseMapResult=new HashMap<>();
        Response<PageInfo<CouponActivityRecordDto>> selectCouponsDto = couponsDubboService.queryAvailableCouponActivities(userId, storeId,pageNum);
        responseMapResult.put("availableCoupons", selectCouponsDto.getResultObject());
        //获取是否有不能叠加的促销
        Response<ShoppingCart>  useCouponResponse =purchaseDubboService.loadShoppingCart(userId);
        ShoppingCart cart=useCouponResponse.getResultObject();
        boolean rHasSuperposePromotion = cart.getCurrentOrder().isHasNotSuperposedPromotion();
        boolean rUseCoupon = cart.getCurrentOrder().isUseCoupon();
        responseMapResult.put("useCoupon", rUseCoupon);
        responseMapResult.put("hasNotSuperposedPromotion", rHasSuperposePromotion);
        response.setResultObject(responseMapResult);
        return response;
    }

    /*
     *@描述:获取结算前的会员等级返券金额
     *@作者:tangqi
     *@时间:2017/11/23 19:04
     */
    /*@RequestMapping(value="calcCouponValue", method= RequestMethod.GET)
    public Response<Double> calcCouponValue(String orderId){
        String userId = getUserId();
        Response<Double> response = couponsDubboService.calcCouponValue(orderId, userId);
        return response;
    }*/

}

