package com.yatang.sc.common.localcache.mq.service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.busi.mq.comsumer.MQPushConsumerService;
import com.busi.mq.comsumer.processor.BaseMQMsgProcessor;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomMQPushConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(MQPushConsumerService.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;
    private String namesrvAddr;
    private String consumerGroup;
    private String msgTopic;
    private String msgTag;
    // private int consumeMessageBatchMaxSize = 1;
    private int consumeThreadMin = 8;
    private int consumeThreadMax = 16;
    private String instanceName;

    // 消息处理类
    List<MQMsgProcessor> msgProcessors;


    // 初始化
    protected void init() throws MQClientException {
        // 参数信息
        logger.info("RocketMQ initialize...");
        logger.info("namesrvAddr : " + this.namesrvAddr);
        logger.info("consumerGroup : " + this.consumerGroup);
        logger.info("msgTopic : " + this.msgTopic);

        // 初始化对象
        defaultMQPushConsumer = new DefaultMQPushConsumer();
        if (this.instanceName != null && this.instanceName.trim().length() > 0) {
            defaultMQPushConsumer.setInstanceName(this.instanceName + String.valueOf(System.currentTimeMillis()));
        } else {
            defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        }

        defaultMQPushConsumer.setNamesrvAddr(this.namesrvAddr);
        defaultMQPushConsumer.setConsumerGroup(this.consumerGroup);

        // 指定订阅的topic指定tag的消息
        defaultMQPushConsumer.subscribe(this.msgTopic, this.msgTag);
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置每一次拉取消息的数量
        // defaultMQPushConsumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        // 设置最小和最大的线程处理数
        defaultMQPushConsumer.setConsumeThreadMin(consumeThreadMin);
        defaultMQPushConsumer.setConsumeThreadMax(consumeThreadMax);
        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);

        // 指定消息的处理对象
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                ConsumeConcurrentlyStatus resStatus = ConsumeConcurrentlyStatus.RECONSUME_LATER;
                try {
                    MessageExt msg = msgs.get(0);
                    String responseBody = new String(msg.getBody(), "utf-8");
                    logger.info("====Consumer收取到消息:mstag{}, msgbody: {}", msg.getTags(), responseBody);

                    for (MQMsgProcessor msgProcessor : msgProcessors) {
                        if (msgProcessor instanceof BaseMQMsgProcessor) {
                            BaseMQMsgProcessor baseMsgProcessor = (BaseMQMsgProcessor) msgProcessor;
                            if (baseMsgProcessor.getMessageTag().equals(msg.getTags())) {
                                baseMsgProcessor.process(responseBody);
                            }
                        } else {
                            msgProcessor.process(responseBody);
                        }
                    }
                    resStatus = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    logger.error("MQ消息处理异常", e);
                }
                return resStatus;
            }
        });
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("RocketMQ initialize success...");
    }


    // 销毁
    protected void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    public DefaultMQPushConsumer getDefaultMQPushConsumer() {
        return defaultMQPushConsumer;
    }


    public void setDefaultMQPushConsumer(DefaultMQPushConsumer defaultMQPushConsumer) {
        this.defaultMQPushConsumer = defaultMQPushConsumer;
    }


    public String getNamesrvAddr() {
        return namesrvAddr;
    }


    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }


    public String getConsumerGroup() {
        return consumerGroup;
    }


    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }


    public String getMsgTopic() {
        return msgTopic;
    }


    public void setMsgTopic(String msgTopic) {
        this.msgTopic = msgTopic;
    }


    public String getMsgTag() {
        return msgTag;
    }


    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }


    public int getConsumeThreadMin() {
        return consumeThreadMin;
    }


    public void setConsumeThreadMin(int consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }


    public int getConsumeThreadMax() {
        return consumeThreadMax;
    }


    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }


    public List<MQMsgProcessor> getMsgProcessors() {
        return msgProcessors;
    }


    public void setMsgProcessors(List<MQMsgProcessor> msgProcessors) {
        this.msgProcessors = msgProcessors;
    }


    public String getInstanceName() {
        return instanceName;
    }


    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
