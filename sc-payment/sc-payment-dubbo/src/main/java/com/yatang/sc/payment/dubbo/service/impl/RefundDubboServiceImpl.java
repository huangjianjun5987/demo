package com.yatang.sc.payment.dubbo.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.payment.common.ResponseUtils;
import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.domain.PayRefundRecordsPO;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dto.request.AddRefundRecordDto;
import com.yatang.sc.payment.dto.request.ApplyRefundRequestDto;
import com.yatang.sc.payment.dto.request.AuditRefundRequestDto;
import com.yatang.sc.payment.dto.request.RefundConfirmDto;
import com.yatang.sc.payment.dto.request.RefundRequestDto;
import com.yatang.sc.payment.dto.response.RefundResponseDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.payment.exception.RefundException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.RefundFacedeService;
import com.yatang.sc.payment.flow.wrapper.RefundFacedeServiceWrapper;
import com.yatang.sc.payment.service.PayRefundRecordsService;
import com.yatang.sc.payment.service.PayRefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("refundDubboService")
public class RefundDubboServiceImpl implements RefundDubboService {
    protected Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RefundFacedeServiceWrapper mRefundFacedeServiceWrapper;

    @Resource(name = "payRefundService")
    protected PayRefundService mPayRefundService;

    @Resource(name = "payRefundRecordsService")
    protected PayRefundRecordsService mPayRefundRecordsService;

