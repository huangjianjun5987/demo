package com.yatang.sc.payment.flow.weixin;

import com.yatang.sc.payment.common.CommonsEnum;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.dto.request.AbstractPayNotifyRequestDto;
import com.yatang.sc.payment.dto.response.PayStatusQueryResponseDto;
import com.yatang.sc.payment.dto.request.PrePayRequestDto;
import com.yatang.sc.payment.dto.request.WeiXinPayNotifyRequestDto;
import com.yatang.sc.payment.dto.response.WeiXinPrePayResponseDto;
import com.yatang.sc.payment.enums.PayStatus;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.PayWayCode;
import com.yatang.sc.payment.exception.PrePayInfoException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.AbstractPayFacedeService;
import com.yatang.sc.payment.util.WeiXinPayUtils;
import com.yatang.sc.payment.util.XmlUtils;
import com.yatang.sc.payment.vo.WeiXinPrePayRequestVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/8.
 */
@Service("weiXinPayFacedeService")
@Transactional
public class WeiXinPayFacedeService extends AbstractPayFacedeService<PrePayRequestDto, WeiXinPrePayResponseDto> {

    @Override
    protected PaymentRecordPO getPayOrder(PrePayRequestDto pParams) {
        PaymentRecordPO paymentRecordPO = new PaymentRecordPO();
        paymentRecordPO.setOrderNo(pParams.getOutOrderNo());
        paymentRecordPO.setPayStatus(PayStatus.REQUEST);
        paymentRecordPO.setPayTypeCode(PayType.weixin.getCode());
        paymentRecordPO.setPayTypeName(PayType.weixin.getName());
        paymentRecordPO.setPayWayCode(PayWayCode.APP_PAY.getCode());
        paymentRecordPO.setPayWayName(PayWayCode.APP_PAY.getName());
        paymentRecordPO.setTotalFee(pParams.getTotalFee());
        paymentRecordPO.setRequestFrom(pParams.getSpbillCreateIp());
        paymentRecordPO.setNonceStr(pParams.getNonceStr());
        return paymentRecordPO;
    }

