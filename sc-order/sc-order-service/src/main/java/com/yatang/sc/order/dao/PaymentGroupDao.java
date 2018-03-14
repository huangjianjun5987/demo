package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.PaymentGroup;

import java.util.List;

public interface PaymentGroupDao {
    int deleteByPrimaryKey(String id);

    int insert(PaymentGroup record);

    int insertSelective(PaymentGroup record);

    PaymentGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PaymentGroup record);

    int updateByPrimaryKey(PaymentGroup record);

    List<PaymentGroup> selectByOrderId(String orderId);
}