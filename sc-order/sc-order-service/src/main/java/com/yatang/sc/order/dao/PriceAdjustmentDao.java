package com.yatang.sc.order.dao;

import java.util.List;

import com.yatang.sc.order.domain.PriceAdjustment;

public interface PriceAdjustmentDao {
    int deleteByPrimaryKey(Long id);

    int insert(PriceAdjustment record);

    int insertSelective(PriceAdjustment record);

    PriceAdjustment selectByPrimaryKey(Long id);

    List<PriceAdjustment> queryByOrderPriceId(Long id);
    
    int updateByPrimaryKeySelective(PriceAdjustment record);

    int updateByPrimaryKey(PriceAdjustment record);
}