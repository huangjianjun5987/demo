package com.yatang.sc.payment.util;

import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.dto.request.PayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.YTPayNotifyRequestDto;
import com.yatang.sc.payment.enums.PayType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/14.
 */
public class YTPayUtils {
    private static final Logger log = LoggerFactory.getLogger("YTPayUtils");

    public static String getToken(Map<String, String> params, PaymentConfigPO pPaymentConfigPO) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder preStr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("token".equalsIgnoreCase(key)) {
                continue;
            }
            String value = params.get(key).toString();
           /* if (StringUtil.isEmpty(value)) {
                continue;
            }*/
            preStr.append(key).append("=").append(value).append("&");
        }
        int lastIndex = preStr.lastIndexOf("&");
        String prePayParamStr = preStr.substring(0, lastIndex);
        String prePayStr = pPaymentConfigPO.getAppId() + params.get("datetime") + prePayParamStr + pPaymentConfigPO.getSignKey();
        log.info("待加密：{}", prePayStr);
        String md5Str = Md5Utils.getEncode(prePayStr);
        return md5Str;
    }

    public static YTPayNotifyRequestDto convertNotifyParamToBean(PayNotifyRequestDto pPayNotifyDto) {
        if (pPayNotifyDto == null) {
            return null;
        }
        YTPayNotifyRequestDto requestDto = new YTPayNotifyRequestDto();
        requestDto.setPayType(PayType.ytpay);

        requestDto.setOriginalMaps(pPayNotifyDto.getOriginalParameters());
        requestDto.setTradeStatus(pPayNotifyDto.get("pay_status"));
        if (requestDto.getTradeStatus() == null) {
            requestDto.setTradeStatus(pPayNotifyDto.get("status"));
        }
        requestDto.setOutTradeNo(pPayNotifyDto.get("order_sn"));
        requestDto.setTradeNo(pPayNotifyDto.get("pay_num"));
        requestDto.setTotalAmount(Double.valueOf(pPayNotifyDto.get("money")));
        requestDto.setGmtPayment(new Date());
        return requestDto;
    }

}
