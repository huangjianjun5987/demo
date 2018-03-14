package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.PaymentGroup;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/8.
 */
public interface PaymentGroupService{

    PaymentGroup getPaymentGroupForId(String id);

    int updatePaymentGroup(PaymentGroup paymentGroup);

    void save(PaymentGroup paymentGroup);

    List<PaymentGroup> getPaymentGroupForOrderId(String orderId);

    int updateByPrimaryKeySelective(PaymentGroup record);
}
