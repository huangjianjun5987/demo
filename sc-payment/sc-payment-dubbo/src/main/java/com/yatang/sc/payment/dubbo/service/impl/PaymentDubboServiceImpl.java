package com.yatang.sc.payment.dubbo.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.payment.common.CommonsEnum;
import com.yatang.sc.payment.common.ResponseUtils;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.dto.PaymentRecordDto;
import com.yatang.sc.payment.dto.request.AbstractPayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.PayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.PrePayRequestDto;
import com.yatang.sc.payment.dto.request.QueryPayStatusRequestDto;
import com.yatang.sc.payment.dto.response.PayStatusQueryResponseDto;
import com.yatang.sc.payment.dubbo.service.PaymentDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.exception.PrePayInfoException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.wrapper.PayFacedeServiceWrapper;
import com.yatang.sc.payment.service.PaymentRecordService;
import com.yatang.sc.payment.util.AliPayUtils;
import com.yatang.sc.payment.util.WeiXinPayUtils;
import com.yatang.sc.payment.util.YTPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yuwei on 2017/7/10.
 */
@Service("paymentDubboService")
public class PaymentDubboServiceImpl implements PaymentDubboService {
    protected Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayFacedeServiceWrapper mPayFacedeServiceWrapper;

    @Resource(name = "paymentRecordService")
    protected PaymentRecordService mPaymentRecordService;

    @Override
    public Response<?> getPrePayInfo(PrePayRequestDto pParams) {
        mLogger.info("getPrePayInfo:{}", pParams.toString());
        Response<Object> response = ResponseUtils.RESPONSE_200();
        try {
            response.setResultObject(mPayFacedeServiceWrapper.wrap(pParams.getPayType()).getPrePayInfo(pParams));
            return response;
        } catch (PrePayInfoException pE) {
            response = ResponseUtils.RESPONSE_500();
            response.setErrorMessage(pE.getMessage());
            mLogger.error("getPrePayInfoForAliPay occur exception", pE);
        } catch (ValidationSignException pE) {
            response = ResponseUtils.RESPONSE_500();
            response.setErrorMessage(pE.getMessage());
            mLogger.error("getPrePayInfoForAliPay 验签失败", pE);
        } catch (Exception pE) {
            response = ResponseUtils.RESPONSE_500();
            response.setErrorMessage("获取预支付信息错误");
            mLogger.error("getPrePayInfoForAliPay occur exception", pE);
        }
        return response;
    }

    @Override
    public Response<Boolean> payNotify(PayNotifyRequestDto pPayNotifyDto) {
        mLogger.info("payNotify:{}", pPayNotifyDto.toString());
        Response<Boolean> response = ResponseUtils.RESPONSE_200();
        try {
            AbstractPayNotifyRequestDto payNotifyRequest = null;
            switch (pPayNotifyDto.getPayType()) {
                case jd: {
                    break;
                }
                case alipay: {
                    payNotifyRequest = AliPayUtils.convertNotifyParamToBean(pPayNotifyDto);
                    break;
                }
                case cmb: {
                    break;
                }
                case weixin: {
                    payNotifyRequest = WeiXinPayUtils.convertNotifyParamToBean(pPayNotifyDto);
                    break;
                }
                case ytpay: {
                    payNotifyRequest = YTPayUtils.convertNotifyParamToBean(pPayNotifyDto);
                    break;
                }
            }

            response.setResultObject(mPayFacedeServiceWrapper.wrap(pPayNotifyDto.getPayType()).payNotify(payNotifyRequest));
            return response;
        } catch (Exception ex) {
            response = ResponseUtils.RESPONSE_500();
            response.setErrorMessage("回调通知处理异常");
            mLogger.error("payNotify occur exception", ex);
        }
        return response;
    }

    @Override
    public Response<PayStatusQueryResponseDto> queryPayStatus(QueryPayStatusRequestDto pQueryPayStatusRequestDto) {
        mLogger.info("queryPayStatus:{}", pQueryPayStatusRequestDto.toString());
        PaymentRecordPO paymentRecordPO = mPaymentRecordService.queryPayRecordsByPayNo(pQueryPayStatusRequestDto.getPayNo());
        Response<PayStatusQueryResponseDto> response;
        if (paymentRecordPO == null) {
            response = ResponseUtils.RESPONSE_FAILD(CommonsEnum.PAY_ORDER_NONE_EXIST);
            return response;
        }
        try {
            PayType payType = PayType.parse(paymentRecordPO.getPayTypeCode());
            Response<PayStatusQueryResponseDto> dtoResponse = ResponseUtils.RESPONSE_200();
            dtoResponse.setResultObject(mPayFacedeServiceWrapper.wrap(payType).queryPayStatus(pQueryPayStatusRequestDto));
            return dtoResponse;

        } catch (Exception pE) {
            response = ResponseUtils.RESPONSE_500();
            response.setErrorMessage(pE.getMessage());
            mLogger.error("queryPayStatus occur exception", pE);
        }
        return response;
    }

    @Override
    public Response<PaymentRecordDto> queryPaymentRecordByPayNo(String payNo) {
        Response<PaymentRecordDto> response = new Response<>();
        try {
            PaymentRecordPO paymentRecordPO = mPaymentRecordService.queryPayRecordsByPayNo(payNo);
            if (paymentRecordPO == null) {
                return response;
            }
            mLogger.info("asdasdas:{}", JSON.toJSONString(paymentRecordPO));
            response.setResultObject(BeanConvertUtils.convert(paymentRecordPO, PaymentRecordDto.class));
            mLogger.info("asdasdas:{}", JSON.toJSONString(response));
            response.setSuccess(true);
        } catch (Exception e) {
            mLogger.error("查询支付记录异常", e);
            response.setSuccess(false);
            response.setErrorMessage("查询异常");
        }
        return response;
    }
}
