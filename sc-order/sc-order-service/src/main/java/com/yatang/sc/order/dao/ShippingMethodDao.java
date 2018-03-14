package com.yatang.sc.order.dao;


import com.yatang.sc.order.domain.ShippingMethod;

import java.util.List;

public interface ShippingMethodDao {
    int deleteByPrimaryKey(Long id);

    int insert(ShippingMethod record);

    int insertSelective(ShippingMethod record);

    ShippingMethod selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShippingMethod record);

    int updateByPrimaryKey(ShippingMethod record);

    List<ShippingMethod> getAllShippingMethod();
}