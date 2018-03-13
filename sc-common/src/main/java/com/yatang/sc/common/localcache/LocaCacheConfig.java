package com.yatang.sc.common.localcache;

import com.busi.mq.comsumer.processor.BaseMQMsgProcessor;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.busi.mq.message.encoder.JSONStringEncoder;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.common.localcache.mq.service.CustomMQPushConsumerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class LocaCacheConfig implements ApplicationContextAware {
    private Logger mLogger = LoggerFactory.getLogger(this.getClass());

    private static Properties props;
    private ApplicationContext mApplicationContext;

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public CustomMQPushConsumerService getLocalCacheMQMsgConsumer() {
        loadProps();
        if (props == null || StringUtils.isEmpty(props.getProperty("rocketmq.nameserver.address"))) {
            mLogger.warn("Local cache mq consumer config is missing");
            return null;
        }

        List<MQMsgProcessor> processorList = new ArrayList<MQMsgProcessor>();
        BaseMQMsgProcessor mqMsgProcessor = (BaseMQMsgProcessor) mApplicationContext.getBean("localCacheMqProcessor");
        mqMsgProcessor.setMessageTag(props.getProperty("rocketmq.localCacheMsg.tag"));
        processorList.add(mqMsgProcessor);
        CustomMQPushConsumerService mqPushConsumerService = new CustomMQPushConsumerService();
        mqPushConsumerService.setConsumerGroup(props.getProperty("rocketmq.localCacheMsg.consumergroup"));
        mqPushConsumerService.setNamesrvAddr(props.getProperty("rocketmq.nameserver.address"));
        mqPushConsumerService.setMsgTopic(props.getProperty("rocketmq.localCacheMsg.topic"));
        mqPushConsumerService.setMsgTag(props.getProperty("rocketmq.localCacheMsg.tag"));
        mqPushConsumerService.setMsgProcessors(processorList);
        return mqPushConsumerService;
    }

    @Bean(name = "localCacheSimpleMQProducer", initMethod = "init", destroyMethod = "destroy")
    public SimpleMQProducer getLocalCacheSimpleMQProducer() {
        SimpleMQProducer producer = new SimpleMQProducer();
        producer.setNamesrvAddr(props.getProperty("rocketmq.nameserver.address"));
        producer.setProducerGroup(props.getProperty("rocketmq.localCacheMsg.consumergroup"));
        producer.setEncoder(getLocalCacheEncoder());
        return producer;
    }

    public JSONStringEncoder getLocalCacheEncoder() {
        JSONStringEncoder encoder = new JSONStringEncoder();
        encoder.setMsgCharset("utf-8");
        encoder.setMsgTag(props.getProperty("rocketmq.localCacheMsg.tag"));
        encoder.setMsgTopic(props.getProperty("rocketmq.localCacheMsg.topic"));
        return encoder;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        mApplicationContext = applicationContext;
    }

    private void loadProps() {
        props = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/cache.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
