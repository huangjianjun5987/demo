package com.yatang.sc.payment.flow.ytpay;

import com.busi.common.resp.Response;
import com.github.pagehelper.StringUtil;
import com.yatang.sc.order.dubboservice.WebOrderDubboService;
import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.dto.response.RefundResponseDto;
import com.yatang.sc.payment.exception.RefundException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.flow.AbstractRefundFacedService;
import com.yatang.sc.payment.util.DateUtil;
import com.yatang.sc.purchase.dto.OMSCommerceItemDto;
import com.yatang.sc.purchase.dto.OrderManageDetailDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ytRefundFacedeService")
public class YTRefundFacedeService extends AbstractRefundFacedService {

    public static final String RESULT_CODE_SUCCESS_ONE = "1";
    public static final String RESULT_CODE_SUCCESS_TWO = "2";

    @Autowired
    private WebOrderDubboService mWebOrderDubboService;

    @Override
    protected RefundResponseDto requestRefund(PayRefundPO pRefundPO, PaymentConfigPO pPaymentConfigPO, PayRefundPO payRefundPO) throws RefundException, ValidationSignException {
        RefundResponseDto refundResponseDto = new RefundResponseDto();
        refundResponseDto.setSuccess(false);

        PaymentRecordPO orderPO = mPaymentRecordService.queryPayRecordsByPayNo(payRefundPO.getPayNo());
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("username", orderPO.getPayAccount());
        requestParams.put("order", orderPO.getOrderNo());
        requestParams.put("parentOrderId", "");
        requestParams.put("money", pRefundPO.getRefundAmount());
        requestParams.put("refund_no", payRefundPO.getRefundNo());
        requestParams.put("trade_no", payRefundPO.getPayTradeNo());

        Response<OrderManageDetailDto> orderDtoResponse = mWebOrderDubboService.queryOrderDeatil(payRefundPO.getOrderNo());
        if (!orderDtoResponse.isSuccess()) {
            refundResponseDto.setSuccess(false);
            refundResponseDto.setMsg("订单系统未查询到订单:" + payRefundPO.getOrderNo());
            return refundResponseDto;
        }
        List<String> skuIds = new ArrayList<>();
        for (OMSCommerceItemDto commerceItemDto : orderDtoResponse.getResultObject().getItems()) {
            skuIds.add(commerceItemDto.getSkuId());
        }
        requestParams.put("skuid", StringUtils.join(skuIds, ","));

        requestParams.put("pay_type", "雅堂金融余额支付");
        requestParams.put("pay_time", DateUtil.getDateInSeconds(orderPO.getPayTime()));
        requestParams.put("check_time", DateUtil.getDateInSeconds(payRefundPO.getApprovedTime()));
        requestParams.put("rap_time", DateUtil.getDateInSeconds(payRefundPO.getCreateTime()));

        try {
            //String response = YTPayUtils.sendRefundRequest(requestParams, pPaymentConfigPO);
           // mLogger.debug("退款接口返回信息：{}", response);
            Map<String, Object> responseMap = new HashMap<>();//JSON.parseObject(response, Map.class);
            String resultCode = (String) responseMap.get("code");
            if (!StringUtil.isEmpty(resultCode)) {
                if (RESULT_CODE_SUCCESS_ONE.equals(resultCode)) {
                    refundResponseDto.setMsg("订单退款申请金额已退到金融账户");
                    refundResponseDto.setSuccess(true);
                } else if (RESULT_CODE_SUCCESS_TWO.equals(resultCode)) {
                    refundResponseDto.setMsg((String) responseMap.get("info"));
                    refundResponseDto.setSuccess(true);
                } else {
                    refundResponseDto.setMsg((String) responseMap.get("info"));
                    refundResponseDto.setSuccess(false);
                }
            } else {
                refundResponseDto.setSuccess(false);
                refundResponseDto.setMsg("金融返回处理结果数据异常:" + resultCode);
            }
        } catch (Exception pE) {
            mLogger.error("退款异常", pE);
            refundResponseDto.setMsg("退款异常");
        }
        return refundResponseDto;
    }
}
