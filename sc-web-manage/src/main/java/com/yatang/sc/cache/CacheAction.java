package com.yatang.sc.cache;

import com.busi.common.resp.Response;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.common.localcache.mq.processor.LocalCacheMessage;
import com.yatang.sc.operation.web.BaseAction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sc/cache")
public class CacheAction extends BaseAction {

    @Resource(name = "localCacheSimpleMQProducer")
    SimpleMQProducer localCacheSimpleMQProducer;

    @RequestMapping(value = "command", method = RequestMethod.GET)
    public Response<String> command(LocalCacheMessage pLocalCacheMessage) throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        localCacheSimpleMQProducer.sendMsg(pLocalCacheMessage);
        response.setResultObject("MQ消息已发送");
        return response;
    }
}
