package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.purchase.dto.AddPaymentInfoDto;
import com.yatang.sc.purchase.dto.ConfirmPaymentDto;
import com.yatang.sc.purchase.dubboservice.AddPaymentInfoDubboService;
import com.yatang.sc.purchase.flow.AddPaymentInfoFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addPaymentInfoDubboService")
public class AddPaymentInfoDubboServiceImpl implements AddPaymentInfoDubboService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    private AddPaymentInfoFlow addPaymentInfoFlow;

    @Override
    public Response<String> addPaymentInfo(AddPaymentInfoDto addPaymentInfoDto) {
        logger.info("/addPaymentInfo:request param{}", JSONObject.toJSON(addPaymentInfoDto));
        Response<String> resultResponse = new Response<String>();
        try {
            resultResponse.setResultObject(addPaymentInfoFlow.addPaymentInfo(addPaymentInfoDto));
            resultResponse.setSuccess(true);
        } catch (Exception ex) {
            logger.error("保存支付异常", ex);
            resultResponse.setSuccess(false);
            resultResponse.setErrorMessage(ex.getMessage());
        }
        return resultResponse;
    }

    @Override
    public Response<Boolean> confirmOfflinePayment(ConfirmPaymentDto confirmPaymentDto) {
        logger.info("/confirmOfflinePayment:request param{}", JSONObject.toJSON(confirmPaymentDto));
        Response<Boolean> resultResponse = new Response<Boolean>();
        try {
            addPaymentInfoFlow.confirmOfflinePayment(confirmPaymentDto);

            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.ORDER_PAY_SUCCESS_CONFIRM);
            wmsMessage.setOrderId(confirmPaymentDto.getOrderId());
            orderMessageSender.sendMsg(wmsMessage);
            resultResponse.setSuccess(true);
        } catch (Exception pE) {
            logger.error("确认支付异常", pE);
            resultResponse.setSuccess(false);
            resultResponse.setErrorMessage(pE.getMessage());
        }
        return resultResponse;
    }
}
