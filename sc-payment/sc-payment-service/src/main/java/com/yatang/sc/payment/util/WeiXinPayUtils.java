package com.yatang.sc.payment.util;

import com.github.pagehelper.StringUtil;
import com.yatang.sc.payment.common.CommonsEnum;
import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.dto.request.PayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.WeiXinPayNotifyRequestDto;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.vo.WeiXinPrePayRequestVO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by yuwei on 2017/7/8.
 */
public class WeiXinPayUtils {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinPayUtils.class.getClass());

    private static final OkHttpClient client = new OkHttpClient();
    private static OkHttpClient sslClient;


    public static String sendRequest(String pGateWay, String pRequestBody) throws IOException {
        logger.info("WeiXin pay request:{}", pRequestBody);

        MediaType xml = MediaType.parse("text/xml; charset=utf-8");
        RequestBody requestBody = RequestBody.create(xml, pRequestBody);
        Request request = new Request.Builder()
                .addHeader("charset", "ISO8859-1")
                .url(pGateWay)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            logger.info("WeiXin request failure,response code:{}", response.code());
            return null;
        }
        String responseStr = response.body().string();
        logger.info("WeiXin send request get response:{}", responseStr);
        return responseStr;
    }

    public static String sendRequestViaSSL(String pGateWay, String pRequestBody, String certPassword, String certPath) throws IOException, ExecutionException {
        logger.info("WeiXin pay request vis ssl:{}", pRequestBody);
        sslClient = HTTPSUtils.getSSLClient(certPath, certPassword);
        MediaType xml = MediaType.parse("text/xml; charset=utf-8");
        RequestBody requestBody = RequestBody.create(xml, new String(pRequestBody.getBytes("UTF-8"), "ISO8859-1"));
        Request request = new Request.Builder()
                .addHeader("charset", "ISO8859-1")
                .url(pGateWay)
                .post(requestBody)
                .build();
        Response response = sslClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            logger.info("WeiXin send request occur network issue,response code:{}", response.code());
            return null;
        }
        String responseStr = response.body().string();
        logger.info("WeiXin ssl get response:{}", responseStr);
        return responseStr;
    }


    public static Map<String, String> convertPrePayParamToMap(WeiXinPrePayRequestVO pWeiXinPrePayRequestVO, String pSignKey) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", pWeiXinPrePayRequestVO.getAppId());
        params.put("body", pWeiXinPrePayRequestVO.getBody());
        params.put("mch_id", pWeiXinPrePayRequestVO.getMchId());
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(32));
        params.put("notify_url", pWeiXinPrePayRequestVO.getNotifyUrl());
        params.put("out_trade_no", pWeiXinPrePayRequestVO.getOutTradeNo());
        params.put("spbill_create_ip", pWeiXinPrePayRequestVO.getSpbillCreateIp());
        params.put("total_fee", String.valueOf(new BigDecimal(pWeiXinPrePayRequestVO.getTotalFee()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()));
        params.put("trade_type", pWeiXinPrePayRequestVO.getTradeType());
        String signStr = createSignStr(params, pSignKey);
        params.put("sign", signStr);
        return params;
    }

    public static String createSignStr(Map<String, String> params, String signKey) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder preStr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key).toString();
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            preStr.append(key).append("=").append(value).append("&");
        }
        preStr.append("key=").append(signKey);
        String md5_str = Md5Utils.getEncode(preStr.toString()).toUpperCase();
        return md5_str;
    }


    public static void validateSign(Map<String, String> returnMap, String key) throws ValidationSignException {
        String oldSign = returnMap.get("sign");
        returnMap.remove("sign");
        String signStr = WeiXinPayUtils.createSignStr(returnMap, key);
        if (!signStr.equalsIgnoreCase(oldSign)) {
            throw new ValidationSignException(CommonsEnum.VALIDATION_SIGN_FAIL.getCode(), CommonsEnum.VALIDATION_SIGN_FAIL.getName());
        }
    }

    public static WeiXinPayNotifyRequestDto convertNotifyParamToBean(PayNotifyRequestDto pPayNotifyRequestDto) {
        if (pPayNotifyRequestDto == null) {
            return null;
        }
        WeiXinPayNotifyRequestDto weiXinPayNotifyRequestDto = new WeiXinPayNotifyRequestDto();
        weiXinPayNotifyRequestDto.setPayType(PayType.weixin);
        weiXinPayNotifyRequestDto.setOriginalMaps(pPayNotifyRequestDto.getOriginalParameters());


        weiXinPayNotifyRequestDto.setReturnCode(pPayNotifyRequestDto.get("return_code"));
        weiXinPayNotifyRequestDto.setReturnMsg(pPayNotifyRequestDto.get("return_msg"));
        weiXinPayNotifyRequestDto.setAppId(pPayNotifyRequestDto.get("appid"));
        weiXinPayNotifyRequestDto.setMchId(pPayNotifyRequestDto.get("mch_id"));
        weiXinPayNotifyRequestDto.setResultCode(pPayNotifyRequestDto.get("result_code"));
        weiXinPayNotifyRequestDto.setErrCode(pPayNotifyRequestDto.get("err_code"));
        weiXinPayNotifyRequestDto.setErrCodeDes(pPayNotifyRequestDto.get("err_code_des"));
        weiXinPayNotifyRequestDto.setTradeType(pPayNotifyRequestDto.get("trade_type"));
        weiXinPayNotifyRequestDto.setTotalAmount(Double.valueOf(pPayNotifyRequestDto.get("total_fee")));
        weiXinPayNotifyRequestDto.setTradeNo(pPayNotifyRequestDto.get("transaction_id"));
        weiXinPayNotifyRequestDto.setOutTradeNo(pPayNotifyRequestDto.get("out_trade_no"));
        weiXinPayNotifyRequestDto.setTimeEnd(pPayNotifyRequestDto.get("time_end"));
        weiXinPayNotifyRequestDto.setPayAccount("");//微信通知未返回支付账号信息
        weiXinPayNotifyRequestDto.setGmtPayment(DateUtil.toDate(pPayNotifyRequestDto.get("time_end"),"yyyyMMddHHmmss"));

        return weiXinPayNotifyRequestDto;
    }

    public static Map<String, String> convertPayStatusQueryMap(String pPayNo, PaymentConfigPO pPaymentConfigPO) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", pPaymentConfigPO.getAppId());
        params.put("mch_id", pPaymentConfigPO.getMercId());
        params.put("out_trade_no", pPayNo);
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(32));
        String signStr = createSignStr(params, pPaymentConfigPO.getSignKey());
        params.put("sign", signStr);
        return params;
    }

    public static Map<String, String> convertRefundParamToMap(PayRefundPO refundPO, PaymentConfigPO pPaymentConfigPO) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", pPaymentConfigPO.getAppId());
        params.put("mch_id", pPaymentConfigPO.getMercId());
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(32));
        params.put("transaction_id", refundPO.getPayTradeNo());
        params.put("out_refund_no", refundPO.getRefundNo());
        DecimalFormat zeroDigits = new DecimalFormat("0");
        params.put("total_fee", zeroDigits.format(refundPO.getTotalPaiedAmount() * 100));
        params.put("refund_fee", zeroDigits.format(refundPO.getRefundAmount() * 100));
        params.put("op_user_id", pPaymentConfigPO.getMercId());
        String signStr = createSignStr(params, pPaymentConfigPO.getSignKey());
        params.put("sign", signStr);
        return params;
    }
}
