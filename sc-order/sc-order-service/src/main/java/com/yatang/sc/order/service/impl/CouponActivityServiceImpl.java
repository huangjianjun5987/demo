package com.yatang.sc.order.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.dao.CouponActivityDao;
import com.yatang.sc.order.dao.CouponsDao;
import com.yatang.sc.order.domain.CouponActivityParamPo;
import com.yatang.sc.order.domain.CouponActivityPo;
import com.yatang.sc.order.domain.CouponActivityRecord;
import com.yatang.sc.order.domain.CouponsPo;
import com.yatang.sc.order.service.CouponActivityService;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/9/19 16:41
 * @version: v1.0
 */
@Service("couponActivityService")
public class CouponActivityServiceImpl implements CouponActivityService {
	private static final Logger	log	= LoggerFactory.getLogger(CouponActivityServiceImpl.class);
	@Value("${zklock.address}")
	private String				zoomKeeperAddr;

	@Autowired
	private CouponActivityDao	couponActivityDao;

	@Autowired
	private CouponsDao			couponsDao;



	@Override
	public void deleteByPrimaryKey(Long id) {
		couponActivityDao.deleteByPrimaryKey(id);
	}



	@Override
	public void insertSelective(CouponActivityPo record) {
		couponActivityDao.insertSelective(record);
	}



	@Override
	public CouponActivityPo selectByPrimaryKey(Long id) {
		return couponActivityDao.selectByPrimaryKey(id);
	}



	@Override
	public void updateByPrimaryKeySelective(CouponActivityPo record) {
		couponActivityDao.updateByPrimaryKeySelective(record);
	}



	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public void grantCoupon(String[] storeIds, String[] promoIds, String userId) {
		log.info("准备发放优惠券，需要发放的门店：{}，需要发放的优惠券：{}，发放人：{}", Arrays.toString(storeIds), Arrays.toString(promoIds), userId);
		for (String promoId : promoIds) {
			// 根据优惠券ID查询优惠券信息
			CouponsPo couponsPo = couponsDao.selectByPrimaryKey(promoId);
			log.info("根据ID={}查询到优惠券结果{}", promoId, JSON.toJSONString(couponsPo));
			if (null == couponsPo) {
				log.error("没有查询到id为【{}】的优惠券", promoId);
				throw new RuntimeException("没有查询到优惠券信息，优惠券ID：" + promoId + ",发放失败");// 不是正常请求，不再继续处理
			}
			// 总数-已发放数量<1,剩余优惠券不足
			if (null == couponsPo.getTotalQuantity() || 0 >= couponsPo.getTotalQuantity()) {
				log.info("id为【{}】的优惠券数量为{},发放失败", promoId, couponsPo.getTotalQuantity());
				throw new RuntimeException("id为" + promoId + "的优惠券数量为" + couponsPo.getTotalQuantity() + ",发放失败");
			}
			if (null != couponsPo.getEndDate() && !(couponsPo.getEndDate().getTime() > new Date().getTime())) {
				log.info("id为【{}】的优惠券有效日期截止期为{},发放失败", promoId, couponsPo.getEndDate());
				throw new RuntimeException(
						"id为" + promoId + "的优惠券有效日期截止期为" + couponsPo.getEndDate().toLocaleString() + ",发放失败");
			}
			// 扣减需要发放的券，扣减失败停止发放
			int result = couponsDao.updateGrantQty(promoId, storeIds.length);
			if (result == 0) {
				throw new RuntimeException("id为：{}券扣减失败，可能余量不足，无法继续发放" + promoId);
			}
			for (String storeId : storeIds) {
				CouponActivityPo couponActivityPo = new CouponActivityPo();
				couponActivityPo.setPromoId(promoId);
				couponActivityPo.setStoreId(storeId);
				couponActivityPo.setState("active");
				couponActivityPo.setActivityDate(new Date());
				couponActivityDao.insert(couponActivityPo);
				log.info("开始发放券：{}", JSON.toJSONString(couponActivityPo));
			}
		}

	}



	@Transactional
	@Override
	public void grantMutiQtyCoupon(String[] storeIds, String promoId, long qty) {
		prehandle(storeIds, promoId, qty);
		for (String storeId : storeIds) {
			grantMutiQtyCoupon(storeId, promoId, qty);
		}
	}



