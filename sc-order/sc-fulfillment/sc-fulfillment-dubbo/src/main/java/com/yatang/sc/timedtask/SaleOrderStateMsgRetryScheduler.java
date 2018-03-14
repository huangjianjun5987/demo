package com.yatang.sc.timedtask;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.order.domain.MsgRetryQueue;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.processor.OrderMsgProcessor;
import com.yatang.sc.order.service.MsgRetryQueueService;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yuwei on 2017/11/03.
 * 定时程序用于重新执行消息队列表中未成功消费的消息
 */
@Component("saleOrderStateMsgRetryScheduler")
public class SaleOrderStateMsgRetryScheduler implements Scheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MsgRetryQueueService mMsgRetryQueueService;

    @Resource(name = "orderMsgProcessor")
    private OrderMsgProcessor mOrderMsgProcessor;

    @Override
    public void execute() {
        logger.debug("START saleOrderStateMsgRetryScheduler......");
        List<String> uniqueIds = mMsgRetryQueueService.selectWZXUniqueIds();
        if (CollectionUtils.isEmpty(uniqueIds)) {
            logger.info("无-重新执行的消息存在");
            return;
        }
        List<MsgRetryQueue> msgRetryQueues;
        for (String uniqueId : uniqueIds) {
            msgRetryQueues = mMsgRetryQueueService.selectByUniqueId(uniqueId);
            if (CollectionUtils.isEmpty(msgRetryQueues)) {
                logger.info("无消息队列:{}", uniqueId);
                continue;
            }
            for (MsgRetryQueue msgRetryQueue : msgRetryQueues) {
                logger.info("重新尝试消费：{}->{}", msgRetryQueue.getUniqueId(), msgRetryQueue.getMsgType());
                OrderMessage orderMessage = new OrderMessage();
                orderMessage.setBody(msgRetryQueue.getMsgBody());
                orderMessage.setMssageType(OrderMessageType.parse(msgRetryQueue.getMsgType()));
                orderMessage.setOrderId(msgRetryQueue.getUniqueId());
                mOrderMsgProcessor.process(JSON.toJSONString(orderMessage));
            }
        }
        logger.debug("END saleOrderStateMsgRetryScheduler......");
    }
}
