package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.PaymentGroupDao;
import com.yatang.sc.order.domain.OrderPayments;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.ShippingPriceAdjService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/8.
 */
@Service
@Transactional
public class PaymentGroupServiceImpl implements PaymentGroupService {

    @Autowired
    private PaymentGroupDao dao;

    @Override
    public PaymentGroup getPaymentGroupForId(String id) {

        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updatePaymentGroup(PaymentGroup paymentGroup) {
        return dao.updateByPrimaryKey(paymentGroup);
    }

    @Override
    public void save(PaymentGroup paymentGroup) {
        dao.insert(paymentGroup);
    }

    @Override
    public List<PaymentGroup> getPaymentGroupForOrderId(String orderId) {
        List<PaymentGroup> paymentGroups = dao.selectByOrderId(orderId);
        if(CollectionUtils.isEmpty(paymentGroups)){
            return Collections.EMPTY_LIST;
        }
        return paymentGroups;
    }

    @Override
    public int updateByPrimaryKeySelective(PaymentGroup record) {
        return dao.updateByPrimaryKeySelective(record);
    }
}
