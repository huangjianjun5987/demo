package com.yatang.sc.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.order.domain.CouponActivityParamPo;
import com.yatang.sc.order.domain.CouponActivityPo;
import com.yatang.sc.order.domain.CouponActivityRecord;

public interface CouponActivityDao {
	int deleteByPrimaryKey(Long id);



	int insert(CouponActivityPo record);



	int insertSelective(CouponActivityPo record);



	CouponActivityPo selectByPrimaryKey(Long id);



	int updateByPrimaryKeySelective(CouponActivityPo record);



	int updateByPrimaryKey(CouponActivityPo record);


	int updateStateToUsed(@Param("activityIds")List<String> activityIds);

	int updateStateToActive(@Param("activityIds")List<String> activityIds);
	
	int selectCountByStoreIdAndPromoId(@Param("storeId") String storeId, @Param("promoId") String promoId);

	/**
	 * 查询未使用(或作废记录)的已领取优惠券
	 * @param couponActivityParamPo
	 * @return
	 */
	List<CouponActivityPo> queryActivityActiveList(CouponActivityParamPo couponActivityParamPo);
	
	/**
	 * 通过促销ID查询已领取的优惠券
	 * @param promotionIds
	 * @return
	 */
	List<String> findByPromotionIds(@Param("promotionIds") String[] promotionIds ,@Param("storeId")String storeId);
	
	/**
	 * 查询门店优惠券领用次数
	 * @param promotionIds
	 * @return
	 */
	List<Map<String,Object>> getCouponReceiveCount(@Param("promotionIds") String[] promotionIds ,@Param("storeId")String storeId);

	
	/**
	 * @Description: 我的优惠券是否使用
	 * @author kangdong
	 * @param storeId
	 * @param state
	 * @return
	 */
    List<CouponActivityRecord> queryStoreCouponActivities(@Param("storeId") String storeId, @Param("state") String state);

	/**
	 * @Description: 已过期的我的优惠券领取记录
	 * @author kangdong
	 * @param storeId
	 * @return
	 */
	List<CouponActivityRecord> queryOverdueCouponActivityItems(@Param("storeId") String storeId);


	/**
	 * @Description: 选择优惠券列表
	 * @param storeId
	 * @return
	 */
    List<CouponActivityRecord> queryAvailableCouponActivities(@Param("storeId") String storeId);

    /**
     * @Description: 优惠券批量作废
     * @author tankejia
     * @date 2017/10/31- 14:45
     * @param ids
     */
	int cancelCoupons(@Param("ids") Long[] ids, @Param("modifyUser")String modifyUser);

	/**
	 * @Description: 校验优惠券是否已使用
	 * @author tankejia
	 * @date 2017/10/31- 15:39
	 * @param ids
	 */
	int checkCouponsState(@Param("ids") Long[] ids);
}