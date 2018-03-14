package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.OrderGiveCouponToStoreLog;

/**
 * Created by 邓东山 on 2017/11/1.
 */
public interface OrderGiveCouponToStoreLogDao {

	int insert(OrderGiveCouponToStoreLog couponToStoreLog);

	int update(OrderGiveCouponToStoreLog couponToStoreLog);

	OrderGiveCouponToStoreLog queryByPriceInfoId(Long priceInfoId);
}