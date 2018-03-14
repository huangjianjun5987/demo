package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.ShippingPriceAdj;

import java.util.List;

public interface ShippingPriceAdjDao {
    int deleteByPrimaryKey(Long id);

    int insert(ShippingPriceAdj record);

    int insertSelective(ShippingPriceAdj record);

    ShippingPriceAdj selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShippingPriceAdj record);

    int updateByPrimaryKey(ShippingPriceAdj record);

    List<ShippingPriceAdj> selectByShippingPriceId(Long id);
}