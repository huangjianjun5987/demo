package com.yatang.sc.coupon.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.dto.CouponsDto;

/**
 * @description: 优惠券 write dubboservice
 * @author: yinyuxin
 * @date: 2017/9/19 18:06
 * @version: v1.0
 */
public interface CouponsWriteDubboService {

	/**
	 * 新增优惠券
	 * @param couponsDto
	 * @return
	 */
	public Response<Boolean> insertCoupons(CouponsDto couponsDto);
}
