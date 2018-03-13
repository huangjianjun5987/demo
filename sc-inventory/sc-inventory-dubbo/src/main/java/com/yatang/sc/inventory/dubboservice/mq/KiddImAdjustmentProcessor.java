package com.yatang.sc.inventory.dubboservice.mq;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentDataDto;
import com.yatang.sc.inventory.dubboservice.ImAdjustmentWriteDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: kidd库存调整mq
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/28 11:34
 * @版本: v1.0
 */

@Service(value = "kiddImAdjustmentProcessor")
public class KiddImAdjustmentProcessor implements MQMsgProcessor {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ImAdjustmentWriteDubboService writeDubboService;

    @Override
    public void process(String message) {//生成调整单后调用
        log.info("mq--adjustmentProcessor--库存调整mq消息>>message:{}", message);
        //获取调整单数据
        KiddImAdjustmentDataDto kiddImAdjustmentDataDto = JSON.parseObject(message, KiddImAdjustmentDataDto.class);
        Response<Void> adjustmentResponse = writeDubboService.adjustImReceipt2SCMImAdjustmentReceipt(kiddImAdjustmentDataDto);
        log.info("库存调整执行结果:{}",JSON.toJSONString(adjustmentResponse));
        if (null == adjustmentResponse || !CommonsEnum.RESPONSE_200.getCode().equals(adjustmentResponse.getCode())) {
            log.error("mq--ImAdjustmentProcessor>>执行库存调整失败,原因:{}", JSON.toJSONString(adjustmentResponse));
            // throw new RuntimeException("mq--ImAdjustmentProcessor>>执行库存调整失败,原因:" + JSON.toJSONString(adjustmentResponse));
        }
        log.info("库存调整mq消息>>处理成功");

    }
}
