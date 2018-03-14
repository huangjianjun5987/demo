package com.yatang.sc.order.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.domain.CouponRecordDetailPo;
import com.yatang.sc.order.domain.CouponRecordParamPo;
import com.yatang.sc.order.domain.CouponRecordPo;

/**
 * @description: 优惠券使用记录dao
 * @author: yinyuxin
 * @date: 2017/9/19 15:45
 * @version: v1.0
 */
public interface CouponRecordService {

	void deleteByPrimaryKey(Long id);

	void insertSelective(CouponRecordPo record);

	CouponRecordPo selectByPrimaryKey(Long id);

	void updateByPrimaryKeySelective(CouponRecordPo record);

	/**
	 * 查询参与数据（已使用）
	 * @param couponRecordParamPo
	 * @return
	 */
	PageInfo<CouponRecordDetailPo> queryRecordList(CouponRecordParamPo couponRecordParamPo);
	
	int deleteByOrderId(String orderId);
}
