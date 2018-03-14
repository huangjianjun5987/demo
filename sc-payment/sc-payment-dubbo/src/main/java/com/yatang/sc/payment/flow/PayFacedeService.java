package com.yatang.sc.payment.flow;

import com.yatang.sc.payment.dto.request.AbstractPayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.NoneStrRequestDto;
import com.yatang.sc.payment.dto.response.PayStatusQueryResponseDto;
import com.yatang.sc.payment.dto.request.QueryPayStatusRequestDto;
import com.yatang.sc.payment.exception.PayNotifyException;
import com.yatang.sc.payment.exception.PrePayInfoException;
import com.yatang.sc.payment.exception.RefundException;
import com.yatang.sc.payment.exception.ValidationSignException;

/**
 * @param <PP>预支付参数
 * @param <PR>预支付返回
 */
public interface PayFacedeService<PP extends NoneStrRequestDto, PR> {

    /**
     * 获取预支付信息
     *
     * @param pParams
     * @return
     */
    PR getPrePayInfo(PP pParams) throws PrePayInfoException, ValidationSignException;

    /**
     * 支付通知回调处理方法
     *
     * @param pPayNotifyRequest
     * @return
     */
    boolean payNotify(AbstractPayNotifyRequestDto pPayNotifyRequest) throws PayNotifyException, ValidationSignException;
    /**
     * 查询支付订单状态
     */
    PayStatusQueryResponseDto queryPayStatus(QueryPayStatusRequestDto pPayNo);
}
