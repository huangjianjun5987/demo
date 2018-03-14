package com.yatang.sc.purchase.flow.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.flow.ThirdOrderFlow;
import com.yatang.sc.purchase.service.PurchaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiangyonghong on 2017/10/16.
 */

@Service("thirdOrderFlow")
public class ThirdOrderFlowImpl implements ThirdOrderFlow {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PurchaseHelper purchaseHelper;

    @Resource(name = "orderMessageSender")
    private OrderMessageSender saleOrderMQProducer;

    @Autowired
    private OrderService orderService;

    @Override
    public Response<Boolean> addThirdOrder(OrderDto orderDto) {
        Response<Boolean> rep = new Response<Boolean>();
        boolean flag;
        try{
            flag = purchaseHelper.saveThirdOrder(orderDto);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            rep.setSuccess(false);
            rep.setResultObject(false);
            rep.setCode(CommonsEnum.RESPONSE_500.getCode());
            rep.setErrorMessage(e.getMessage());
            return rep;
        }
        rep.setSuccess(flag);
        rep.setResultObject(flag);
        rep.setCode(CommonsEnum.RESPONSE_200.getCode());
        rep.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return rep;
    }

    @Override
    public Response<Boolean> signOrder(String thirdPartOrderNo) {
        log.info("第三方签收订单,thirdPartOrderNo{}",thirdPartOrderNo);
        Response<Boolean> rep = new Response<Boolean>();
        try{
            Order order = orderService.queryThirdOrderByThirdOrderNo(thirdPartOrderNo);
            if(order ==null){
                rep.setSuccess(false);
                rep.setResultObject(false);
                rep.setCode(CommonsEnum.RESPONSE_500.getCode());
                rep.setErrorMessage("没有对应订单");
                return rep;
            }
            OrderMessage msg = new OrderMessage();
            msg.setOrderId(order.getId());
            msg.setMssageType(OrderMessageType.CompleteOrder);
            saleOrderMQProducer.sendMsg(msg);

        }catch (Exception e){
            e.printStackTrace();
            log.error("第三方签收订单,exception{}",e.getMessage());
            rep.setSuccess(false);
            rep.setResultObject(false);
            rep.setCode(CommonsEnum.RESPONSE_500.getCode());
            rep.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            return rep;
        }
        rep.setSuccess(true);
        rep.setResultObject(true);
        rep.setCode(CommonsEnum.RESPONSE_200.getCode());
        rep.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        log.info("第三方签收订单,return{}", JSON.toJSONString(rep));
        return rep;
    }

}
