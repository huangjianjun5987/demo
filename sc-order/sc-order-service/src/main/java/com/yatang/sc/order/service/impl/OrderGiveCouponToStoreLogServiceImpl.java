package com.yatang.sc.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busi.idgenerator.IdGenerator;
import com.yatang.sc.order.dao.OrderGiveCouponToStoreLogDao;
import com.yatang.sc.order.domain.OrderGiveCouponToStoreLog;
import com.yatang.sc.order.service.OrderGiveCouponToStoreLogService;

/**
 * Created by 邓东山 on 2017/11/1.
 */
@Service
@Transactional
public class OrderGiveCouponToStoreLogServiceImpl implements OrderGiveCouponToStoreLogService {

    @Autowired
    private OrderGiveCouponToStoreLogDao dao;

    @Autowired
    IdGenerator idGenerator;
	@Override
	public void save(OrderGiveCouponToStoreLog couponToStoreLog) {
		couponToStoreLog.setId(idGenerator.generateId("ogcl"));
		dao.insert(couponToStoreLog);
	}
    
	@Override
	public void update(OrderGiveCouponToStoreLog couponToStoreLog) {
		dao.update(couponToStoreLog);
	}
	
	
	@Override
	public OrderGiveCouponToStoreLog queryByPriceInfoId(Long priceInfoId) {
		return dao.queryByPriceInfoId(priceInfoId);
	}
}
