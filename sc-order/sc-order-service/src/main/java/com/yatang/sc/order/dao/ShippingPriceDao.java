package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.ShippingPrice;

public interface ShippingPriceDao {
    int deleteByPrimaryKey(Long id);

    int insert(ShippingPrice record);

    int insertSelective(ShippingPrice record);

    ShippingPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShippingPrice record);

    int updateByPrimaryKey(ShippingPrice record);
}