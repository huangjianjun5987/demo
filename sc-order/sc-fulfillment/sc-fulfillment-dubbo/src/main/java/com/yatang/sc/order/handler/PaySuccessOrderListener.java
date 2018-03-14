package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.dto.PaymentResultDto;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.service.PaySuccessHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付成功，检查其他订单状态是否满足发生订单到WMS
 */
@Service("paySuccessOrderListener")
public class PaySuccessOrderListener extends OrderMsgListener {
    protected Logger log = LoggerFactory.getLogger(PaySuccessOrderListener.class);

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    private PaySuccessHelper mPaySuccessHelper;

    @Override
    public void handle(OrderMessage msg) throws Exception {
        log.info("handle msg order id: " + msg.getOrderId());

        PaymentResultDto paymentResultDto = JSON.parseObject(msg.getBody(), PaymentResultDto.class);
        if (OrderMessageType.ORDER_PAY_SUCCESS.equals(msg.getMssageType())) {
            mPaySuccessHelper.processForPay(paymentResultDto);
        } else {
            mPaySuccessHelper.confirmPay(msg.getOrderId());
        }

        log.info("update order payment status completed:{}", msg.getOrderId());
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.ORDER_PAY_SUCCESS.equals(msg.getMssageType()) || OrderMessageType.ORDER_PAY_SUCCESS_CONFIRM.equals(msg.getMssageType());
    }
}
