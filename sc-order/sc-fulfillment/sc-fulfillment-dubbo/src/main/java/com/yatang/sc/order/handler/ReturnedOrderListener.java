package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.enums.ReturnedOrderStatusType;
import com.yatang.sc.fulfillment.dubboservice.OrderReturnRequestWriteDubboService;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderMqDto;
import com.yatang.sc.order.domain.MqMsgLog;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.service.MqMsgLogService;
import com.yatang.sc.order.service.ReturnRequestService;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestItemDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 退换货单消费mq
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/20 14:06
 * @版本: v1.0
 */
@Service("returnOrderMsgProcessor")
public class ReturnedOrderListener implements MQMsgProcessor {

    protected Logger log = LoggerFactory.getLogger( ReturnedOrderListener.class );

    @Autowired
    private OrderReturnRequestWriteDubboService writeDubboService;

    @Autowired
    private MqMsgLogService mMqMsgLogService;


    @Autowired
    private ReturnRequestService requestService;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Override
    public void process(String msg) {
        log.info( "handle returned order status notify msg: " + JSON.toJSONString( msg ) );

        KiddReturnOrderMqDto requestDto = JSON.parseObject( msg, KiddReturnOrderMqDto.class );
        MqMsgLog mqMsgLog = new MqMsgLog();
        mqMsgLog.setOrderId( requestDto.getReturnOrderCode() );
        mqMsgLog.setMsgBody( msg );
        mqMsgLog.setMsgType( "kiddReturnedOrderNotify" );

        try {
            mMqMsgLogService.insert( mqMsgLog );
        } catch (Exception pE) {
            log.error( "保存mq消息日志出错", pE );
        }
        if (StringUtils.isEmpty( msg )) {
            return;
        }

        String lockPath = "/order_returned_lock_" + requestDto.getReturnOrderCode();
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock();
            ReturnRequestReceiptDto requestReceiptDto = transKiddReturnOrder2ReturnReceipt( requestDto );
            switch (ReturnedOrderStatusType.parse( requestDto.getStatus() )) {//解析订单状态(对于际链都是异步通知)
                case RETURN_COMPLETED://退货已完成
                    Response<Void> response = writeDubboService.completeReturnedOrderReceipt( requestReceiptDto );
                    log.info( "handle returned order--退换货完成操作请求返回结果:{}", JSON.toJSONString( response ) );
                    if (!CommonsEnum.RESPONSE_200.getCode().equals( response.getCode() )) {
                        log.error( "handle returned order--退换货完成操作请求返回执行失败:{}", JSON.toJSONString( response ) );
                        // throw new RuntimeException("退货单号" + requestDto.getReturnOrderCode() + "退货完成操作失败");
                    }
                    break;
                case EXCHANGE_DELIVERED: //仓库已发货
                    Response<Void> deliveryResponse = writeDubboService.deliveryExchangeOrderReceipt( requestReceiptDto );
                    log.info( "handle returned order--换货完成仓库已发货请求返回结果:{}", JSON.toJSONString( deliveryResponse ) );
                    if (!CommonsEnum.RESPONSE_200.getCode().equals( deliveryResponse.getCode() )) {
                        log.error( "handle returned order--换货完成仓库已发货请求返回执行失败:{}", JSON.toJSONString( deliveryResponse ) );
                        //  throw new RuntimeException("换货单号" + requestDto.getReturnOrderCode() + "仓库发货更新操作失败");
                    }
                    break;
                case EXCHANGE_COMPLETED://换货已完成
//                    Response<Void> exchangeResponse = writeDubboService.completeExchangeOrderReceipt( requestReceiptDto );
//                    log.info( "handle returned order--换货完成请求返回结果:{}", JSON.toJSONString( exchangeResponse ) );
//                    if (!CommonsEnum.RESPONSE_200.getCode().equals( exchangeResponse.getCode() )) {
//                        log.error( "handle returned order--换货完成操作请求返回执行失败:{}", JSON.toJSONString( exchangeResponse ) );
//                        // throw new RuntimeException("换货单号" + requestDto.getReturnOrderCode() + "仓库发货已完成更新操作失败");
//                    }
                    break;
                default:
                    log.info( "Ignore returned order status:{}", requestDto.getStatus() );
            }

            mqMsgLog.setComment( "消息已被消费" );
            mqMsgLog.setProcessResult( true );
            try {
                mMqMsgLogService.update( mqMsgLog );
            } catch (Exception pE) {
                log.error( "更新mq消息日志出错", pE );
            }
        } catch (Exception pE) {
            mqMsgLog.setComment( pE.getMessage() );
            mqMsgLog.setProcessResult( false );
            try {
                mMqMsgLogService.update( mqMsgLog );
            } catch (Exception pp) {
                log.error( "更新mq消息日志出错", pE );
            }
            throw pE;
        } finally {
            rLock.unlock();
        }

    }


    /**
     * kidd转换为退换货单
     *
     * @param requestDto
     * @return
     */
    private ReturnRequestReceiptDto transKiddReturnOrder2ReturnReceipt(KiddReturnOrderMqDto requestDto) {
        ReturnRequestReceiptDto requestReceiptDto = new ReturnRequestReceiptDto();
        //退换货单主体
        ReturnRequestDto returnRequestDto = new ReturnRequestDto();
        returnRequestDto.setId( requestDto.getReturnOrderCode() );//获取退换货单号

        ReturnRequestPo requestPo = requestService.selectByPrimaryKey( requestDto.getReturnOrderCode() );
        returnRequestDto.setBranchCompanyArehouse( requestPo.getBranchCompanyArehouse() );//仓库编号
        returnRequestDto.setReturnRequestType( requestPo.getReturnRequestType() );//获取退换货类型

        if (null!=requestDto.getOrderLinesDtos()&&requestDto.getOrderLinesDtos().size() > 0) {
            List<ReturnRequestItemDto> itemDtos = new ArrayList<>();
            for (KiddOrderLinesDto kiddOrderLinesDto : requestDto.getOrderLinesDtos()) {
                ReturnRequestItemDto returnRequestItemDto = new ReturnRequestItemDto();
                returnRequestItemDto.setReturnId( returnRequestDto.getId() );
                returnRequestItemDto.setProductCode( kiddOrderLinesDto.getItemCode() );
                returnRequestItemDto.setProductId( kiddOrderLinesDto.getItemId() );
                String actualQty = String.valueOf( kiddOrderLinesDto.getActualQty() );
                returnRequestItemDto.setActualReturnQuantity( Long.parseLong( actualQty ) );
                itemDtos.add( returnRequestItemDto );
            }
            requestReceiptDto.setRequestItems( itemDtos );

        }
        requestReceiptDto.setReturnRequest( returnRequestDto );
        return requestReceiptDto;


    }
}
