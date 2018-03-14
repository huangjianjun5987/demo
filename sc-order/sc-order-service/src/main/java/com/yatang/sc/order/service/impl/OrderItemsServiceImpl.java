package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.OrderItemsDao;
import com.yatang.sc.order.domain.orderIndex.OrderItemIndex;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.domain.QuerOrderItemsPo;
import com.yatang.sc.order.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
@Service
public class OrderItemsServiceImpl implements OrderItemsService {

    @Autowired
    private OrderItemsDao dao;

    @Override
    public List<OrderItems> getOrderItemsForOrderId(String orderId) {
        return dao.getOrderItemsForOrderId(orderId);
    }

    @Override
    public void save(OrderItems orderItems) {
        dao.insert(orderItems);
    }

    @Override
    public List<QuerOrderItemsPo> queryOrderItemsByOrderId(String orderId) {
        return dao.queryOrderItemsByOrderId(orderId);
    }

    @Override
    public List<OrderItemIndex> getOrderItemToScreenPoList(String orderId) {
        return dao.getOrderItemToScreenPoList(orderId);
    }

    @Override
    public int updateByPrimaryKey(OrderItems record) {
        return dao.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return dao.deleteByPrimaryKey(id);
    }
}
