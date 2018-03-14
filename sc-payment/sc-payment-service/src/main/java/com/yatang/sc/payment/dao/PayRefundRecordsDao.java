package com.yatang.sc.payment.dao;

import com.yatang.sc.payment.domain.PayRefundRecordsPO;

import java.util.List;

/**
 * Created by yuwei on 2017/7/11.
 */
public interface PayRefundRecordsDao {

    Long insert(PayRefundRecordsPO pPayRefundRecordsPO);

    List<PayRefundRecordsPO> queryRefundRecordsByRefundId(Integer pRefundId);
}
