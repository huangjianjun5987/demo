package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.OrderPrice;

/**
 * Created by xiangyonghong on 2017/7/8.
 */
public interface OrderPriceService {

    OrderPrice getOrderPriceForId(Long id);
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderPrice record);

    OrderPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPrice record);

    void save(OrderPrice orderPrice);
    
    int updateOrderPriceCouponActivityReverted(Long id);
}
