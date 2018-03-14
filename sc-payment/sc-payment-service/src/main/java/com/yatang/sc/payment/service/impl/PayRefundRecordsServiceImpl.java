package com.yatang.sc.payment.service.impl;

import com.yatang.sc.payment.dao.PayRefundRecordsDao;
import com.yatang.sc.payment.domain.PayRefundRecordsPO;
import com.yatang.sc.payment.service.PayRefundRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuwei on 2017/7/15.
 */
@Service("payRefundRecordsService")
public class PayRefundRecordsServiceImpl implements PayRefundRecordsService {
    @Autowired
    private PayRefundRecordsDao mPayRefundRecordsDao;

    @Override
    public Long insert(PayRefundRecordsPO pPayRefundRecordsPO) {
        return mPayRefundRecordsDao.insert(pPayRefundRecordsPO);
    }

    @Override
    public List<PayRefundRecordsPO> queryRefundRecordsByRefundId(Integer pRefundId) {
        return mPayRefundRecordsDao.queryRefundRecordsByRefundId(pRefundId);
    }
}
