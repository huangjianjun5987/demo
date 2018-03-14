package com.yatang.sc.coupon.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.CouponActivityDto;
import com.yatang.sc.dto.CouponActivityParamDto;
import com.yatang.sc.dto.CouponRecordDto;
import com.yatang.sc.dto.CouponRecordParamDto;
import com.yatang.sc.dto.CouponsDto;
import com.yatang.sc.dto.CouponsParamDto;

/**
 * @description: 优惠券 query dubboservice
 * @author: yinyuxin
 * @date: 2017/9/19 20:28
 * @version: v1.0
 */
public interface CouponsQueryDubboService {

	/**
	 * 根据id查询优惠券
	 * @param id
	 * @return
	 */
	public Response<CouponsDto> queryCouponsById(String id);

	/**
	 * 查询优惠券列表
	 * @param couponsParamDto
	 * @return
	 */
	public Response<PageResult<CouponsDto>> queryCouponsList(CouponsParamDto couponsParamDto);

	/**
	 * 查询可发放的优惠券
	 * @param couponsParamDto
	 * @return
	 */
	public Response<PageResult<CouponsDto>> queryAliveCouponsList(CouponsParamDto couponsParamDto);

	/**
	 * 查询参与数据（已使用）
	 * @param couponRecordParamDto
	 * @return
	 */
	public Response<PageResult<CouponRecordDto>> queryCouponRecordList(CouponRecordParamDto couponRecordParamDto);

	/**
	 * 未使用(或作废)的已激活的参与数据
	 * @param couponActivityParamDto
	 * @return
	 */
	public Response<PageResult<CouponActivityDto>> queryCouponActivityActiveList(CouponActivityParamDto couponActivityParamDto);

	/**
	 * 查询返券类型的优惠券
	 * @return
	 */
    List<CouponsDto>  queryToGiveCoupons();

	/*
	 *@描述:结算后返券金额
	 *@作者:tangqi
	 *@时间:2017/11/24 14:48
	 */
	Response<Double> calcCouponAftPay(Long priceId);

	/*
	 *@描述:计算展示等级返券金额
	 *@作者:tangqi
	 *@时间:2017/11/30 17:39
	 */
	Response<Double> calcGiveCoupon(Double price, String userId);
}
