package com.yatang.sc.payment.flow;

import com.yatang.sc.payment.dto.request.ApplyRefundRequestDto;
import com.yatang.sc.payment.dto.request.AuditRefundRequestDto;
import com.yatang.sc.payment.dto.request.RefundConfirmDto;
import com.yatang.sc.payment.dto.request.RefundRequestDto;
import com.yatang.sc.payment.dto.response.RefundResponseDto;
import com.yatang.sc.payment.exception.RefundException;
import com.yatang.sc.payment.exception.ValidationSignException;

public interface RefundFacedeService {
    /**
     * 退款
     *
     * @param pParams
     * @return
     */
    RefundResponseDto refund(RefundRequestDto pParams) throws RefundException, ValidationSignException;

    /**
     * 确认退款
     * @param pRefundConfirmDto
     * @return
     */
    Boolean confirmRefund(RefundConfirmDto pRefundConfirmDto) throws RefundException;

    /**
     * 申请退款
     *
     * @param pParams
     * @return
     */
    Long applyRefund(ApplyRefundRequestDto pParams) throws RefundException, ValidationSignException;

    /**
     * 审核退款
     *
     * @return
     */
    Boolean auditRefund(AuditRefundRequestDto pRequestDto) throws RefundException;
}
