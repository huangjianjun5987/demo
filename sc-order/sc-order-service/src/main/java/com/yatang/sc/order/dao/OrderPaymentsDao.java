package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.CompanyCancelNoPayPo;
import com.yatang.sc.order.domain.OrderPayments;

import java.util.List;

public interface OrderPaymentsDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPayments record);

    int insertSelective(OrderPayments record);

    OrderPayments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPayments record);

    int updateByPrimaryKey(OrderPayments record);

    List<OrderPayments> selectByOrderId(String orderId);

}