    @Override
    public Response<RefundResponseDto> refund(RefundRequestDto pParams) {
        mLogger.info("refund request params:{}",pParams);
        Response<RefundResponseDto> dtoResponse = ResponseUtils.RESPONSE_200();
        try {
            if (pParams.getPayType() == null) {
                PayRefundPO refundPO = mPayRefundService.queryRefundsByRefundNo(pParams.getRefundNo());
                pParams.setPayType(refundPO.getPayType());
            }
            dtoResponse.setResultObject(mRefundFacedeServiceWrapper.wrap(pParams.getPayType()).refund(pParams));
            return dtoResponse;
        } catch (RefundException pE) {
            mLogger.error("退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage(pE.getMessage());
        } catch (ValidationSignException pE) {
            mLogger.error("退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage(pE.getMessage());
        } catch (Exception pE) {
            mLogger.error("退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage("退款操作异常");
        }
        return dtoResponse;
    }

    @Override
    public Response<Boolean> confirmRefund(RefundConfirmDto pRefundConfirmDto) {
        mLogger.info("refund request params:{}", pRefundConfirmDto);
        Response<Boolean> dtoResponse = ResponseUtils.RESPONSE_200();
        try {
            dtoResponse.setResultObject(mRefundFacedeServiceWrapper.wrap(PayType.weixin).confirmRefund(pRefundConfirmDto));
            return dtoResponse;
        } catch (RefundException pE) {
            mLogger.error("退款确认异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage(pE.getMessage());
        } catch (Exception pE) {
            mLogger.error("退款确认异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage("退款确认异常");
        }
        return dtoResponse;
    }

    @Override
    public Response<Long> applyRefund(ApplyRefundRequestDto pParams) {
        Response<Long> dtoResponse = ResponseUtils.RESPONSE_200();
        try {
            //TODO 未接入的线下支付默认取微信

            RefundFacedeService refundFacedeService = mRefundFacedeServiceWrapper.tryWrap(pParams.getPayType());
            if(refundFacedeService == null){
                refundFacedeService = mRefundFacedeServiceWrapper.wrap(PayType.weixin);
            }
            dtoResponse.setResultObject(refundFacedeService.applyRefund(pParams));
            return dtoResponse;
        } catch (RefundException pE) {
            mLogger.error("申请退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage(pE.getMessage());
        } catch (ValidationSignException pE) {
            mLogger.error("申请退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage(pE.getMessage());
        } catch (Exception pE) {
            mLogger.error("申请退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage("申请退款操作异常");
        }
        return dtoResponse;
    }

    @Override
    public Response<Boolean> auditRefund(AuditRefundRequestDto pParams) {
        Response<Boolean> dtoResponse = ResponseUtils.RESPONSE_200();
        try {
            dtoResponse.setResultObject(mRefundFacedeServiceWrapper.wrap(PayType.weixin).auditRefund(pParams));
        } catch (RefundException pE) {
            mLogger.error("申请退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage(pE.getMessage());
        } catch (Exception pE) {
            mLogger.error("申请退款异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage("申请退款操作异常");
        }
        return dtoResponse;
    }

    @Override
    public Response<List<RefundDto>> getRefundByOrderId(String pOrderId) {
        List<PayRefundPO> payRefundPOS = mPayRefundService.queryRefundsByOrderNo(pOrderId);
        List<RefundDto> refundDtos = BeanConvertUtils.convertList(payRefundPOS,RefundDto.class);
        Response<List<RefundDto>> response = new Response<>();
        response.setSuccess(true);
        response.setResultObject(refundDtos);
        return response;
    }

    @Override
    public Response<Boolean> addRefund(AddRefundRecordDto pAddRefundRecordDto) {
        mLogger.info("add refund request params:{}", pAddRefundRecordDto);
        Response<Boolean> dtoResponse = ResponseUtils.RESPONSE_200();
        try {
            PayRefundPO payRefundPO = new PayRefundPO();
            payRefundPO.setPayNo("manuallyAdd");
            payRefundPO.setRefundTradeNo(pAddRefundRecordDto.getTradeNo());
            payRefundPO.setPayTradeNo("manuallyAdd");
            payRefundPO.setRefundAmount(pAddRefundRecordDto.getAmount());
            payRefundPO.setTotalPaiedAmount(pAddRefundRecordDto.getAmount());
            payRefundPO.setRequestUserId(pAddRefundRecordDto.getOperatorId());
            payRefundPO.setRequestUserName(pAddRefundRecordDto.getOperatorName());
            payRefundPO.setStatus(RefundStatus.REFUNDED_CONFIRM);
            payRefundPO.setOrderNo(pAddRefundRecordDto.getOrderId());
            payRefundPO.setReason(pAddRefundRecordDto.getReason());
            payRefundPO.setRemark(pAddRefundRecordDto.getRemark());
            payRefundPO.setPayType(pAddRefundRecordDto.getPaytype());

            mPayRefundService.insert(payRefundPO);
            addRefundRecord(pAddRefundRecordDto.getOperatorId(), pAddRefundRecordDto.getOperatorName(), payRefundPO.getId(), RefundStatus.REQUEST, payRefundPO.getStatus(), payRefundPO.getRemark());

            return dtoResponse;
        } catch (Exception pE) {
            mLogger.error("退款确认异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage("退款确认异常");
        }
        return dtoResponse;
    }
    protected void addRefundRecord(String pRefundUserId, String pRefundUserName, Long pRefundId, RefundStatus pPreStatus, RefundStatus pCurrentStatus, String pRemark) {
        PayRefundRecordsPO payRefundRecordsPO = new PayRefundRecordsPO();
        payRefundRecordsPO.setOperatorId(pRefundUserId);
        payRefundRecordsPO.setOperatorName(pRefundUserName);
        payRefundRecordsPO.setRefundId(pRefundId);
        payRefundRecordsPO.setCreateTime(new Date());
        payRefundRecordsPO.setPreStatus(pPreStatus);
        payRefundRecordsPO.setCurrentStatus(pCurrentStatus);
        payRefundRecordsPO.setRemark(pRemark);
        mPayRefundRecordsService.insert(payRefundRecordsPO);
    }

    @Override
    public Response<RefundDto> refundById(Long id) {
        mLogger.info("refund request params:{}",id);
        Response<RefundDto> dtoResponse = ResponseUtils.RESPONSE_200();
        try {
            PayRefundPO refundPO = mPayRefundService.get(id);
            RefundDto payRefundDao = BeanConvertUtils.convert(refundPO,RefundDto.class);
            dtoResponse.setResultObject(payRefundDao);
            return dtoResponse;
        } catch (Exception pE) {
            mLogger.error("查询异常", pE);
            dtoResponse = ResponseUtils.RESPONSE_500();
            dtoResponse.setErrorMessage("查询操作异常");
        }
        return dtoResponse;
    }
}
