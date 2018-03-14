package com.yatang.sc.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.dao.CouponActivityDao;
import com.yatang.sc.order.dao.CouponsDao;
import com.yatang.sc.order.domain.CouponActivityRecord;
import com.yatang.sc.order.domain.CouponsParamPo;
import com.yatang.sc.order.domain.CouponsPo;
import com.yatang.sc.order.service.CouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/9/19 17:24
 * @version: v1.0
 */
@Service("couponsService")
@Transactional
public class CouponsServiceImpl implements CouponsService{

	@Autowired
	private CouponsDao couponsDao;
	@Autowired
	private CouponActivityDao couponActivityDao;
	@Autowired
	private CouponsService couponsService;

	@Override
	public void deleteByPrimaryKey(String id) {
		couponsDao.deleteByPrimaryKey(id);
	}



	@Override
	public void insertSelective(CouponsPo record) {
		couponsDao.insertSelective(record);
	}



	@Override
	public CouponsPo selectByPrimaryKey(String id) {
		return couponsDao.selectByPrimaryKey(id);
	}



	@Override
	public void updateByPrimaryKeySelective(CouponsPo record) {
		couponsDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public void updateUsedQty(String id) {
		 couponsDao.updateUsedQty(id);
	}


	@Override
	public PageInfo<CouponsPo> queryCouponsList(CouponsParamPo couponsParamPo) {
		PageHelper.startPage(couponsParamPo.getPageNum(),couponsParamPo.getPageSize());
		return new PageInfo<CouponsPo>(couponsDao.queryList(couponsParamPo));
	}



	@Override
	public PageInfo<CouponsPo> queryAliveCouponsList(CouponsParamPo couponsParamPo) {
		PageHelper.startPage(couponsParamPo.getPageNum(),couponsParamPo.getPageSize());
		return new PageInfo<CouponsPo>(couponsDao.queryAliveCouponsList(couponsParamPo));
	}



	@Override
	public CouponsPo queryById(String id){
		return couponsDao.queryById(id);
	}



	@Override
	public int selectCountByStoreIdAndPromoId(String storeId, String promoId) {
		return couponActivityDao.selectCountByStoreIdAndPromoId(storeId,promoId);
	}



	@Override
	public List<CouponsPo> queryToGiveCoupons() {
		return couponsDao.queryToGiveCoupons();
	}



	@Override
	public void revertUsedQty(String id) {
		couponsDao.revertUsedQty(id);
	}



	@Override
	public void revertGrantQty(String id) {
		couponsDao.revertGrantQty(id);
	}

	@Override
	public Double calcCouponValue(Double total, Double discountRatio) {
		Double realValue = 0.0;
		Double couponValues = total * discountRatio /100;
		List<CouponsPo> couponsPos = couponsService.queryToGiveCoupons();
		if(CollectionUtils.isEmpty(couponsPos)){
			return couponValues;
		}
		for(CouponsPo CouponsPo : couponsPos){
			while (couponValues > CouponsPo.getDiscount().doubleValue()){
				realValue += CouponsPo.getDiscount().doubleValue();
				couponValues -= CouponsPo.getDiscount().doubleValue();
			}
		}
		return realValue;
	}
}
