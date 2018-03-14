package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.OrderDictionary;

public interface OrderDictionaryDao {
    int deleteByPrimaryKey(String propertyName);

    int insert(OrderDictionary record);

    int insertSelective(OrderDictionary record);

    OrderDictionary selectByPrimaryKey(String propertyName);

    int updateByPrimaryKeySelective(OrderDictionary record);

    int updateByPrimaryKey(OrderDictionary record);
}