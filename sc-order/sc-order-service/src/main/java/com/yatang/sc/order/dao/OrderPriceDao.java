package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.OrderPrice;

public interface OrderPriceDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPrice record);

    int insertSelective(OrderPrice record);

    OrderPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPrice record);

    int updateByPrimaryKey(OrderPrice record);
    
    int updateOrderPriceCouponActivityReverted(Long id);
}