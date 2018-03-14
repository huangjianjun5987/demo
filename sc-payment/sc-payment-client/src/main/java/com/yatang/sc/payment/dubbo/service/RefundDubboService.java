package com.yatang.sc.payment.dubbo.service;

import com.busi.common.resp.Response;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dto.request.AddRefundRecordDto;
import com.yatang.sc.payment.dto.request.ApplyRefundRequestDto;
import com.yatang.sc.payment.dto.request.AuditRefundRequestDto;
import com.yatang.sc.payment.dto.request.RefundConfirmDto;
import com.yatang.sc.payment.dto.request.RefundRequestDto;
import com.yatang.sc.payment.dto.response.RefundResponseDto;

import java.util.List;

public interface RefundDubboService{

    /**
     * 退款
     *
     * @param pParams
     * @return
     */
    Response<RefundResponseDto> refund(RefundRequestDto pParams);

    /**
     * 确认退款
     * @param pRefundConfirmDto
     * @return
     */
    Response<Boolean> confirmRefund(RefundConfirmDto pRefundConfirmDto);

    /**
     * 申请退款
     *
     * @param pParams
     * @return
     */
    Response<Long> applyRefund(ApplyRefundRequestDto pParams);

    /**
     * 审核退款
     *
     * @return
     */
    Response<Boolean> auditRefund(AuditRefundRequestDto pRequestDto);

    /**
     * 获取订单退款申请记录
     * @param pOrderId
     * @return
     */
    Response<List<RefundDto>> getRefundByOrderId(String pOrderId);

    /**
     * 添加退款记录
     * @param pRefundConfirmDto
     * @return
     */
    Response<Boolean> addRefund(AddRefundRecordDto pRefundConfirmDto);

    /**
     * 通过退款记录id查询
     *
     * @param id
     * @return
     */
    Response<RefundDto> refundById(Long id);
}
