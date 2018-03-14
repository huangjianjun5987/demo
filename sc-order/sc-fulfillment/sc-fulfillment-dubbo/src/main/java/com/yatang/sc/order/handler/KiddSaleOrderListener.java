package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.CommonEnum;
import com.yatang.sc.enums.KiddOrderStatusType;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.fulfillment.dto.OrderCancelConfirmDto;
import com.yatang.sc.fulfillment.dto.OrderCancelFailDto;
import com.yatang.sc.fulfillment.dto.OrderDeliveryDto;
import com.yatang.sc.fulfillment.dto.OrderNotReceiveDto;
import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.fulfillment.dto.OrderSendCarDto;
import com.yatang.sc.fulfillment.dto.OrderTransferBackDto;
import com.yatang.sc.fulfillment.dto.OrderWMSRejectDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import com.yatang.sc.kidd.dto.orderNotify.KiddOrderNoticeInfoDto;
import com.yatang.sc.order.domain.MsgRetryQueue;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.MsgRetryQueueService;
import com.yatang.sc.order.service.ReturnRequestService;
import com.yatang.sc.order.states.OrderReturnedStates;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @描述: kidd销售消息处理
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 16:10
 * @版本: v1.0
 */
@Service("kiddSaleOrderListener")
public class KiddSaleOrderListener extends OrderMsgListener {

    protected Logger log = LoggerFactory.getLogger(KiddSaleOrderListener.class);

    @Autowired
    private OrderFulfillerDubboService mOrderFulfillerDubboService;

    @Autowired
    private MsgRetryQueueService mMsgRetryQueueService;

    @Autowired
    private ReturnRequestService returnRequestService;

