package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.ShippingGroup;

public interface ShippingGroupDao {
    int deleteByPrimaryKey(String id);

    int insert(ShippingGroup record);

    int insertSelective(ShippingGroup record);

    ShippingGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ShippingGroup record);

    int updateByPrimaryKey(ShippingGroup record);

}