	private void prehandle(String[] storeIds, String promoId, long qty) {
		CouponsPo couponsPo = couponsDao.selectByPrimaryKey(promoId);
		// 校验coupon是否存在
		if (couponsPo == null) {
			log.error("ID为【{}】的优惠券不存在！", promoId);
			throw new RuntimeException("没有查询到优惠券信息，优惠券ID：" + promoId + "，发放失败");
		}
		// 校验时间
		if (couponsPo.getEndDate().getTime() < new Date().getTime()) {
			log.error("ID为【{}】的优惠券已经过期！", promoId);
			throw new RuntimeException("优惠券" + promoId + "已经过期！");
		}
		// 校验数量
		if (couponsPo.getTotalQuantity() - couponsPo.getGrantQty() < storeIds.length * qty) {
			log.info("ID为【{}】的优惠券库存不足", promoId);
			throw new RuntimeException("ID为" + promoId + "的优惠券库存不足");
		}

	}



	public void grantMutiQtyCoupon(String storeId, String promoId, long qty) {

		CouponsPo couponsPo = couponsDao.selectByPrimaryKey(promoId);
		log.info("根据ID={}查询到优惠券结果{}", promoId, JSON.toJSONString(couponsPo));
		if (null == couponsPo) {
			log.error("没有查询到id为【{}】的优惠券", promoId);
			throw new RuntimeException("没有查询到优惠券信息，优惠券ID：" + promoId + ",发放失败");// 不是正常请求，不再继续处理
		}
		if (qty <= 0) {
			return;
		}
		Long grantQty = couponsPo.getGrantQty();// 券的已发放数量，null后处理继续发放
		if (null == couponsPo.getGrantQty()) {
			grantQty = 0L;
		}
		for (int i = 0; i < qty; i++) {
			CouponActivityPo couponActivityPo = new CouponActivityPo();
			couponActivityPo.setPromoId(promoId);
			couponActivityPo.setStoreId(storeId);
			couponActivityPo.setState("active");
			couponActivityPo.setActivityDate(new Date());
			couponActivityDao.insert(couponActivityPo);
			log.info("开始发放券：{}", JSON.toJSONString(couponActivityPo));
		}
		grantQty += qty;
		log.info("券{}发放后剩余数量更新为{}", promoId, grantQty);
		CouponsPo newCouponsPo = new CouponsPo();
		newCouponsPo.setId(couponsPo.getId());
		newCouponsPo.setGrantQty(grantQty);
		couponsDao.updateByPrimaryKeySelective(newCouponsPo);
	}



	@Override
	public PageInfo<CouponActivityPo> queryActivityActiveList(CouponActivityParamPo couponActivityParamPo) {
		PageHelper.startPage(couponActivityParamPo.getPageNum(), couponActivityParamPo.getPageSize());
		return new PageInfo<CouponActivityPo>(couponActivityDao.queryActivityActiveList(couponActivityParamPo));
	}



	@Override
	public void updateStateToUsed(List<String> activityIds) {
		couponActivityDao.updateStateToUsed(activityIds);
	}



	@Override
	public void updateStateToActive(List<String> activityIds) {
		couponActivityDao.updateStateToActive(activityIds);
	}



	/**
	 * @Description: 我的优惠券是否使用
	 * @author kangdong
	 * @param storeId
	 *            门店ID
	 * @param state
	 *            状态
	 * @return
	 */
	@Override
	public List<CouponActivityRecord> queryStoreCouponActivities(String storeId, String state) {
		return couponActivityDao.queryStoreCouponActivities(storeId, state);
	}



	/**
	 * @Description: 已过期的我的优惠券
	 * @author kangdong
	 * @param storeId
	 *            门店ID
	 * @return
	 */
	@Override
	public List<CouponActivityRecord> queryOverdueCouponActivityItems(String storeId) {
		return couponActivityDao.queryOverdueCouponActivityItems(storeId);
	}



	/**
	 * @Description: 已过期的我的优惠券
	 * @param storeId
	 * @return
	 */
	@Override
	public PageInfo<CouponActivityRecord> queryAvailableCouponActivities(String storeId, int pageNum) {
		if(pageNum<=0){
			pageNum=1;
		}
		PageHelper.startPage(pageNum, 10);
		return new PageInfo<CouponActivityRecord>(couponActivityDao.queryAvailableCouponActivities(storeId));
	}



	@Override
	public void cancelCoupons(Long[] couponActivityIds, String modifyUser) {
		couponActivityDao.cancelCoupons(couponActivityIds, modifyUser);
	}



	@Override
	public boolean checkCouponsState(Long[] couponActivityIds) {
		return couponActivityDao.checkCouponsState(couponActivityIds) >= 1;
	}



	@Override
	public List<String> findByPromotionIds(String[] promotionIds, String storeId) {
		return couponActivityDao.findByPromotionIds(promotionIds, storeId);
	}



	@Override
	public List<Map<String,Object>> getCouponReceiveCount(String[] promotionIds, String storeId) {
		return couponActivityDao.getCouponReceiveCount(promotionIds, storeId);
	}
	
	
}
