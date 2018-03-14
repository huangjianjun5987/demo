package com.yatang.sc.payment.flow.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.google.common.collect.Maps;
import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.dto.response.RefundResponseDto;
import com.yatang.sc.payment.exception.RefundException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.AbstractRefundFacedService;
import com.yatang.sc.payment.util.AliPayUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("alipayRefundFacedeService")
public class AlipayRefundFacedeService extends AbstractRefundFacedService {
    @Override
    protected RefundResponseDto requestRefund(PayRefundPO pRefundPO, PaymentConfigPO pPaymentConfigPO, PayRefundPO payRefundPO) throws RefundException, ValidationSignException {
        AlipayClient alipayClient = AliPayUtils.createClient(pPaymentConfigPO);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        Map<String, Object> params = Maps.newHashMap();
        params.put("trade_no", pRefundPO.getPayTradeNo());
        params.put("refund_amount", pRefundPO.getRefundAmount());
        params.put("out_request_no", pRefundPO.getRefundNo());
        request.setBizContent(JSON.toJSONString(params));
        AlipayTradeRefundResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException pE) {
            mLogger.error("支付宝退款接口异常", pE);
            throw new RefundException("支付宝退款接口异常:" + pE.getErrMsg(), pE);
        }
        RefundResponseDto refundResponseDto = new RefundResponseDto();
        if (response == null) {
            refundResponseDto.setSuccess(false);
            refundResponseDto.setMsg("支付宝退款接口请求失败");
            return refundResponseDto;
        }
        if (!response.isSuccess() || !"10000".equals(response.getCode())) {
            mLogger.warn("支付宝退款申请失败,订单号:{} response msg:{} subMsg:{}", pRefundPO.getPayTradeNo(), response.getMsg(), response.getSubMsg());
            refundResponseDto.setSuccess(false);
            refundResponseDto.setMsg(response.getSubMsg());
        } else {
            refundResponseDto.setSuccess(true);
            refundResponseDto.setMsg("退款成功");
            refundResponseDto.setRefundNo(response.getOutTradeNo());
            refundResponseDto.setRefundTradeNo(response.getTradeNo());
            refundResponseDto.setRefundAmount(Double.valueOf(response.getRefundFee()));
        }
        return refundResponseDto;
    }
}
