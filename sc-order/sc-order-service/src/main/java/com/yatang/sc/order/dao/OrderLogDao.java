package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.OrderLog;

public interface OrderLogDao {
    int deleteByPrimaryKey(String id);

    int insert(OrderLog record);

    int insertSelective(OrderLog record);

    OrderLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderLog record);

    int updateByPrimaryKey(OrderLog record);
}