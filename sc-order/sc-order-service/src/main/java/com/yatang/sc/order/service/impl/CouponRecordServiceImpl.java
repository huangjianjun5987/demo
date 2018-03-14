package com.yatang.sc.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.dao.CouponRecordDao;
import com.yatang.sc.order.domain.CouponRecordDetailPo;
import com.yatang.sc.order.domain.CouponRecordParamPo;
import com.yatang.sc.order.domain.CouponRecordPo;
import com.yatang.sc.order.service.CouponRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/9/19 17:23
 * @version: v1.0
 */
@Service("couponRecordService")
@Transactional
public class CouponRecordServiceImpl implements CouponRecordService{

	@Autowired
	private CouponRecordDao couponRecordDao;

	@Override
	public void deleteByPrimaryKey(Long id) {
		couponRecordDao.deleteByPrimaryKey(id);
	}



	@Override
	public void insertSelective(CouponRecordPo record) {
		couponRecordDao.insertSelective(record);
	}



	@Override
	public CouponRecordPo selectByPrimaryKey(Long id) {
		return couponRecordDao.selectByPrimaryKey(id);
	}



	@Override
	public void updateByPrimaryKeySelective(CouponRecordPo record) {
		couponRecordDao.updateByPrimaryKeySelective(record);
	}



	@Override
	public PageInfo<CouponRecordDetailPo> queryRecordList(CouponRecordParamPo couponRecordParamPo) {
		PageHelper.startPage(couponRecordParamPo.getPageNum(),couponRecordParamPo.getPageSize());
		return new PageInfo<CouponRecordDetailPo>(couponRecordDao.queryRecordList(couponRecordParamPo));
	}


	@Override
	public int deleteByOrderId(String orderId) {
		return couponRecordDao.deleteByOrderId(orderId);
	}
}
