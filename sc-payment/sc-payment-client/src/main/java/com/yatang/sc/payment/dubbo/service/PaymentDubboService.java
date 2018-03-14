package com.yatang.sc.payment.dubbo.service;

import com.busi.common.resp.Response;
import com.yatang.sc.payment.dto.PaymentRecordDto;
import com.yatang.sc.payment.dto.request.PayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.PrePayRequestDto;
import com.yatang.sc.payment.dto.request.QueryPayStatusRequestDto;
import com.yatang.sc.payment.dto.response.PayStatusQueryResponseDto;

/**
 * Created by yuwei on 2017/7/10.
 */
public interface PaymentDubboService {
    <R> Response<R> getPrePayInfo(PrePayRequestDto pParams);

    <R> Response<R> payNotify(PayNotifyRequestDto pPayNotifyDto);

    Response<PayStatusQueryResponseDto> queryPayStatus(QueryPayStatusRequestDto pQueryPayStatusRequestDto);

    Response<PaymentRecordDto> queryPaymentRecordByPayNo(String payNo);
}
