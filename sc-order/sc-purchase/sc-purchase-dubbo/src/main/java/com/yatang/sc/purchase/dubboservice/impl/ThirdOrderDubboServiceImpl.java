package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dubboservice.ThirdOrderDubboService;
import com.yatang.sc.purchase.flow.ThirdOrderFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by xiangyonghong on 2017/10/16.
 */
@Service("thirdOrderDubboService")
public class ThirdOrderDubboServiceImpl implements ThirdOrderDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdOrderFlow thirdOrderFlow;

    @Override
    public Response<Boolean> addThirdOrder(OrderDto orderDto) {
        log.info("/ThirdOrderDubboService,method:addThirdOrder,start:param{}", JSON.toJSONString(orderDto));
        Response<Boolean>  rep = thirdOrderFlow.addThirdOrder(orderDto);
        log.info("/ThirdOrderDubboService,method:addThirdOrder,end:return{}", JSON.toJSONString(rep));
        return rep;
    }

    @Override
    public Response<Boolean> signOrder(String thirdPartOrderNo) {

        log.info("/ThirdOrderDubboService,method:signOrder,start:param{}", thirdPartOrderNo);
        Response<Boolean>  rep = thirdOrderFlow.signOrder(thirdPartOrderNo);
        log.info("/ThirdOrderDubboService,method:signOrder,end:return{}", JSON.toJSONString(rep));
        return rep;
    }

}
