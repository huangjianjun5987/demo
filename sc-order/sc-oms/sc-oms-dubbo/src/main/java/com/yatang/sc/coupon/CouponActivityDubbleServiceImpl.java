package com.yatang.sc.coupon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.coupon.dubboservice.CouponActivityDubbleService;
import com.yatang.sc.dto.GrantCouponDto;
import com.yatang.sc.order.service.CouponActivityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * <class description>优惠券写服务实现类
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月19日
 */
@Slf4j
@Service("couponActivityDubbleService")
public class CouponActivityDubbleServiceImpl implements CouponActivityDubbleService {
	@Autowired
	private CouponActivityService couponActivityService;



	@Override
	public Response<String> grantCoupon(GrantCouponDto grantCouponDto) {
		Response<String> response = new Response<String>();
		try {
			log.info("调用couponWriteDubbleService.grantCoupon(grantCouponDto)方法，参数为：{}",
					JSON.toJSONString(grantCouponDto));
			couponActivityService.grantCoupon(grantCouponDto.getStoreIds(), grantCouponDto.getPromoIds(),
					grantCouponDto.getUserId());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	@Override
	public Response<String> cancelCoupons(Long[] couponActivityIds, String modifyUser) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<优惠券批量作废>> cancelCoupons(): param="+ JSONObject.toJSONString(couponActivityIds)+ "----------");
		}

		Response<String> response = new Response<String>();

		try {
			// 校验是否已使用
			if (!couponActivityService.checkCouponsState(couponActivityIds)){
				couponActivityService.cancelCoupons(couponActivityIds, modifyUser);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(CommonsEnum.RESPONSE_20200.getName());
			} else {
				response.setCode(CommonsEnum.RESPONSE_20201.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_20201.getName());
				response.setResultObject(CommonsEnum.RESPONSE_20201.getName());
			}

		} catch (Exception e) {
			log.error("---------优惠券批量作废>>cancelCoupons(),error="+ ExceptionUtils.getFullStackTrace(e)+"----------");
			response.setCode(com.yatang.sc.facade.common.CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(com.yatang.sc.facade.common.CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}

	@Override
	public Response<String> grantMutiQtyCoupon(String[] storeIds, String promoId, long qty) {
		Response<String> response = new Response<String>();
		try {
			couponActivityService.grantMutiQtyCoupon(storeIds, promoId, qty);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

}
