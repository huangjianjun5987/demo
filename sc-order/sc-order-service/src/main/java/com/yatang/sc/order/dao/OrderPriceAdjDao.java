package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.OrderPriceAdj;

import java.util.List;

public interface OrderPriceAdjDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPriceAdj record);

    int insertSelective(OrderPriceAdj record);

    OrderPriceAdj selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPriceAdj record);

    int updateByPrimaryKey(OrderPriceAdj record);

    List<OrderPriceAdj> selectByOrderPriceId(Long id);
}