package com.yatang.sc.order.dao;

import java.util.List;

import com.yatang.sc.order.domain.AvailableCouponActivityPo;
import com.yatang.sc.order.domain.PromoQueryConditionPo;
import com.yatang.sc.order.domain.PromotionPo;
import org.apache.ibatis.annotations.Param;

public interface PromotionDao {

	PromotionPo queryById(String id);



	int deleteByPrimaryKey(String id);



	int insert(PromotionPo po);



	int updateByPrimaryKey(PromotionPo po);



	/**
	 * @Description: 根据条件查询促销列表
	 * @author tankejia
	 * @date 2017/9/4- 19:22
	 * @param po
	 */
	List<PromotionPo> listPromotions(PromoQueryConditionPo po);



	/**
	 * @Description: 查询系统时间可用促销列表
	 * @author tankejia
	 * @date 2017/9/5- 16:54
	 */
	List<PromotionPo> listAvailablePromotions(String promoType);



	/**
	 * 
	 * <method description>根据门店ID查询门店当前可用的所有优惠券
	 *
	 * @param storeId
	 * @return
	 */
	List<AvailableCouponActivityPo> queryAvailableCouponActivity(String storeId);

	/**
	 * @Description: 查询已过期的优惠券
	 * @param id
	 * @return
	 */
	List<PromotionPo> queryOverdueInfo(String id);

	/**
	 * @Description: 查询未过期的优惠券
	 * @param id
	 * @return
	 */
	List<PromotionPo> queryNotOverdueInfo(String id);

	/**
	 * 查询指定范围内可以使用的活动
	 * @param branchCompanyId
	 * @param promoType
	 * @return
	 */
	List<PromotionPo> queryAvailablePromotions(@Param("branchCompanyId") String branchCompanyId, @Param("promoType") String promoType);

	/**
	 * 批量修改活动或者优惠券的状态
	 * @param ids
	 * @return
	 */
	int batchUpdatePromoStatus(@Param("ids") String[] ids,@Param("status") String status);
}
