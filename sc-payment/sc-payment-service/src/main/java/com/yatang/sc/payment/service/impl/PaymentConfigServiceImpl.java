package com.yatang.sc.payment.service.impl;

import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.payment.dao.PaymentConfigDao;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.service.PaymentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuwei on 2017/7/10.
 */
@Service
public class PaymentConfigServiceImpl implements PaymentConfigService {

    @Autowired
    private PaymentConfigDao mPaymentConfigDao;

    @LocalCache(group = "payment",expireAfterAccess = 60,maximumSize = 100)
    public PaymentConfigPO getConfigByType(PayType pPayType) {
        return mPaymentConfigDao.getConfigByType(pPayType.getCode());
    }
}
