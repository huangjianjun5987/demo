package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.OrderRefundPo;

import java.util.List;

public interface OrderRefundDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderRefundPo record);

    int insertSelective(OrderRefundPo record);

    OrderRefundPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRefundPo record);

    int updateByPrimaryKey(OrderRefundPo record);

    int selectByReturnOrderId(String returnOrderId);

    OrderRefundPo queryByReturnId(String returnId);
}