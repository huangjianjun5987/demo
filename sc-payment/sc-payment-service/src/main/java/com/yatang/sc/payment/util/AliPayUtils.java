package com.yatang.sc.payment.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.yatang.sc.payment.common.CommonsEnum;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.dto.request.AliPayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.PayNotifyRequestDto;
import com.yatang.sc.payment.enums.NotifyType;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.exception.ValidationSignException;

import java.util.Date;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/8.
 */
public class AliPayUtils {

    public static AlipayClient createClient(PaymentConfigPO pPaymentConfigPO) {
        String prePayApi = pPaymentConfigPO.getPrePayApi();
        if (!pPaymentConfigPO.getPrePayApi().contains(pPaymentConfigPO.getPayServer())) {
            prePayApi = pPaymentConfigPO.getPayServer() + pPaymentConfigPO.getPrePayApi();
        }
        return new DefaultAlipayClient(prePayApi,
                pPaymentConfigPO.getAppId(),
                pPaymentConfigPO.getPrivateKey(),
                "json",
                "utf-8",
                pPaymentConfigPO.getPublicKey(),
                pPaymentConfigPO.getSignType()); //获得初始化的AlipayClient
    }

    public static AliPayNotifyRequestDto convertNotifyParamToBean(PayNotifyRequestDto pPayNotifyRequestDto) {
        if (pPayNotifyRequestDto == null) {
            return null;
        }
        AliPayNotifyRequestDto aliPayNotifyRequestDto = new AliPayNotifyRequestDto();
        aliPayNotifyRequestDto.setPayType(PayType.alipay);
        aliPayNotifyRequestDto.setOriginalMaps(pPayNotifyRequestDto.getOriginalParameters());
        if (NotifyType.SYNC.equals(pPayNotifyRequestDto.getNotifyType())) {
            aliPayNotifyRequestDto.setTradeNo(pPayNotifyRequestDto.get("trade_no"));
            aliPayNotifyRequestDto.setOutTradeNo(pPayNotifyRequestDto.get("out_trade_no"));
            aliPayNotifyRequestDto.setTotalAmount(Double.valueOf(pPayNotifyRequestDto.get("total_amount")));
            aliPayNotifyRequestDto.setGmtPayment(new Date());
        } else {
            if(pPayNotifyRequestDto.get("gmt_payment") != null) {
                aliPayNotifyRequestDto.setGmtPayment(DateUtil.toDate(pPayNotifyRequestDto.get("gmt_payment"), "yyyy-MM-dd HH:mm:ss"));
            }
            aliPayNotifyRequestDto.setNotifyId(pPayNotifyRequestDto.get("notify_id"));
            aliPayNotifyRequestDto.setNotifyTime(pPayNotifyRequestDto.get("notify_time"));
            aliPayNotifyRequestDto.setNotifyType(pPayNotifyRequestDto.getNotifyType());
            aliPayNotifyRequestDto.setTotalAmount(Double.valueOf(pPayNotifyRequestDto.get("total_amount")));
            aliPayNotifyRequestDto.setTradeNo(pPayNotifyRequestDto.get("trade_no"));
            aliPayNotifyRequestDto.setTradeStatus(pPayNotifyRequestDto.get("trade_status"));
            aliPayNotifyRequestDto.setOutTradeNo(pPayNotifyRequestDto.get("out_trade_no"));
            aliPayNotifyRequestDto.setPayAccount(pPayNotifyRequestDto.get("buyer_logon_id"));
        }
        return aliPayNotifyRequestDto;
    }

    public static void validateSign(Map<String, String> returnMap, String pPublicKey, String pSignType) throws ValidationSignException {
        try {
            boolean result = AlipaySignature.rsaCheckV1(returnMap, pPublicKey, "utf-8", pSignType);
            if (!result) {
                throw new ValidationSignException(CommonsEnum.VALIDATION_SIGN_FAIL.getCode(), CommonsEnum.VALIDATION_SIGN_FAIL.getName());
            }
        } catch (AlipayApiException pE) {
            throw new ValidationSignException(CommonsEnum.VALIDATION_SIGN_FAIL.getCode(), CommonsEnum.VALIDATION_SIGN_FAIL.getName(), pE);
        }

    }
}
