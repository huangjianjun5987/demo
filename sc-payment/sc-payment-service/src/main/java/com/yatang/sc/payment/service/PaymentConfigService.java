package com.yatang.sc.payment.service;

import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.enums.PayType;

/**
 * Created by yuwei on 2017/7/10.
 */
public interface PaymentConfigService {
    PaymentConfigPO getConfigByType(PayType pPayType);
}
