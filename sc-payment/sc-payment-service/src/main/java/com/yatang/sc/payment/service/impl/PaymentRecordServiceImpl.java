package com.yatang.sc.payment.service.impl;

import com.yatang.sc.payment.dao.PaymentRecordDao;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.enums.PayStatus;
import com.yatang.sc.payment.service.PaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/10.
 */
@Service("paymentRecordService")
@Transactional(rollbackFor = Exception.class)
public class PaymentRecordServiceImpl implements PaymentRecordService {

    protected Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentRecordDao mPayOrderDao;

    @Override
    public List<PaymentRecordPO> queryPayRecordsByOrderNo(String pOrderNo, List<PayStatus> pPayStatusList) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", pOrderNo);
        params.put("payStatusList", pPayStatusList);
        return mPayOrderDao.queryPayRecordsByOrderNo(params);
    }

    @Override
    public PaymentRecordPO queryPayRecordsByPayNo(String pPayNo) {
        return mPayOrderDao.queryPayRecordsByPayNo(pPayNo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Long insert(PaymentRecordPO pPaymentRecordPO) {

        return mPayOrderDao.insert(pPaymentRecordPO);
    }

    @Override
    public int update(PaymentRecordPO pPaymentRecordPO) {
        return mPayOrderDao.update(pPaymentRecordPO);
    }
}
