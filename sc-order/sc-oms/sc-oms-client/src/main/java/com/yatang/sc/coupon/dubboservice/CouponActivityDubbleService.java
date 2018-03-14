package com.yatang.sc.coupon.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.dto.GrantCouponDto;

/**
 * 
 * <class description>优惠券写服务
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年9月19日
 */
public interface CouponActivityDubbleService {
	/**
	 * 
	 * <method description>发放优惠券
	 *
	 * @param grantCouponDto
	 * @return
	 */
	public Response<String> grantCoupon(GrantCouponDto grantCouponDto);


	/**
	 * @Description: 优惠券批量作废
	 * @author tankejia
	 * @date 2017/10/31- 14:45
	 * @param couponActivityIds
	 */
	public Response<String> cancelCoupons(Long[] couponActivityIds, String modifyUser);

	public Response<String> grantMutiQtyCoupon(String[] storeIds, String promoId, long qty);

}
