package com.yatang.sc.purchase.flow;

import com.yatang.sc.purchase.dto.AddPaymentInfoDto;
import com.yatang.sc.purchase.dto.ConfirmPaymentDto;

public interface AddPaymentInfoFlow {
    /**
     * 新增订单线下支付的支付状态
     *
     * @param addPaymentInfoDto
     * @return
     */
    String addPaymentInfo(AddPaymentInfoDto addPaymentInfoDto) throws Exception;

    /**
     * 确认线下付款
     *
     * @param confirmPaymentDto
     * @return
     */
    Boolean confirmOfflinePayment(ConfirmPaymentDto confirmPaymentDto) throws Exception;
}
