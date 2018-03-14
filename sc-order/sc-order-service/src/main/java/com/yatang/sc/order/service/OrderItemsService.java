package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.orderIndex.OrderItemIndex;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.domain.QuerOrderItemsPo;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
public interface OrderItemsService {
    List<OrderItems> getOrderItemsForOrderId(String orderId);

    void save(OrderItems orderItems);

    List<QuerOrderItemsPo> queryOrderItemsByOrderId(String orderId);

    List<OrderItemIndex> getOrderItemToScreenPoList(String orderId);
    int updateByPrimaryKey(OrderItems record);

    int deleteByPrimaryKey(Long id);
}
