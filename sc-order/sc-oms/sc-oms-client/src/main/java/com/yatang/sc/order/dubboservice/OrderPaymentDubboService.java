package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.dto.PaymentResultDto;
import com.yatang.sc.dto.UpdateOrderPaymentStatusDto;

import java.util.Date;

public interface OrderPaymentDubboService {
    Response<Boolean> confirmOrderPayment(PaymentResultDto var1);
    Response<Boolean> updateOrderPaymentStatus(UpdateOrderPaymentStatusDto pUpdateOrderPaymentStatusDto);


}
