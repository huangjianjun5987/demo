package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.CompanyCancelNoPayPo;
import com.yatang.sc.order.domain.OrderPayments;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/24.
 */
public interface OrderPaymentsService {

    void saveOrderPayments(OrderPayments orderPayments);

    List<OrderPayments> selectByOrderId(String orderId);

    int savePaymentInfo(String orderId, Double amount, Date payDate, String channel, String transNum, String payRecordNo,String payAccount);

    List<CompanyCancelNoPayPo> getCompanyCancelNoPay();

    Long getNoPayByCompanyId(String companyId);
}
