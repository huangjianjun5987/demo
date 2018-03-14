package com.yatang.sc.service;

import com.yatang.sc.dto.PaymentResultDto;

public interface PaySuccessHelper {
    boolean processForPay(PaymentResultDto paymentResult) throws Exception;
    boolean confirmPay(String orderId) throws Exception;
}
