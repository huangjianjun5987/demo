package com.yatang.sc.order.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.service.OrderIndexHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;



/**
 * Created by xiangyonghong on 9/27/2017.
 */
@Service("orderIndexMsgProcessor")
public class OrderIndexMsgProcessor  implements MQMsgProcessor, ApplicationContextAware {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected ApplicationContext mContext;

    @Autowired
    OrderIndexHelper orderIndexHelper;

    @Override
    public void process(String msg) {
//        log.info("receive msg{}", JSON.toJSONString(msg));
        OrderMessage orderMessage = JSONObject.parseObject(msg, OrderMessage.class);
        orderIndexHelper.sendOrderIndexMessage(orderMessage.getOrderId());
    }


    @Override
    public void setApplicationContext(ApplicationContext pApplicationContext) throws BeansException {
        mContext = pApplicationContext;
    }
}
