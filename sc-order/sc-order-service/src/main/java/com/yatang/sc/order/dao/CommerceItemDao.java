package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.CommerceItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommerceItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(CommerceItem record);

    int insertSelective(CommerceItem record);

    CommerceItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommerceItem record);

    int updateByPrimaryKey(CommerceItem record);

    List<CommerceItem> getCommerceItemForOrderId(String orderId);

    List<CommerceItem> getCommerceItemAndPriceForOrderId(String orderId);

    int updateState(@Param("itemIds") List<Long> itemIds, @Param("state") short state, @Param("stateDetail") String stateDetail);
}