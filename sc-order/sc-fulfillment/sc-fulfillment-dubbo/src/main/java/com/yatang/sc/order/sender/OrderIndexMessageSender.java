package com.yatang.sc.order.sender;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.busi.mq.producer.SimpleMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiangyonghong on 9/30/2017.
 */
@Service("orderIndexMessageSender")
public class OrderIndexMessageSender {
    private Logger log = LoggerFactory.getLogger(OrderIndexMessageSender.class);

    @Resource(name = "orderIndexMQProducer")
    SimpleMQProducer orderIndexMQProducer;


    public void sendOrderIndexMsg(OrderIndexMessage msg){
        SendResult sendResult = orderIndexMQProducer.sendOrderlyMsg(msg.getId(), msg);
        if (sendResult == null) {
            log.error("MQ order index message send failed:{}", msg);
        }
    }

}
