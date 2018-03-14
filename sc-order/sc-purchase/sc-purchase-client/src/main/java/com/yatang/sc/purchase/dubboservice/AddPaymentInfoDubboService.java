package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.AddPaymentInfoDto;
import com.yatang.sc.purchase.dto.ConfirmPaymentDto;

public interface AddPaymentInfoDubboService {

    /**
     * 新增订单线下支付的支付状态
     *
     * @param addPaymentInfoDto
     * @return
     */
    Response<String> addPaymentInfo(AddPaymentInfoDto addPaymentInfoDto);

    /**
     * 确认线下付款
     *
     * @param confirmPaymentDto
     * @return
     */
    Response<Boolean> confirmOfflinePayment(ConfirmPaymentDto confirmPaymentDto);
}
