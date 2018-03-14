package com.yatang.sc.order.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.order.provider.order.ProviderOrderMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by qiugang on 7/24/2017.
 */
@Service("orderMessageSender")
public class OrderMessageSender {
    private Logger log = LoggerFactory.getLogger(OrderMessageSender.class);
    @Resource(name = "orderSimpleMQProducer")
    SimpleMQProducer simpleMQProducer;


    @Resource(name = "orderUpdateMQProducer")
    SimpleMQProducer orderUpdateMQProducer;

    public void sendMsg(OrderMessage msg) {
        log.info("send order message:{}", msg);
        SendResult sendResult = simpleMQProducer.sendOrderlyMsg(msg.getOrderId(), msg);
        if (sendResult == null) {
            log.error("MQ message send failed:{}", msg);
        }
    }

    /**
     * 供应商系统平台发生直配订单MQ消息
     *
     * @param orderId
     * @param body
     * @param <T>
     */
    public <T extends ProviderOrderMsg> void sendProviderOrderMsg(String orderId, T body) {
        log.info("send order message:{}-->{}", orderId, JSON.toJSONString(body));
        OrderMessage msg = new OrderMessage();
        msg.setOrderId(orderId);
        msg.setMssageType(OrderMessageType.PROVIDER_ORDER_STATUE_NOTIFY);
        msg.setBody(JSON.toJSONString(body));
        SendResult sendResult = simpleMQProducer.sendOrderlyMsg(msg.getOrderId(), msg);
        if (sendResult == null) {
            log.error("MQ message send failed:{}", msg);
        }
    }

    public void sendOrderUpdateMsg(OrderMessage msg) {
        log.info("send order update message:{}", msg);
        SendResult sendResult = orderUpdateMQProducer.sendOrderlyMsg(msg.getOrderId(), msg);
        if (sendResult == null) {
            log.error("MQ order update message send failed:{}", msg);
        }
    }
}
