package com.yatang.sc.fulfillment.dubboservice.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.flow.OrderFulfillerDubboServiceFlow;
import com.yatang.sc.fulfillment.dto.OrderCancelConfirmDto;
import com.yatang.sc.fulfillment.dto.OrderCancelFailDto;
import com.yatang.sc.fulfillment.dto.OrderCancelShippingDto;
import com.yatang.sc.fulfillment.dto.OrderDeliveryDto;
import com.yatang.sc.fulfillment.dto.OrderNotReceiveDto;
import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.fulfillment.dto.OrderRejectDto;
import com.yatang.sc.fulfillment.dto.OrderSendCarDto;
import com.yatang.sc.fulfillment.dto.OrderTransferBackDto;
import com.yatang.sc.fulfillment.dto.OrderTransferDto;
import com.yatang.sc.fulfillment.dto.OrderWMSRejectDto;
import com.yatang.sc.fulfillment.dto.ProviderOrderSingReqDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by qiugang on 7/27/2017.
 */
@Service("orderFulfillerDubboService")
public class OrderFulfillerDubboServiceImpl implements OrderFulfillerDubboService {

    protected Logger log = LoggerFactory.getLogger(OrderFulfillerDubboServiceImpl.class);

    @Resource(name = "orderFulfillerDubboServiceFlow")
    private OrderFulfillerDubboServiceFlow mOrderFulfillerDubboServiceFlow;

    @Value("${scm.systemCode:code0}")
    private String systemCode;

    /**
     * 发送订单到WMS
     *
     * @param orderTransferDto
     * @return
     */
    @Override
    public Response<Boolean> orderTransfer(OrderTransferDto orderTransferDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderTransfer( orderTransferDto );
            return response;
        } catch (Exception ex) {
            log.error( "发送订单到WMS异常:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "发送订单到WMS处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    /**
     * GLink接受订单回调通知
     *
     * @param orderTransferBackDto
     * @return
     */
    @Override
    public Response<Boolean> orderTransferBack(OrderTransferBackDto orderTransferBackDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderTransferBack( orderTransferBackDto );
            return response;
        } catch (Exception ex) {
            log.error( "GLink接受订单回调通知:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "GLink接受订单回调通知处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    /**
     * 订单开始配送
     *
     * @param orderDeliveryDto
     * @return
     */
    @Override
    public Response<Boolean> orderDelivery(OrderDeliveryDto orderDeliveryDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderDelivery( orderDeliveryDto );
            return response;
        } catch (Exception ex) {
            log.error( "订单开始配送:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "订单开始配送处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    /**
     * @param pOrderSendCarDto
     * @return
     */
    @Override
    public Response<Boolean> orderSendCar(OrderSendCarDto pOrderSendCarDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderSendCar( pOrderSendCarDto );
            return response;
        } catch (Exception ex) {
            log.error( "保存发车信息:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "保存发车信息处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    /**
     * 订单签收
     *
     * @param orderReceiveDto
     * @return
     */
    @Override
    public Response<Boolean> orderReceive(OrderReceiveDto orderReceiveDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderReceive( orderReceiveDto );
            return response;
        } catch (Exception ex) {
            log.error( "订单签收:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "订单签收处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    @Override
    public Response<Boolean> orderCancelConfirm(OrderCancelConfirmDto pOrderCancelConfirmDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderCancelConfirm( pOrderCancelConfirmDto );
            return response;
        } catch (Exception ex) {
            log.error( "订单取消成功通知处理失败:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "订单取消成功通知处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    @Override
    public Response<Boolean> orderCancelFail(OrderCancelFailDto pOrderCancelFailDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderCancelFail( pOrderCancelFailDto );
            return response;
        } catch (Exception ex) {
            log.error( "订单取消失败通知处理失败:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "订单取消失败通知处理失败" );
            response.setSuccess( false );
            return response;
        }
    }


    /**
     * 订单拒签
     *
     * @param orderRejectDto
     * @return
     */
    @Override
    public Response<Boolean> orderReject(OrderRejectDto orderRejectDto) {
        Response<Boolean> response = new Response<>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderReject( orderRejectDto );
            return response;
        } catch (Exception ex) {
            log.error( "订单拒签操作失败:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "订单拒签操作失败" );
            response.setSuccess( false );
            return response;
        }
    }

    /**
     * 订单未送达
     *
     * @param orderNotReceiveDto
     * @return
     */
    @Override
    public Response<Boolean> orderNotReceive(OrderNotReceiveDto orderNotReceiveDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderNotReceive( orderNotReceiveDto );
            return response;
        } catch (Exception ex) {
            log.error( "订单未送达:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "订单未送达处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    /**
     * 订单取消配送
     *
     * @param orderCancelShippingDto
     * @return
     */
    @Override
    public Response<Boolean> orderCancelShipping(OrderCancelShippingDto orderCancelShippingDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderCancelShipping( orderCancelShippingDto );
            return response;
        } catch (Exception ex) {
            log.error( "订单取消配送:", ex );
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage( "订单取消配送处理失败" );
            response.setSuccess( false );
            return response;
        }
    }

    /**
     * 仓库拒收
     *
     * @param orderWMSRejectDto
     * @return
     */
    @Override
    public Response<Boolean> orderWMSReject(OrderWMSRejectDto orderWMSRejectDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.orderWMSReject( orderWMSRejectDto );
            return response;
        } catch (Exception ex) {
            log.error("仓库拒收:", ex);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("仓库拒收处理失败");
            response.setSuccess(false);
            return response;
        }
    }

    @Override
    public Response<Boolean> providerOrderASN(String orderId, String receiptId) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.providerOrderASN(orderId, receiptId);
            return response;
        } catch (Exception ex) {
            log.error("直配订单录入ASN消息处理异常:", ex);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("直配订单录入ASN消息处理失败");
            response.setSuccess(false);
            return response;
        }
    }

    @Override
    public Response<Boolean> providerOrderSingReq(ProviderOrderSingReqDto pProviderOrderSingReqDto) {
        Response<Boolean> response = new Response<Boolean>();
        try {
            response = mOrderFulfillerDubboServiceFlow.providerOrderSingReq(pProviderOrderSingReqDto);
            return response;
        } catch (Exception ex) {
            log.error("收货待确认异常:", ex);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("收货待确认异常");
            response.setSuccess(false);
            return response;
        }
    }
}