    @Override
    public void handle(OrderMessage msg) throws MqBizFailureException {
        log.info("handle order status notify msg: " + msg);
        if (StringUtils.isEmpty(msg.getBody())) {
            return;
        }
        //todo 可能会重新改
        KiddOrderNoticeInfoDto requestDto = JSON.parseObject(msg.getBody(), KiddOrderNoticeInfoDto.class);

        if (requestDto == null) {
            log.warn("订单反序列化异常:{}", msg.getBody());
            return;
        }
        Response<Boolean> response;
        try {
            switch (KiddOrderStatusType.parse(requestDto.getCurrentStatus())) {//解析订单状态(对于际链都是异步通知)

                case ACCEPT://心怡由orderNotice发送
                    OrderTransferBackDto orderTransferBackDto = new OrderTransferBackDto();
                    orderTransferBackDto.setOrderId(requestDto.getOrderCode());
                    orderTransferBackDto.setSuccess(true);
                    response = mOrderFulfillerDubboService.orderTransferBack(orderTransferBackDto);
                    if (shouldRetry(response)) {
                        throw new MqBizFailureException("[订单发送到WMS]消息处理失败:" + response.getErrorMessage());
                    }
                    break;

                case READY: //待提货 心怡由 出库单确认目前发送
                    OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
                    orderDeliveryDto.setOrderId(requestDto.getOrderCode());

                    //物流信息
                    orderDeliveryDto.setExpressCode(requestDto.getExpressCode());//运单编号
                    orderDeliveryDto.setLogisticsName(requestDto.getLogisticsName());//物流名称
                    orderDeliveryDto.setLogisticsCode(requestDto.getLogisticsCode());
                    orderDeliveryDto.setCargoDtos(requestDto.getCargoDtos());
                    //物流信息 cago
                    response = mOrderFulfillerDubboService.orderDelivery(orderDeliveryDto);
                    if (shouldRetry(response)) {
                        throw new MqBizFailureException("[订单开始配送]消息处理失败:" + response.getErrorMessage());
                    }
                    break;
                case CANCELED://取消订单 xinyi取消订单发送 取消成功
                    OrderCancelConfirmDto orderCancelConfirmDto = new OrderCancelConfirmDto();
                    orderCancelConfirmDto.setOrderId(requestDto.getOrderCode());
                    response = mOrderFulfillerDubboService.orderCancelConfirm(orderCancelConfirmDto);
                    if (shouldRetry(response)) {
                        throw new MqBizFailureException("[订单取消成功]消息处理失败:" + response.getErrorMessage());
                    }
                    break;

                case CANCELEDFAIL: //取消订单 xinyi取消订单发送 取消失败
                    log.warn("订单:{} 取消处理失败:{}", requestDto.getOrderCode(), JSON.toJSONString(requestDto));
                    OrderCancelFailDto orderCancelFailDto = new OrderCancelFailDto();
                    orderCancelFailDto.setOrderId(requestDto.getOrderCode());
                    orderCancelFailDto.setMessage(requestDto.getRemark());
                    response = mOrderFulfillerDubboService.orderCancelFail(orderCancelFailDto);
                    if (shouldRetry(response)) {
                        throw new MqBizFailureException("[订单取消失败]消息处理失败:" + response.getErrorMessage());
                    }
                    break;

                case SENDCAR:
                    OrderSendCarDto orderSendCarDto = new OrderSendCarDto();
                    orderSendCarDto.setDeliveryTime(requestDto.getDeliveryTime());
                    orderSendCarDto.setDriverName(requestDto.getDriverName());
                    orderSendCarDto.setDriverPhone(requestDto.getDriverPhone());
                    orderSendCarDto.setOrderId(requestDto.getOrderCode());
                    response = mOrderFulfillerDubboService.orderSendCar(orderSendCarDto);
                    if (shouldRetry(response)) {
                        throw new MqBizFailureException("[订单发车]消息处理失败:" + response.getErrorMessage());
                    }
                    break;
                case SIGNED://签收可以线下
                    OrderReceiveDto orderReceiveDto = new OrderReceiveDto();
                    orderReceiveDto.setOrderId(requestDto.getOrderCode());

                    // 已创建退货单且未被驳回的订单不进行签收处理
                    ReturnRequestPo returnRequestPo = returnRequestService.queryReturnRequestByOrderId(orderReceiveDto.getOrderId());
                    if (returnRequestPo != null && returnRequestPo.getState() != OrderReturnedStates.CANCELLED.getStateValue()) {
                        log.info("订单存在拒收退货申请，不处理仓库回传的签收状态:{}", JSON.toJSONString(orderReceiveDto));
                        return;
                    }
                    response = mOrderFulfillerDubboService.orderReceive(orderReceiveDto);
                    if (shouldRetry(response)) {
                        throw new MqBizFailureException("[订单签收]消息处理失败:" + response.getErrorMessage());

                    }
                    break;
                case REFUSE://拒签(心怡带测试)
                    // 拒收退货上线后 这里不处理拒签 2017年11月16日14:59:21
//                    OrderNotReceiveDto orderNotReceiveDto = new OrderNotReceiveDto();
//                    orderNotReceiveDto.setOrderId(requestDto.getOrderCode());
//                    response = mOrderFulfillerDubboService.orderNotReceive(orderNotReceiveDto);
//                    if (shouldRetry(response)) {
//                        throw new MqBizFailureException("[订单未送达]消息处理失败:" + response.getErrorMessage());
//                    }
                    break;

                case REJECT://仓库拒单(心怡带测试)
                    OrderWMSRejectDto orderWMSRejectDto = new OrderWMSRejectDto();
                    orderWMSRejectDto.setOrderId(requestDto.getOrderCode());
                    response = mOrderFulfillerDubboService.orderWMSReject(orderWMSRejectDto);
                    if (shouldRetry(response)) {
                        throw new MqBizFailureException("[仓库拒收]消息处理失败:" + response.getErrorMessage());
                    }
                    break;
                default:
                    log.info("Ignore order status:{}", requestDto.getCurrentStatus());
            }
        } catch (Exception ex) {
            MsgRetryQueue msgRetryQueue = mMsgRetryQueueService.selectByUniqueIdAndMsgType(msg.getOrderId(), msg.getMssageType().getType());
            if (msgRetryQueue != null) {
                msgRetryQueue.setRetryCount(msgRetryQueue.getRetryCount() + 1);
                msgRetryQueue.setLastExecTime(new Date());
                mMsgRetryQueueService.update(msgRetryQueue);
                return;
            }
            msgRetryQueue = new MsgRetryQueue();
            msgRetryQueue.setUniqueId(msg.getOrderId());
            msgRetryQueue.setMsgBody(msg.getBody());
            msgRetryQueue.setMsgType(msg.getMssageType().getType());
            msgRetryQueue.setState("WZX");
            msgRetryQueue.setOrder(KiddOrderStatusType.getOrder(KiddOrderStatusType.parse(requestDto.getCurrentStatus())));
            msgRetryQueue.setRetryCount(0);
            mMsgRetryQueueService.insert(msgRetryQueue);
            log.info("仓库消息处理失败，等待定时任务重新执行：{}", JSON.toJSONString(msg));
            return;
        }

        MsgRetryQueue msgRetryQueue = mMsgRetryQueueService.selectByUniqueIdAndMsgType(msg.getOrderId(), msg.getMssageType().getType());
        if (msgRetryQueue != null) {
            msgRetryQueue.setRetryCount(msgRetryQueue.getRetryCount() + 1);
            msgRetryQueue.setState("YZX");
            msgRetryQueue.setLastExecTime(new Date());
            mMsgRetryQueueService.update(msgRetryQueue);
        }
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.KIDD_ORDER_STATUS_NOTIFY.equals(msg.getMssageType());
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
}
