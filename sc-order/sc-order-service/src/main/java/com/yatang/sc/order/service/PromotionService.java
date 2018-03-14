package com.yatang.sc.order.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.domain.AvailableCouponActivityPo;
import com.yatang.sc.order.domain.PromoCompaniesPo;
import com.yatang.sc.order.domain.PromoQueryConditionPo;
import com.yatang.sc.order.domain.PromoRecordsPo;
import com.yatang.sc.order.domain.PromotionPo;

/**
 * @描述: demo
 * @作者: tankejia
 * @创建时间: 2017/9/4-15:45 .
 * @版本: 1.0 .
 */
public interface PromotionService {

	/**
	 * @Description: 多条件查询活动列表分页显示
	 * @author tankejia
	 * @date 2017/9/4- 14:10
	 * @param record
	 */
	PageInfo<PromotionPo> listPromotions(PromoQueryConditionPo record);



	/**
	 * @Description: 查询系统时间可用促销列表
	 * @author tankejia
	 * @date 2017/9/5- 16:54
	 */
	List<PromotionPo> listAvailablePromotions(String promoType);



	/**
	 * @Description: 根据活动id查询促销范围列表接口
	 * @author tankejia
	 * @date 2017/9/4- 15:17
	 * @param promoId
	 */
	List<PromoCompaniesPo> listBranchCompany(String promoId);



	/**
	 * @Description: 新增促销活动
	 * @author tankejia
	 * @date 2017/9/4- 15:26
	 * @param po
	 */
	Boolean insertPromotion(PromotionPo po);



	/**
	 * @Description: 根据活动编号或订单编号查询促销订单记录
	 * @author tankejia
	 * @date 2017/9/6- 14:06
	 * @param promoId,orderId
	 */
	List<PromoRecordsPo> queryByPromoOrOrderId(String promoId, String orderId);



	/**
	 * @Description: 新增促销活动订单记录
	 * @author tankejia
	 * @date 2017/9/6- 14:11
	 * @param record
	 */
	Boolean insertPromoOrderRecord(PromoRecordsPo record);



	/**
	 * @Description: 发布/关闭促销活动
	 * @author tankejia
	 * @date 2017/9/6- 16:12
	 * @param record
	 */
	Boolean updatePromoStatus(PromotionPo record);



	/**
	 * @Description: 查询活动详情
	 * @author tankejia
	 * @date 2017/9/6- 16:12
	 * @param promotionId
	 */
	PromotionPo queryPromotionDetail(String promotionId);



	/**
	 * 获取参与数据的总条数
	 * 
	 * @param queryParticipateDataMap
	 * @return
	 */
	long getParticipateDataPageListCount(Map<String, Object> queryParticipateDataMap);



	/**
	 * 分页查询参与数据
	 * 
	 * @param queryParticipateDataMap
	 * @return
	 */
	List<Map<String, String>> getParticipateDataPageList(Map<String, Object> queryParticipateDataMap);



	/**
	 * 根据id查询促销
	 * 
	 * @param id
	 * @return
	 */
	PromotionPo queryById(String id);



	/**
	 * 
	 * <method description>根据门店ID查询门店当前可用的所有优惠券
	 *
	 * @param storeId
	 * @return
	 */
	List<AvailableCouponActivityPo> queryAvailableCouponActivity(String storeId);


	/**
	 * 查询指定范围可使用的促销
	 * @param branchCompanyId
	 * @param promoType
	 * @return
	 */
	List<PromotionPo> queryAvailablePromotions(String branchCompanyId, String promoType);

	/**
	 * @Description: 查询活动详情页修改关联的门店Id
	 * @author zhudanning
	 * @date 2017/12/14- 16:12
	 * @param record
	 */
	Boolean updateStoreId(PromotionPo record);

	/**
	 * 批量修改活动或者优惠券的状态
	 * @param couponPromotionIds
	 * @return
	 */
	Boolean batchUpdatePromoStatus(String[] couponPromotionIds,String status);
}
