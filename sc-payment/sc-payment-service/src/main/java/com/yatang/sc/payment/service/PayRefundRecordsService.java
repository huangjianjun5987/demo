package com.yatang.sc.payment.service;

import com.yatang.sc.payment.domain.PayRefundRecordsPO;

import java.util.List;

/**
 * Created by yuwei on 2017/7/11.
 */
public interface PayRefundRecordsService {
    Long insert(PayRefundRecordsPO pPayRefundRecordsPO);

    List<PayRefundRecordsPO> queryRefundRecordsByRefundId(Integer pRefundId);
}
