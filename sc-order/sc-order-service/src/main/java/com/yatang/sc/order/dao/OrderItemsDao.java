package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.orderIndex.OrderItemIndex;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.domain.QuerOrderItemsPo;

import java.util.List;

public interface OrderItemsDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderItems record);

    int insertSelective(OrderItems record);

    OrderItems selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItems record);

    int updateByPrimaryKey(OrderItems record);

    List<OrderItems> getOrderItemsForOrderId(String orderId);

    List<QuerOrderItemsPo> queryOrderItemsByOrderId(String orderId);

    List<OrderItemIndex> getOrderItemToScreenPoList(String orderId);
}