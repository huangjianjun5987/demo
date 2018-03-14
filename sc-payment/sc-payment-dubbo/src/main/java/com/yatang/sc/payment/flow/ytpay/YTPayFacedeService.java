package com.yatang.sc.payment.flow.ytpay;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.order.dubboservice.WebOrderDubboService;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.dto.request.AbstractPayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.PrePayRequestDto;
import com.yatang.sc.payment.dto.request.YTPayNotifyRequestDto;
import com.yatang.sc.payment.dto.response.PayStatusQueryResponseDto;
import com.yatang.sc.payment.dto.response.YTPrePayResponseDto;
import com.yatang.sc.payment.enums.PayStatus;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.PayWayCode;
import com.yatang.sc.payment.exception.PrePayInfoException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.AbstractPayFacedeService;
import com.yatang.sc.payment.util.DateUtil;
import com.yatang.sc.payment.util.YTPayUtils;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/14.
 */
@Service("ytPayFacedeService")
@Transactional
public class YTPayFacedeService extends AbstractPayFacedeService<PrePayRequestDto, YTPrePayResponseDto> {

    @Autowired
    private WebOrderDubboService mWebOrderDubboService;

    @Autowired
    private OrganizationService mOrganizationService;

    @Override
    protected PaymentRecordPO getPayOrder(PrePayRequestDto pParams) {
        PaymentRecordPO paymentRecordPO = new PaymentRecordPO();
        paymentRecordPO.setOrderNo(pParams.getOutOrderNo());
        paymentRecordPO.setPayStatus(PayStatus.REQUEST);
        paymentRecordPO.setPayTypeCode(PayType.ytpay.getCode());
        paymentRecordPO.setPayTypeName(PayType.ytpay.getName());
        paymentRecordPO.setPayWayCode(PayWayCode.APP_PAY.getCode());
        paymentRecordPO.setPayWayName(PayWayCode.APP_PAY.getName());
        paymentRecordPO.setTotalFee(pParams.getTotalFee());
        paymentRecordPO.setRequestFrom(pParams.getSpbillCreateIp());
        paymentRecordPO.setNonceStr(pParams.getNonceStr());
        Response<String> orderProfileResp = mWebOrderDubboService.queryProfileIdByOrderId(paymentRecordPO.getOrderNo());
        mLogger.info("余额支付订单:{}-->{}", paymentRecordPO.getOrderNo(), JSON.toJSONString(orderProfileResp));
        Response<FranchiseeDto> response = mOrganizationService.querySimpleById(orderProfileResp.getResultObject());
        if (response != null && response.isSuccess() && response.getResultObject() != null) {
            pParams.setUserName(response.getResultObject().getYtAccount());
        }
       /* if ("test".equals(devEnvironment)) {
            pParams.setUserName("小小面条");
        }*/
        paymentRecordPO.setPayAccount(pParams.getUserName());

        return paymentRecordPO;
    }

    @Override
    protected YTPrePayResponseDto requestPrePayInfo(PrePayRequestDto pParam, String pPayNo) throws PrePayInfoException {
        if (pParam == null) {
            mLogger.warn("YTPay requestPrePayInfo param is null.");
            throw new PrePayInfoException("参数错误,参数为空.");
        }

        mLogger.info("YTPay requestPrePayInfo param:{}", pParam.toString());

        PaymentConfigPO paymentConfigPO = mPaymentConfigService.getConfigByType(PayType.ytpay);
        if (paymentConfigPO == null) {
            mLogger.error("Can not get any YTPay pay config form database.");
            throw new PrePayInfoException("雅堂余额支付未配置支付信息.");
        }
        if ("test".equals(devEnvironment)) {
            pParam.setTotalFee(0.01D);
        }
        YTPrePayResponseDto ytPrePayResponseDto = new YTPrePayResponseDto();
        ytPrePayResponseDto.setApiUrl(paymentConfigPO.getPayServer() + paymentConfigPO.getPrePayApi());
        ytPrePayResponseDto.setType(4);
        ytPrePayResponseDto.setDatetime(DateUtil.getFormatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        ytPrePayResponseDto.setUser_name(pParam.getUserName());
        ytPrePayResponseDto.setShop_user_name(paymentConfigPO.getMercId());
        ytPrePayResponseDto.setOrder_amount(String.valueOf(pParam.getTotalFee()));
        ytPrePayResponseDto.setOrder_sn(pPayNo);
        ytPrePayResponseDto.setBusiness_type(2);
        ytPrePayResponseDto.setGoods_name(pParam.getBody());
        ytPrePayResponseDto.setSync_url(paymentConfigPO.getAsynNotifyUrl().replace("asyncNotify", "syncNotify"));
        ytPrePayResponseDto.setAsync_url(paymentConfigPO.getAsynNotifyUrl());

        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("type", "4");
        requestParam.put("datetime", ytPrePayResponseDto.getDatetime());
        requestParam.put("user_name", ytPrePayResponseDto.getUser_name());
        requestParam.put("shop_user_name", ytPrePayResponseDto.getShop_user_name());
        requestParam.put("order_amount", String.valueOf(ytPrePayResponseDto.getOrder_amount()));
        requestParam.put("order_sn", ytPrePayResponseDto.getOrder_sn());
        requestParam.put("business_type", "2");
        requestParam.put("goods_name", ytPrePayResponseDto.getGoods_name());
        requestParam.put("sync_url", ytPrePayResponseDto.getSync_url());
        requestParam.put("async_url", ytPrePayResponseDto.getAsync_url());

        ytPrePayResponseDto.setToken(YTPayUtils.getToken(requestParam, paymentConfigPO));
        return ytPrePayResponseDto;
    }

    @Override
    protected void validateNotifyParameterType(AbstractPayNotifyRequestDto pPayNotifyRequest) {
        if (!(pPayNotifyRequest instanceof YTPayNotifyRequestDto)) {
            throw new IllegalArgumentException("参数类型错误.");
        }
    }

    @Override
    protected void validateNotifySign(AbstractPayNotifyRequestDto pPayNotifyRequest, PaymentConfigPO pPaymentConfigPO) throws ValidationSignException {
        String token = YTPayUtils.getToken(pPayNotifyRequest.getOriginalMaps(), pPaymentConfigPO);
        mLogger.info("金融余额支付回调验签->本地：{},回传token:{}", token, pPayNotifyRequest.getOriginalMaps().get("token"));
        if (!token.equalsIgnoreCase(pPayNotifyRequest.getOriginalMaps().get("token"))) {
            throw new ValidationSignException("验签失败");
        }
    }

    @Override
    protected boolean judgePayTradeSuccess(AbstractPayNotifyRequestDto pPayNotifyRequest) {
        YTPayNotifyRequestDto ytPayNotifyRequestDto = (YTPayNotifyRequestDto) pPayNotifyRequest;
        mLogger.info("余额支付状态：{}", ytPayNotifyRequestDto.getTradeStatus());
        return "1".equalsIgnoreCase(ytPayNotifyRequestDto.getTradeStatus());
    }

    @Override
    protected PayStatusQueryResponseDto requestPayStatus(String pPayNo) throws Exception {
        PayStatusQueryResponseDto payStatusQueryResponseDto = new PayStatusQueryResponseDto();
        payStatusQueryResponseDto.setSuccess(false);
        payStatusQueryResponseDto.setErrorMsg("暂不支持雅堂金融余额支付查询");
        return null;
    }
}
