package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.ItemPrice;

public interface ItemPriceDao {
    int deleteByPrimaryKey(Long id);

    int insert(ItemPrice record);

    int insertSelective(ItemPrice record);

    ItemPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemPrice record);

    int updateByPrimaryKey(ItemPrice record);
}