package com.yatang.sc.payment.service;

import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.enums.RefundStatus;

import java.util.List;

/**
 * Created by yuwei on 2017/7/11.
 */
public interface PayRefundService {
    Long insert(PayRefundPO pPayRefundPO);

    int update(PayRefundPO pPayRefundPO);

    PayRefundPO get(Long pRefundId);

    List<PayRefundPO> queryRefundsByPayNo(String pPayNo);

    PayRefundPO queryRefundsByRefundNo(String pRefundNo);

    int updateTryCount(Long mRefundId);

    int updateRefundStatus(Long mRefundId, RefundStatus pRefundStatus);

    List<PayRefundPO> queryRefundsByOrderNo(String orderNo);
}
