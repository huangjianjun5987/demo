package com.yatang.sc.payment.flow.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.dto.request.AbstractPayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.AliPayNotifyRequestDto;
import com.yatang.sc.payment.dto.response.PayStatusQueryResponseDto;
import com.yatang.sc.payment.dto.request.PrePayRequestDto;
import com.yatang.sc.payment.enums.PayStatus;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.PayWayCode;
import com.yatang.sc.payment.exception.PrePayInfoException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.AbstractPayFacedeService;
import com.yatang.sc.payment.util.AliPayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/8.
 */
@Service("aliPayPayFacedeService")
@Transactional
public class AlipayPayFacedeService extends AbstractPayFacedeService<PrePayRequestDto, String> {

    @Override
    protected PaymentRecordPO getPayOrder(PrePayRequestDto pParams) {
        PaymentRecordPO paymentRecordPO = new PaymentRecordPO();
        paymentRecordPO.setOrderNo(pParams.getOutOrderNo());
        paymentRecordPO.setPayStatus(PayStatus.REQUEST);
        paymentRecordPO.setPayTypeCode(PayType.alipay.getCode());
        paymentRecordPO.setPayTypeName(PayType.alipay.getName());
        paymentRecordPO.setPayWayCode(PayWayCode.APP_PAY.getCode());
        paymentRecordPO.setPayWayName(PayWayCode.APP_PAY.getName());
        paymentRecordPO.setTotalFee(pParams.getTotalFee());
        paymentRecordPO.setRequestFrom(pParams.getSpbillCreateIp());
        paymentRecordPO.setNonceStr(pParams.getNonceStr());
        return paymentRecordPO;
    }

    @Override
    protected String requestPrePayInfo(PrePayRequestDto pParams, String pPayNo) throws PrePayInfoException {
        if (pParams == null) {
            mLogger.warn("AliPay requestPrePayInfo param is null.");
            throw new PrePayInfoException("参数错误,参数为空.");
        }

        mLogger.info("AliPay getAppPrePayInfo param:{}", pParams.toString());

        PaymentConfigPO paymentConfigPO = mPaymentConfigService.getConfigByType(PayType.alipay);
        if (paymentConfigPO == null) {
            mLogger.error("Can not get any AliPay pay config form database.");
            throw new PrePayInfoException("支付宝支付未配置支付信息.");
        }
        if("test".equals(devEnvironment)){
            pParams.setTotalFee(0.01D);
        }
        AlipayClient alipayClient = AliPayUtils.createClient(paymentConfigPO);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(pParams.getBody());
        model.setSubject(pParams.getBody());
        model.setOutTradeNo(pPayNo);
        // model.setTimeoutExpress(alipayConfig.getTimeoutExpress());
        model.setTotalAmount(String.valueOf(pParams.getTotalFee()));
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(paymentConfigPO.getAsynNotifyUrl());

        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
        } catch (AlipayApiException pE) {
            mLogger.error("调用微信接口异常", pE);
            throw new PrePayInfoException("调用支付宝接口异常");
        }
        String body = response.getBody();
        mLogger.info("支付宝支付信息:{}", body);
        return body;
    }

    @Override
    protected void validateNotifyParameterType(AbstractPayNotifyRequestDto pPayNotifyRequest) {
        if (!(pPayNotifyRequest instanceof AliPayNotifyRequestDto)) {
            throw new IllegalArgumentException("参数类型错误，需要：com.yatang.sc.payment.dto.request.AliPayNotifyRequestDto");
        }
    }


    @Override
    protected void validateNotifySign(AbstractPayNotifyRequestDto pPayNotifyRequest, PaymentConfigPO pPaymentConfigPO) throws ValidationSignException {
        AliPayUtils.validateSign(pPayNotifyRequest.getOriginalMaps(), pPaymentConfigPO.getPublicKey(), pPaymentConfigPO.getSignType());
    }

    @Override
    protected boolean judgePayTradeSuccess(AbstractPayNotifyRequestDto pPayNotifyRequest) {
        AliPayNotifyRequestDto requestDto = (AliPayNotifyRequestDto) pPayNotifyRequest;
        if ("TRADE_SUCCESS".equals(requestDto.getTradeStatus())
                || "TRADE_FINISHED".equals(requestDto.getTradeStatus())) {
            return true;
        }
        return false;
    }

    @Override
    protected PayStatusQueryResponseDto requestPayStatus(String pPayNo) throws Exception {
        PaymentConfigPO paymentConfigPO = mPaymentConfigService.getConfigByType(PayType.alipay);
        if (paymentConfigPO == null) {
            mLogger.error("Can not get any AliPay pay config form database.");
            throw new Exception("支付宝支付未配置支付信息.");
        }
        AlipayClient alipayClient = AliPayUtils.createClient(paymentConfigPO);
        AlipayTradeQueryRequest aliPayRequest = new AlipayTradeQueryRequest();
        Map<String, String> param = new HashMap<>();
        param.put("out_trade_no", pPayNo);
        aliPayRequest.setBizContent(JSON.toJSONString(param));
        AlipayTradeQueryResponse response = alipayClient.execute(aliPayRequest);
        PayStatusQueryResponseDto payStatusQueryResponseDto = new PayStatusQueryResponseDto();

        if (response.isSuccess()) {
            payStatusQueryResponseDto.setTradeNo(response.getTradeNo());
            payStatusQueryResponseDto.setPayNo(response.getOutTradeNo());
            payStatusQueryResponseDto.setTotalAmount(Double.valueOf(response.getTotalAmount()));
        } else {
            payStatusQueryResponseDto.setSuccess(false);
            payStatusQueryResponseDto.setErrorMsg(response.getSubMsg());
        }
        return payStatusQueryResponseDto;
    }
}
