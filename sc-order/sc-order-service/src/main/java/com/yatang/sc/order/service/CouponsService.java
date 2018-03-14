package com.yatang.sc.order.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.domain.CouponActivityRecord;
import com.yatang.sc.order.domain.CouponsParamPo;
import com.yatang.sc.order.domain.CouponsPo;

import java.util.Date;
import java.util.List;

/**
 * @description: 优惠券servie
 * @author: yinyuxin
 * @date: 2017/9/19 15:45
 * @version: v1.0
 */
public interface CouponsService {

	void deleteByPrimaryKey(String id);

	void insertSelective(CouponsPo record);

	CouponsPo selectByPrimaryKey(String id);

	void updateByPrimaryKeySelective(CouponsPo record);

	void updateUsedQty(String id);

	void revertUsedQty(String id);
	
	void revertGrantQty(String id);
	/**
	 * 分页查询优惠券列表
	 * @param couponsParamPo
	 * @return
	 */
	PageInfo<CouponsPo> queryCouponsList(CouponsParamPo couponsParamPo);

	/**
	 * 分页查询可发放的优惠券N
	 * @param couponsParamPo
	 * @return
	 */
	PageInfo<CouponsPo> queryAliveCouponsList(CouponsParamPo couponsParamPo);

	/**
	 * 根据id查询优惠券详情
	 * @param id
	 * @return
	 */
	CouponsPo queryById(String id);

	/**
	 * 查询针对某一优惠券，用户已经领取的数量
	 * @param storeId
	 * @param promoId
	 * @return
	 */
	int selectCountByStoreIdAndPromoId(String storeId,String promoId);
	
	/**
	 * 查询返券类型的优惠券
	 * @return
	 */
    List<CouponsPo>  queryToGiveCoupons();

	/*
     *@描述:计算订单等级返券金额
     *@作者:tangqi
     *@时间:2017/11/23 18:22
     */
	Double calcCouponValue(Double total, Double discountRatio);
}
