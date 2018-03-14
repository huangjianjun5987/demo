package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.CommonEnum;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.fulfillment.dto.OrderTransferBackDto;
import com.yatang.sc.fulfillment.dto.ProviderOrderSingReqDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.provider.order.ProviderOrderMsg;
import com.yatang.sc.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 直配订单状态通知处理
 */
@Service("providerOrderStatusListener")
public class ProviderOrderStatusListener extends OrderMsgListener {

    protected Logger log = LoggerFactory.getLogger(ProviderOrderStatusListener.class);

    @Autowired
    OrderService orderService;

    @Autowired
    private OrderFulfillerDubboService mOrderFulfillerDubboService;

    @Override
    public void handle(OrderMessage msg) throws Exception {
        log.info("handle msg order id: " + msg.getOrderId());
        if (StringUtils.isEmpty(msg.getOrderId())) {
            log.error("直供订单订单号为空");
            return;
        }

        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("直供订单订单号不存在:{}", msg.getOrderId());
            return;
        }

        ProviderOrderMsg providerOrderMsg = JSON.parseObject(msg.getBody(), ProviderOrderMsg.class);
        if (providerOrderMsg == null) {
            log.error("直供订单MQ信息内容为空:{}", msg.getOrderId());
            return;
        }
        switch (providerOrderMsg.getStatus()) {
            case ACCEPT: {
                OrderTransferBackDto orderTransferBackDto = new OrderTransferBackDto();
                orderTransferBackDto.setOrderId(msg.getOrderId());
                orderTransferBackDto.setSuccess(true);
                Response<Boolean> response = mOrderFulfillerDubboService.orderTransferBack(orderTransferBackDto);
                if (shouldRetry(response)) {
                    throw new MqBizFailureException("[直配订单接单消息]消息处理失败:" + response.getErrorMessage());
                }
                break;
            }
            case ASN: {
                if (StringUtils.isEmpty(providerOrderMsg.getBody())) {
                    log.warn("直配订单录入ANS消息，收货单号为空");
                    return;
                }
                Response<Boolean> response = mOrderFulfillerDubboService.providerOrderASN(msg.getOrderId(), providerOrderMsg.getBody());
                if (shouldRetry(response)) {
                    throw new MqBizFailureException("[直配订单录入ANS消息]消息处理失败:" + response.getErrorMessage());
                }
                break;
            }
            case PROVIDER_SINGED_REQ: {
                ProviderOrderSingReqDto orderSingReqDto = new ProviderOrderSingReqDto();
                orderSingReqDto.setOrderId(msg.getOrderId());
                orderSingReqDto.setSignedMsg(providerOrderMsg.getBody());
                Response<Boolean> response = mOrderFulfillerDubboService.providerOrderSingReq(orderSingReqDto);
                if (shouldRetry(response)) {
                    throw new MqBizFailureException("[直配订单签收申请消息]消息处理失败:" + response.getErrorMessage());
                }
                break;
            }
            default: {
                log.warn("状态不正确：{}", msg.getBody());
            }
        }
    }

    protected boolean shouldRetry(Response pResponse) {
        log.info("should retry response:{}", JSON.toJSONString(pResponse));
        if (pResponse != null
                && !pResponse.isSuccess()
                && !CommonEnum.ORDER_NONEXIST.getCode().equals(pResponse.getCode())
            // && !CommonEnum.ORDER_STATUS_INVALID.getCode().equals(pResponse.getCode())
                ) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.PROVIDER_ORDER_STATUE_NOTIFY.equals(msg.getMssageType());
    }
}
