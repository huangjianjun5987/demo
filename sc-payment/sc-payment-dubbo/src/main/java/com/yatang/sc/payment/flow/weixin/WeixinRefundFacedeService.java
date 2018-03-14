package com.yatang.sc.payment.flow.weixin;

import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.dto.response.RefundResponseDto;
import com.yatang.sc.payment.exception.RefundException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.AbstractRefundFacedService;
import com.yatang.sc.payment.util.WeiXinPayUtils;
import com.yatang.sc.payment.util.XmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service("weixinRefundFacedeService")
public class WeixinRefundFacedeService extends AbstractRefundFacedService {

    @Override
    protected RefundResponseDto requestRefund(PayRefundPO pRefundPO, PaymentConfigPO pPaymentConfigPO, PayRefundPO payRefundPO) throws RefundException, ValidationSignException {
        try {
            Map<String, String> paramMap = WeiXinPayUtils.convertRefundParamToMap(pRefundPO, pPaymentConfigPO);
            String xmlParam = XmlUtils.mapToXml(paramMap);

            xmlParam = new String(xmlParam.getBytes("UTF-8"), "ISO8859-1");

            String refundApi = pPaymentConfigPO.getRefundApi();
            if (!refundApi.contains(pPaymentConfigPO.getPayServer())) {
                refundApi = pPaymentConfigPO.getPayServer() + refundApi;
            }

            String responseStr = WeiXinPayUtils.sendRequestViaSSL(refundApi, xmlParam, pPaymentConfigPO.getCertPassword(), pPaymentConfigPO.getCertPath());
            Map<String, String> responseMap = null;
            if (StringUtils.isNotEmpty(responseStr)) {
                responseMap = XmlUtils.xml2Map(responseStr, "xml");
            }

            RefundResponseDto refundResponseDto;
            if (responseMap == null || responseMap.isEmpty()) {
                refundResponseDto = new RefundResponseDto();
                mLogger.error("微信支付退款错误，返回值为空：{}", pRefundPO.getRefundNo());
                refundResponseDto.setSuccess(false);
                refundResponseDto.setMsg("微信支付退款错误，返回值为空：" + pRefundPO.getRefundNo());
                return refundResponseDto;
            }

            String returnCode = responseMap.get("return_code");
            if ("FAIL".equals(returnCode)) {
                refundResponseDto = new RefundResponseDto();
                refundResponseDto.setSuccess(false);
                refundResponseDto.setMsg(responseMap.get("return_msg"));
                return refundResponseDto;
            }

            WeiXinPayUtils.validateSign(responseMap, pPaymentConfigPO.getSignKey());
            String resultCode = responseMap.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                refundResponseDto = new RefundResponseDto();
                refundResponseDto.setRefundAmount(Double.valueOf(responseMap.get("refund_fee")) / 100);//微信单位为分
                refundResponseDto.setRefundTradeNo(responseMap.get("refund_id"));
                refundResponseDto.setRefundNo(responseMap.get("out_refund_no"));

            } else {
                refundResponseDto = new RefundResponseDto();
                refundResponseDto.setSuccess(false);
                String error = responseMap.get("err_code_des");
                if (StringUtils.isEmpty(error)) {
                    error = responseMap.get("return_msg");
                }
                refundResponseDto.setMsg(error);
            }
            return refundResponseDto;
        } catch (UnsupportedEncodingException pE) {
            mLogger.error("退款异常:{}", pRefundPO.getRefundNo());
            throw new RefundException("退款异常.", pE);
        } catch (ExecutionException pE) {
            mLogger.error("退款异常:{}", pRefundPO.getRefundNo());
            throw new RefundException("退款异常.", pE);
        } catch (IOException pE) {
            mLogger.error("退款异常:{}", pRefundPO.getRefundNo());
            throw new RefundException("退款异常.", pE);
        }
    }
}
