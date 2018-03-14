package com.yatang.sc.payment.dao;

import com.yatang.sc.payment.domain.PaymentRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/8.
 */
public interface PaymentRecordDao {

    List<PaymentRecordPO> queryPayRecordsByOrderNo(Map<String,Object> pMap);

    PaymentRecordPO queryPayRecordsByPayNo(@Param("payNo") String pPayNo);

    Long insert(PaymentRecordPO pPaymentRecordPO);

    int update(PaymentRecordPO pPaymentRecordPO);
}
