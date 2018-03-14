package com.yatang.sc.order.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.domain.CouponActivityParamPo;
import com.yatang.sc.order.domain.CouponActivityPo;
import com.yatang.sc.order.domain.CouponActivityRecord;

/**
 * @description: 优惠券领取记录service
 * @author: yinyuxin
 * @date: 2017/9/19 15:45
 * @version: v1.0
 */
public interface CouponActivityService {

	void deleteByPrimaryKey(Long id);



	void insertSelective(CouponActivityPo record);



	CouponActivityPo selectByPrimaryKey(Long id);



	void updateByPrimaryKeySelective(CouponActivityPo record);



	void grantCoupon(String[] storeIds, String[] promoIds, String userId);



	void grantMutiQtyCoupon(String storeId, String promoId, long qty);



	void grantMutiQtyCoupon(String[] storeIds, String promoId, long qty);



	/**
	 * 查询未使用(或作废记录)的已领取优惠券
	 * 
	 * @param couponActivityParamPo
	 * @return
	 */
	PageInfo<CouponActivityPo> queryActivityActiveList(CouponActivityParamPo couponActivityParamPo);



	void updateStateToUsed(List<String> activityIds);



	/**
	 * 将优惠券状态设置成激活状态
	 * 
	 * @param activityIds
	 */
	void updateStateToActive(List<String> activityIds);



	/**
	 * @Description: 我的优惠券是否使用
	 * @param storeId
	 * @param state
	 * @return
	 */
	List<CouponActivityRecord> queryStoreCouponActivities(String storeId, String state);



	/**
	 * @Description: 已过期的我的优惠券领取记录
	 * @param storeId
	 * @return
	 */
	List<CouponActivityRecord> queryOverdueCouponActivityItems(String storeId);



	/**
	 * @Description: 选择优惠券列表
	 * @param storeId
	 * @return
	 */
	PageInfo<CouponActivityRecord> queryAvailableCouponActivities(String storeId, int pageNum);



	/**
	 * @Description: 优惠券批量作废
	 * @author tankejia
	 * @date 2017/10/31- 14:45
	 * @param couponActivityIds
	 */
	void cancelCoupons(Long[] couponActivityIds, String modifyUser);



	/**
	 * @Description: 校验优惠券是否已使用
	 * @author tankejia
	 * @date 2017/10/31- 15:39
	 * @param couponActivityIds
	 */
	boolean checkCouponsState(Long[] couponActivityIds);


	
	
	/**
	 * 通过促销ID查询已领取的优惠券
	 * @param promotionIds
	 * @return
	 */
	List<String> findByPromotionIds(String[] promotionIds,String storeId);

	
	/**
	 * 查询门店优惠券领用次数
	 * @param promotionIds
	 * @return
	 */
	List<Map<String, Object>> getCouponReceiveCount(String[] promotionIds ,String storeId);



	

}
