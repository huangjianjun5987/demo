package com.yatang.sc.payment.notifier.web.controller;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.payment.dto.request.PayNotifyRequestDto;
import com.yatang.sc.payment.dubbo.service.PaymentDubboService;
import com.yatang.sc.payment.enums.NotifyType;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.notifier.web.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/7.
 */
@Controller
@RequestMapping(value = "/pay")
public class PayNotifyController extends BaseController {

    private final static Map<String, String> VALID_PAY_TYPE_SUCCESS_RETURN = new HashMap<>();
    private final static Map<String, String> VALID_PAY_TYPE_FAIL_RETURN = new HashMap<>();

    @Autowired
    private PaymentDubboService mPaymentDubboService;

    static {
        VALID_PAY_TYPE_SUCCESS_RETURN.put("alipay", "success");
        VALID_PAY_TYPE_SUCCESS_RETURN.put("weixin", "<xml><return_code><![CDATA[SUCCESS]]></return_code> <return_msg><![CDATA[OK]]></return_msg></xml>");
        VALID_PAY_TYPE_SUCCESS_RETURN.put("cmb", "success");
        VALID_PAY_TYPE_SUCCESS_RETURN.put("jd", "ok");
        VALID_PAY_TYPE_SUCCESS_RETURN.put("ytpay", "success");

        VALID_PAY_TYPE_FAIL_RETURN.put("alipay", "fail");
        VALID_PAY_TYPE_FAIL_RETURN.put("weixin", "<xml><return_code><![CDATA[FAIL]]></return_code> <return_msg><![CDATA[支付通知处理失败]]></return_msg></xml>");
        VALID_PAY_TYPE_FAIL_RETURN.put("cmb", "success");
        VALID_PAY_TYPE_FAIL_RETURN.put("jd", "error");
        VALID_PAY_TYPE_FAIL_RETURN.put("ytpay", "fail");
    }

    @RequestMapping("/{payType}/{notifyType}Notify")
    public void notify(@PathVariable("payType") String pType, @PathVariable("notifyType") String pNotifyType,
                       HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
        logger.debug("notify type:{}", pType);
        if (StringUtils.isEmpty(pType) || !VALID_PAY_TYPE_SUCCESS_RETURN.keySet().contains(pType)) {
            pResponse.getOutputStream().write(("Pay type unknown:" + pType).getBytes());
            return;
        }

        PayNotifyRequestDto payNotifyDto = new PayNotifyRequestDto();
        payNotifyDto.setPayType(PayType.parse(pType));
        payNotifyDto.setNotifyType(NotifyType.parse(pNotifyType));
        if (payNotifyDto.getPayType().equals(PayType.weixin)) {
            payNotifyDto.putAll(RequestUtils.createRequestFromStream(pRequest));
        } else {
            logger.info("pay notify params:{}", JSON.toJSONString(pRequest.getParameterMap()));
            payNotifyDto.putAll(RequestUtils.createRequest(pRequest));
        }
        logger.info("Pay notify request params:{}", JSON.toJSONString(payNotifyDto));
        Response<Boolean> payNotifyResponseDto = mPaymentDubboService.payNotify(payNotifyDto);

        boolean isSuccess = payNotifyResponseDto.isSuccess() && payNotifyResponseDto.getResultObject();
        String returnMsg = "fail";
        if (NotifyType.SYNC.equals(payNotifyDto.getNotifyType())) {
            returnMsg = isSuccess ? "success" : "fail";
        } else {
            returnMsg = isSuccess ? VALID_PAY_TYPE_SUCCESS_RETURN.get(pType) : VALID_PAY_TYPE_FAIL_RETURN.get(pType);
        }
        if (!isSuccess) {
            logger.info("支付{}处理处理失败：{}", payNotifyDto.getNotifyType().getName(), payNotifyResponseDto.getErrorMessage());
        }
        pResponse.getOutputStream().write(returnMsg.getBytes());
    }

}
