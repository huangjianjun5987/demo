package com.yatang.sc.fulfillment.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.exception.OrderReturnedException;
import com.yatang.sc.flow.OrderReturnRequestFlowService;
import com.yatang.sc.fulfillment.dto.OrderReturnOperateMessageDto;
import com.yatang.sc.fulfillment.dubboservice.OrderReturnRequestWriteDubboService;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.ReturnRequestService;
import com.yatang.sc.order.states.OrderReturnRequestTypes;
import com.yatang.sc.order.states.OrderReturnedProductSates;
import com.yatang.sc.order.states.OrderReturnedStates;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 退换货dubboWrite服务实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/19 17:20
 * @版本: v1.0
 */
@Service("orderReturnRequestWriteDubboService")
public class OrderReturnRequestWriteDubboServiceImpl implements OrderReturnRequestWriteDubboService {
    protected Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    private OrderReturnRequestFlowService requestFlowService;

    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private OrderLogService orderLogService;


    @Override
    public Response<Void> operateOrderReturnedReceipt(OrderReturnOperateMessageDto operateMessageDto) {
        logger.info( "dubbo--operateOrderReturnedReceipt>>操作退换货单号【{}】的请求状态为:{},操作者:{}", operateMessageDto.getReturnId(), operateMessageDto.getOperateType(),operateMessageDto.getUserId() );
        Response<Void> response ;
        try {
            Short orderReturnedState = returnRequestService.getOrderReturnedReceiptState( operateMessageDto.getReturnId() );//注意 确认后才向第三方推送单子
            if (1 == orderReturnedState) {//可执行取消,编辑,提交的权限
                switch (operateMessageDto.getOperateType()) {//更新订单状态
                    case 1://确认 更新退货单状态为已确认
                        operateMessageDto.setOperateType( OrderReturnedStates.CONFIRMED.getStateValue() );
                        operateMessageDto.setRemark( OrderReturnedStates.CONFIRMED.getDescription() );
                        response = requestFlowService.confirmOrderReturnedReceipt( operateMessageDto );
                        break;
                    case 2://取消 更新退货单状态为 取消
                        operateMessageDto.setOperateType( OrderReturnedStates.CANCELLED.getStateValue() );
                        operateMessageDto.setRemark( OrderReturnedStates.CANCELLED.getDescription() );
                        response = requestFlowService.cancelOrderReturnedReceipt( operateMessageDto );

                        break;
                    default:
                        logger.error( "dubbo--operateOrderReturnedReceipt>>单号为【{}】退换货单操作失败,请求的操作状态为;{}", operateMessageDto.getReturnId(), operateMessageDto.getOperateType() );
                        response = new Response<>();
                        response.setCode( CommonsEnum.RESPONSE_20107.getCode() );
                        response.setErrorMessage( "单号为:" + operateMessageDto.getReturnId() + CommonsEnum.RESPONSE_20107.getName() );
                        response.setSuccess( false );
                }
            } else {
                logger.error( "dubbo--operateOrderReturnedReceipt>>单号为【{}】退换货单操作失败,请求的操作状态为:{},当前退换货单的状态为:{}", operateMessageDto.getReturnId(), operateMessageDto.getOperateType(), orderReturnedState );
                response = new Response<>();
                response.setCode( CommonsEnum.RESPONSE_20107.getCode() );
                response.setErrorMessage( "单号为:" + operateMessageDto.getReturnId() + CommonsEnum.RESPONSE_20107.getName() + "当前退换货单状态为:" + OrderReturnedStates.parse( orderReturnedState ).getDescription() );
                response.setSuccess( false );
            }
        } catch (Exception e) {
            response = new Response<>();
            if (e instanceof OrderReturnedException) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage(e.getMessage());
            } else {
                response.setErrorMessage( CommonsEnum.RESPONSE_500.getName() );
                response.setCode( CommonsEnum.RESPONSE_500.getCode() );

            }
            response.setSuccess( false );
            logger.error( ExceptionUtils.getFullStackTrace( e ) );
        }
        return response;
    }

    @Override
    public Response<Void> completeReturnedOrderReceipt(ReturnRequestReceiptDto requestReceiptDto) {//退货已完成

        logger.info( "service--completeReturnedOrderTReceipt>>完成退货操作参数:{}", JSON.toJSONString( requestReceiptDto ) );
        Response<Void> response;
        try {
            ReturnRequestDto returnRequest = requestReceiptDto.getReturnRequest();//退换货单主
            String returnRequestType = returnRequest.getReturnRequestType();//获取退换货单类型
            switch (OrderReturnRequestTypes.parse( returnRequestType )) {
                case NORMAL_RETURN://退货
                case REJECT_RETURN://拒收退货
                    response = requestFlowService.completeReturnedOrderTReceipt( requestReceiptDto );
                    //更新
                    break;
                case NORMAL_EXCHANGE://换货
                    response = requestFlowService.completeReturnedOrderHReceipt( requestReceiptDto );
                    break;

                default:
                    logger.error( "单号【{}】的退货完成状态更新失败,退换货单类型为：{}", requestReceiptDto.getReturnRequest().getId(), returnRequestType );
                    response = new Response<>();
                    response.setSuccess( false );
                    response.setCode( CommonsEnum.RESPONSE_20112.getCode() );
                    response.setErrorMessage( CommonsEnum.RESPONSE_20112.getName() );

            }
        } catch (Exception e) {
            response = new Response<>();
            if (e instanceof OrderReturnedException) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage(e.getMessage());
            } else {
                response.setErrorMessage( CommonsEnum.RESPONSE_500.getName() );
                response.setCode( CommonsEnum.RESPONSE_500.getCode() );

            }
            response.setSuccess( false );
            logger.error( ExceptionUtils.getFullStackTrace( e ) );
        }


        return response;
    }

    @Override
    public Response<Void> deliveryExchangeOrderReceipt(ReturnRequestReceiptDto requestReceiptDto) {//商品状态为更新已发货

        logger.info( "service--deliveryExchangeOrderReceipt>>换货单更新传递状态参数:{}", JSON.toJSONString( requestReceiptDto ) );
        Response<Void> response = new Response<>();
        try {
            ReturnRequestPo requestPo = BeanConvertUtils.convert( requestReceiptDto.getReturnRequest(), ReturnRequestPo.class );
            //更新为已发货
            requestPo.setProductState( OrderReturnedProductSates.DELIVERED.getStateValue() );
            requestPo.setProductStateDetail( OrderReturnedProductSates.DELIVERED.getDescription() );
            requestPo.setState( OrderReturnedStates.COMPLETED.getStateValue() );//退货单单总状态已完成
            requestPo.setStateDetail( OrderReturnedStates.COMPLETED.getDescription() );
            boolean deliverySuccess = returnRequestService.deliveryExchangeOrderReceipt( requestPo );
            if (!deliverySuccess) {
                logger.error( "单号【{}】换货单更新传递状态失败：{}", requestPo.getId() );
                orderLogService.saveOrderLog( requestPo.getId(), OrderReturnedProductSates.DELIVERED.getStateValue(), "换货更新已发货失败", "系统" );
                response.setSuccess( false );
                response.setCode( CommonsEnum.RESPONSE_20113.getCode() );
                response.setErrorMessage( CommonsEnum.RESPONSE_20113.getName() );

            } else {
                orderLogService.saveOrderLog( requestPo.getId(), OrderReturnedProductSates.DELIVERED.getStateValue(), "换货更新已发货成功", "系统" );
                response.setErrorMessage( CommonsEnum.RESPONSE_200.getName() );
                response.setSuccess( true );
            }
        } catch (Exception e) {
            logger.error( ExceptionUtils.getFullStackTrace( e ) );
            response.setCode( CommonsEnum.RESPONSE_500.getCode() );
            response.setSuccess( false );
            response.setErrorMessage( CommonsEnum.RESPONSE_500.getName() );

        }
        return response;
    }

    @Override
    public Response<Void> completeExchangeOrderReceipt(ReturnRequestReceiptDto requestReceiptDto) {

        logger.info( "service--deliveryExchangeOrderReceipt>>换货单更新完成状态参数:{}", JSON.toJSONString( requestReceiptDto ) );
        Response<Void> response = new Response<>();
        try {
            ReturnRequestPo requestPo = BeanConvertUtils.convert( requestReceiptDto.getReturnRequest(), ReturnRequestPo.class );
            requestPo.setState( OrderReturnedStates.COMPLETED.getStateValue() );//退货单单总状态已完成
            requestPo.setStateDetail( OrderReturnedStates.COMPLETED.getDescription() );
            boolean completeSuccess = returnRequestService.deliveryExchangeOrderReceipt( requestPo );
            if (!completeSuccess) {
                logger.error( "单号【{}】换货单更新完成状态参数：{}", requestPo.getId() );
                response.setSuccess( false );
                response.setCode( CommonsEnum.RESPONSE_20113.getCode() );
                response.setErrorMessage( CommonsEnum.RESPONSE_20113.getName() );
            } else {
                response.setErrorMessage( CommonsEnum.RESPONSE_200.getName() );
                response.setSuccess( true );
            }
        } catch (Exception e) {
            logger.error( ExceptionUtils.getFullStackTrace( e ) );
            response.setCode( CommonsEnum.RESPONSE_500.getCode() );
            response.setSuccess( false );
            response.setErrorMessage( CommonsEnum.RESPONSE_500.getName() );

        }
        return response;
    }


}
