package com.yatang.sc.payment.dao;

import com.yatang.sc.payment.domain.PaymentConfigPO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yuwei on 2017/7/8.
 */
public interface PaymentConfigDao {
    PaymentConfigPO getConfigByType(@Param("payType") String pPayType);
}
