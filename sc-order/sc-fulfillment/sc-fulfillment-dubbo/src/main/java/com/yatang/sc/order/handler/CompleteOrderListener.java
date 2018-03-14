package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiangyonghong on 10/27/2017.
 */
@Service("completeOrderListener")
public class CompleteOrderListener extends OrderMsgListener {
    protected Logger log = LoggerFactory.getLogger(CompleteOrderListener.class);

    @Autowired
    private OrderFulfillerDubboService mOrderFulfillerDubboService;

    @Override
    public void handle(OrderMessage msg) {
        log.info("订单签收,param{}", JSON.toJSONString(msg));
        OrderReceiveDto orderReceiveDto = new OrderReceiveDto();
        orderReceiveDto.setOrderId(msg.getOrderId());
        Response<Boolean> rusult = mOrderFulfillerDubboService.orderReceive(orderReceiveDto);
        log.info("订单签收,result{}",JSON.toJSONString(rusult));
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.CompleteOrder.equals(msg.getMssageType());
    }
}
