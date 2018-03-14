package com.yatang.sc.payment.dao;

import com.yatang.sc.payment.domain.PayRefundPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuwei on 2017/7/11.
 */
public interface PayRefundDao {

    PayRefundPO get(@Param("id") Long pRefundId);

    Long insert(PayRefundPO pPayRefundPO);

    int update(PayRefundPO pPayRefundPO);

    List<PayRefundPO> queryRefundsByPayNo(@Param("payNo") String pPayNo);

    PayRefundPO queryRefundsByRefundNo(@Param("refundNo") String pRefundNo);

    List<PayRefundPO> queryRefundsByOrderNo(@Param("orderNo") String orderNo);

}
