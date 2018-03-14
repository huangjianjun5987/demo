package com.yatang.sc.order.processor;

import com.alibaba.fastjson.JSONObject;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.order.domain.MqMsgLog;
import com.yatang.sc.order.handler.OrderMsgListener;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.service.MqMsgLogService;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by qiugang on 7/24/2017.
 */
@Service("orderMsgProcessor")
public class OrderMsgProcessor implements MQMsgProcessor, ApplicationContextAware {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected ApplicationContext mContext;

    @Autowired
    private MqMsgLogService mMqMsgLogService;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Override
    public void process(String msg) {
        log.info("receive msg:" + msg);
        OrderMessage orderMessage = JSONObject.parseObject(msg, OrderMessage.class);
        MqMsgLog mqMsgLog = new MqMsgLog();
        mqMsgLog.setOrderId(orderMessage.getOrderId());
        mqMsgLog.setMsgBody(orderMessage.getBody());
        mqMsgLog.setMsgType(orderMessage.getMssageType().getType());

        try {
            mMqMsgLogService.insert(mqMsgLog);
        } catch (Exception pE) {
            log.error("保存mq消息日志出错", pE);
        }

        String lockPath = "/order_fulfill_lock_" + orderMessage.getOrderId();
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock();
            Map<String, OrderMsgListener> orderMsgListenerMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(mContext, OrderMsgListener.class, false, true);
            if (orderMsgListenerMap == null || orderMsgListenerMap.size() == 0) {
                log.warn("Not find any order msg listener");
                return;
            }
            OrderMsgListener msgListener;
            for (Map.Entry<String, OrderMsgListener> entry : orderMsgListenerMap.entrySet()) {
                msgListener = entry.getValue();
                if (msgListener.isCare(orderMessage)) {
                    log.info("find msg [{}] listener would be to process current message", entry.getKey());
                    msgListener.handle(orderMessage);
                }
            }
            mqMsgLog.setComment("消息已被消费");
            mqMsgLog.setProcessResult(true);
            try {
                mMqMsgLogService.update(mqMsgLog);
            } catch (Exception pE) {
                log.error("更新mq消息日志出错", pE);
            }
        } catch (MqBizFailureException pE) {
            mqMsgLog.setComment(pE.getMessage());
            mqMsgLog.setProcessResult(false);
            try {
                mMqMsgLogService.update(mqMsgLog);
            } catch (Exception pp) {
                log.error("更新mq消息日志出错", pE);
            }
            throw pE;
        } catch (Exception e) {
            log.error("更新mq消息日志出错", e);
            mqMsgLog.setComment(e.getMessage());
            mqMsgLog.setProcessResult(false);
            try {
                mMqMsgLogService.update(mqMsgLog);
            } catch (Exception pp) {
                log.error("更新mq消息日志出错", e);
            }
        } finally {
            rLock.unlock();
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext pApplicationContext) throws BeansException {
        mContext = pApplicationContext;
    }
}