    @Override
    protected WeiXinPrePayResponseDto requestPrePayInfo(PrePayRequestDto pParams, String pPayNo) throws PrePayInfoException, ValidationSignException {
        if (pParams == null) {
            mLogger.warn("WeiXin requestPrePayInfo param is null.");
            throw new PrePayInfoException("参数错误,参数为空.");
        }

        mLogger.info("WeiXin requestPrePayInfo param:{}", pParams.toString());

        PaymentConfigPO paymentConfigPO = mPaymentConfigService.getConfigByType(PayType.weixin);
        if (paymentConfigPO == null) {
            mLogger.error("Can not get any Weixin pay config form database.");
            throw new PrePayInfoException(CommonsEnum.PAY_CONFIG_NONE_EXIST.getCode(), CommonsEnum.PAY_CONFIG_NONE_EXIST.getName());
        }

        if("test".equals(devEnvironment)){
            pParams.setTotalFee(0.01D);
        }
        WeiXinPrePayRequestVO weiXinPrePayRequestVO = new WeiXinPrePayRequestVO();
        weiXinPrePayRequestVO.setAppId(paymentConfigPO.getAppId());
        weiXinPrePayRequestVO.setMchId(paymentConfigPO.getMercId());
        weiXinPrePayRequestVO.setNotifyUrl(paymentConfigPO.getAsynNotifyUrl());
        weiXinPrePayRequestVO.setOutTradeNo(pPayNo);
        weiXinPrePayRequestVO.setSpbillCreateIp(pParams.getSpbillCreateIp());
        weiXinPrePayRequestVO.setTotalFee(convertUnitToOutside(Double.valueOf(pParams.getTotalFee())));//微信金额单位为分
        weiXinPrePayRequestVO.setTradeType(pParams.getPayWayCode().getCode());
        weiXinPrePayRequestVO.setBody(pParams.getBody());

        if (pParams.getBody().equals("exception")) {
            throw new PrePayInfoException("测试异常情况");
        }

        Map<String, String> params = WeiXinPayUtils.convertPrePayParamToMap(weiXinPrePayRequestVO, paymentConfigPO.getSignKey());

        String prePayApi = paymentConfigPO.getPrePayApi();
        if (!paymentConfigPO.getPrePayApi().contains(paymentConfigPO.getPayServer())) {
            prePayApi = paymentConfigPO.getPayServer() + paymentConfigPO.getPrePayApi();
        }
        String response = null;
        try {
            response = WeiXinPayUtils.sendRequest(prePayApi, XmlUtils.mapToXml(params));
        } catch (IOException pE) {
            mLogger.error("调用微信接口异常", pE);
            throw new PrePayInfoException("调用微信接口异常");
        }
        mLogger.info("WeiXin requestPrePayInfo xmlResponse: " + response);
        if (StringUtils.isEmpty(response)) {
            throw new PrePayInfoException("微信支付接口请求出错.");
        }

        Map<String, String> responseMap = XmlUtils.xml2Map(response, "xml");
        if (responseMap == null || responseMap.isEmpty()) {
            throw new PrePayInfoException("微信支付获取预支付信息失败.");
        }
        if ("FAIL".equals(responseMap.get("return_code"))) {
            throw new PrePayInfoException(responseMap.get("return_msg"));
        }

        WeiXinPayUtils.validateSign(responseMap, paymentConfigPO.getSignKey());

        String returnCode = responseMap.get("return_code");
        String resultCode = responseMap.get("result_code");
        if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
            // 组装调用微信APP支付所需要的信息
            Map<String, String> payInfoMap = new HashMap<String, String>();
            payInfoMap.put("prepayid", responseMap.get("prepay_id"));
            payInfoMap.put("noncestr", RandomStringUtils.randomAlphanumeric(32));
            payInfoMap.put("appid", paymentConfigPO.getAppId());
            payInfoMap.put("partnerid", paymentConfigPO.getMercId());
            payInfoMap.put("package", "Sign=WXPay");
            payInfoMap.put("timestamp", Long.toString(System.currentTimeMillis() / 1000));
            String signStr = WeiXinPayUtils.createSignStr(payInfoMap, paymentConfigPO.getSignKey());
            payInfoMap.put("sign", signStr);


            WeiXinPrePayResponseDto weiXinPrePayResponseDto = new WeiXinPrePayResponseDto();
            weiXinPrePayResponseDto.setPrepayid(payInfoMap.get("prepayid"));
            weiXinPrePayResponseDto.setNnoncestr(payInfoMap.get("noncestr"));
            weiXinPrePayResponseDto.setAppid(payInfoMap.get("appid"));
            weiXinPrePayResponseDto.setPartnerid(payInfoMap.get("partnerid"));
            weiXinPrePayResponseDto.setPackage(payInfoMap.get("package"));
            weiXinPrePayResponseDto.setTimestamp(payInfoMap.get("timestamp"));
            weiXinPrePayResponseDto.setSign(payInfoMap.get("sign"));
            return weiXinPrePayResponseDto;
        } else {
            mLogger.error("微信支付获取预支付信息失败.");
            throw new PrePayInfoException(responseMap.get("err_code_des"));
        }
    }

    @Override
    protected void validateNotifyParameterType(AbstractPayNotifyRequestDto pPayNotifyRequest) {
        if (!(pPayNotifyRequest instanceof WeiXinPayNotifyRequestDto)) {
            throw new IllegalArgumentException("参数类型错误.");
        }
    }

    @Override
    protected void validateNotifySign(AbstractPayNotifyRequestDto pPayNotifyRequest, PaymentConfigPO pPaymentConfigPO) throws ValidationSignException {
        WeiXinPayUtils.validateSign(pPayNotifyRequest.getOriginalMaps(), pPaymentConfigPO.getSignKey());
    }

    @Override
    protected boolean judgePayTradeSuccess(AbstractPayNotifyRequestDto pPayNotifyRequest) {
        WeiXinPayNotifyRequestDto weiXinPayNotifyRequestDto = (WeiXinPayNotifyRequestDto) pPayNotifyRequest;
        if ("SUCCESS".equals(weiXinPayNotifyRequestDto.getReturnCode())
                && "SUCCESS".equals(weiXinPayNotifyRequestDto.getResultCode())) {
            return true;
        }
        return false;
    }

    @Override
    protected PayStatusQueryResponseDto requestPayStatus(String pPayNo) throws Exception {
        PaymentConfigPO paymentConfigPO = mPaymentConfigService.getConfigByType(PayType.weixin);
        if (paymentConfigPO == null) {
            mLogger.error("Can not get any WeiXin pay config form database.");
            throw new Exception("微信支付未配置支付信息.");
        }

        PayStatusQueryResponseDto payStatusQueryResponseDto = new PayStatusQueryResponseDto();
        Map<String, String> paramMap = WeiXinPayUtils.convertPayStatusQueryMap(pPayNo, paymentConfigPO);
        String xmlParam = XmlUtils.mapToXml(paramMap);
        String queryPayStatusApi = paymentConfigPO.getQueryPayStatusApi();
        if (!paymentConfigPO.getQueryPayStatusApi().contains(paymentConfigPO.getPayServer())) {
            queryPayStatusApi = paymentConfigPO.getPayServer() + paymentConfigPO.getQueryPayStatusApi();
        }
        String xmlResponse = WeiXinPayUtils.sendRequest(queryPayStatusApi, new String(xmlParam.toString().getBytes("UTF-8"), "ISO8859-1"));
        if (StringUtils.isEmpty(xmlResponse)) {
            throw new Exception("微信支付接口请求出错.");
        }

        Map<String, String> responseMap = XmlUtils.xml2Map(xmlResponse, "xml");
        if (responseMap == null || responseMap.isEmpty()) {
            throw new Exception("微信支付获取预支付信息失败.");
        }
        WeiXinPayUtils.validateSign(responseMap, paymentConfigPO.getSignKey());

        String returnCode = responseMap.get("return_code");
        String resultCode = responseMap.get("result_code");
        if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
            String tradeState = responseMap.get("trade_state");
            if ("SUCCESS".equals(tradeState)) {
                payStatusQueryResponseDto.setTradeNo(responseMap.get("transaction_id"));
                payStatusQueryResponseDto.setPayNo(responseMap.get("out_trade_no"));
                payStatusQueryResponseDto.setTotalAmount(Double.valueOf(responseMap.get("total_fee")) / 100);
                payStatusQueryResponseDto.setTradeNo(responseMap.get("transaction_id"));
                payStatusQueryResponseDto.setPayEndTime(responseMap.get("time_end"));
            } else {
                payStatusQueryResponseDto.setSuccess(false);
                payStatusQueryResponseDto.setErrorMsg(responseMap.get("trade_state_desc"));
            }
        } else {
            payStatusQueryResponseDto.setSuccess(false);
            payStatusQueryResponseDto.setErrorMsg("微信接口查询失败");
        }
        return payStatusQueryResponseDto;
    }

    protected Double convertUnitToLocal(Double pAmount) {
        if (pAmount == null) {
            return 0D;
        }
        return pAmount / 100D;
    }

    protected Double convertUnitToOutside(Double pAmount) {
        if (pAmount == null) {
            return 0D;
        }
        return pAmount * 100D;
    }
}
