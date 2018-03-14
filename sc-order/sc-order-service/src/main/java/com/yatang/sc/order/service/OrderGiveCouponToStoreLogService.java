package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.OrderGiveCouponToStoreLog;

/**
 * Created by 邓东山 on 2017/11/1.
 */
public interface OrderGiveCouponToStoreLogService {

    void save(OrderGiveCouponToStoreLog couponToStoreLog);

	void update(OrderGiveCouponToStoreLog couponToStoreLog);

	OrderGiveCouponToStoreLog queryByPriceInfoId(Long priceInfoId);

}
