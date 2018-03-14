package com.yatang.sc.payment.service;

import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.enums.PayStatus;

import java.util.List;

/**
 * Created by yuwei on 2017/7/10.
 */
public interface PaymentRecordService {
    List<PaymentRecordPO> queryPayRecordsByOrderNo(String pOrderNo, List<PayStatus> pPayStatusList);

    PaymentRecordPO queryPayRecordsByPayNo(String pPayNo);

    Long insert(PaymentRecordPO pPaymentRecordPO);

    int update(PaymentRecordPO pPaymentRecordPO);

}
