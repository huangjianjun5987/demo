package com.yatang.sc.common.localcache.mq.processor;

import com.alibaba.fastjson.JSON;
import com.busi.mq.comsumer.processor.BaseMQMsgProcessor;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.common.localcache.LocalCacheContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("localCacheMqProcessor")
public class LocalCacheMqProcessor extends BaseMQMsgProcessor {

    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(String pS) {
        mLogger.info("Recieved message:{}", pS);
        if (StringUtils.isEmpty(pS)) {
            mLogger.warn("message is empty");
            return;
        }

        try {
            LocalCacheMessage localCacheMessage = JSON.parseObject(pS, LocalCacheMessage.class);
            if (localCacheMessage == null || StringUtils.isEmpty(localCacheMessage.getCommand())) {
                mLogger.warn("Can not parse mq message to LocalCacheMessage:{}", pS);
                return;
            }
            switch (localCacheMessage.getCommand()) {
                case LocalCacheMqCommand.invalidAll: {
                    LocalCacheContext.invalidateAll(localCacheMessage.getGroup());
                    break;
                }
                case LocalCacheMqCommand.invalidAllKeys: {
                    LocalCacheContext.invalidateAll(localCacheMessage.getGroup(), JSON.parseArray(localCacheMessage.getBody(), String.class));
                    break;
                }
                case LocalCacheMqCommand.invalidKey: {
                    LocalCacheContext.invalidate(localCacheMessage.getGroup(), localCacheMessage.getBody());
                    break;
                }
                case LocalCacheMqCommand.print: {
                    LocalCacheContext.printCache(localCacheMessage.getGroup());
                    break;
                }
                default: {
                    mLogger.warn("Current command is unsupported.");
                }
            }

        } catch (Exception ex) {
            mLogger.info("消息处理失败:", ex);
        }
    }
}
