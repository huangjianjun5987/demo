package com.yatang.sc.glink.web;

import com.alibaba.fastjson.JSON;
import com.busi.kidd.annotation.KiddProvider;
import com.yatang.sc.glink.dto.PushOrderStateDto;
import com.yatang.sc.glink.dto.PushOrderStateRequestDto;
import com.yatang.sc.glink.dto.PushOrdersDto;
import com.yatang.sc.glink.dto.im.InventoryReportRequestDto;
import com.yatang.sc.glink.web.bean.GLinkImAdjustmentReceiptVo;
import com.yatang.sc.glink.web.bean.Response;
import com.yatang.sc.kidd.dto.ProviderRequestDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Glink回调接口
 *
 * @author yangqingsong
 */
@RestController
public class GlinkCallbackAction {
    private static final Logger mLogger = LoggerFactory.getLogger( GlinkCallbackAction.class );

    private static final String ownerCode = "YTXC";

    @Autowired
    private KiddFacadeService kiddFacadeService;


    @KiddProvider
    @RequestMapping(value = "/callback")
    public Response<String> callback(@RequestBody PushOrderStateDto request) {

        mLogger.info( "action--callback>>际链发送数据:{}", JSON.toJSONString( request ) );
        // 调用业务逻辑
        Response<String> response = new Response<>();
        response.setOwnerCode( ownerCode );

        if (null == request) {
            response.setCode( 1 );
            response.setMessage( "请求不能为空" );
            return response;
        }

        List<PushOrdersDto> orders = JSON.parseArray( request.getOrders(), PushOrdersDto.class );
        if (CollectionUtils.isEmpty( orders )) {
            response.setCode( 1 );
            response.setMessage( "订单数据pushOrdersDtos不能为空" );
            return response;
        }

        PushOrderStateRequestDto requestDto = new PushOrderStateRequestDto();
        requestDto.setOrders( orders );

        ProviderRequestDto<PushOrderStateRequestDto> providerDto = new ProviderRequestDto<>();
        providerDto.setApiMethod( "PushOrderState" );
        providerDto.setPayload( requestDto );

        mLogger.info( "glink service geteway dataBean:{},providerDto:{}", PushOrderStateRequestDto.class, JSON.toJSON( providerDto ) );
        com.busi.common.resp.Response kiddResponse = (com.busi.common.resp.Response) kiddFacadeService.send( providerDto );
        mLogger.info( "glink service geteway response:{}", JSON.toJSON( kiddResponse ) );


        response.setCode( kiddResponse.isSuccess() ? 0 : Integer.parseInt( kiddResponse.getCode() ) );
        response.setMessage( kiddResponse.getErrorMessage() );

//        for (PushOrdersDto order : orders) {
//            if (null == order) {
//                continue;
//            }
//            if (null == order.getOrderType()) {
//                continue;
//            }
//            if (order.getOrderType().endsWith("RK")) {
//                entryOrderMQProducer.sendOrderlyMsg(order.getOrderCode(), order);
//            } else {
//                OrderMessage saleOrderMessage = new OrderMessage();
//                saleOrderMessage.setMssageType(OrderMessageType.GLINK_ORDER_STATUS_NOFITY);
//                saleOrderMessage.setOrderId(order.getOrderCode());
//                saleOrderMessage.setBody(JSON.toJSONString(order));
//                saleOrderMQProducer.sendMsg(saleOrderMessage);
//            }
//        }
//        response.setCode(0);

        return response;
    }


    /**
     * 库存调整 glink传入接口 发送mq形式
     *
     * @param gLinkImAdjustmentReceipt
     * @return
     */
    @KiddProvider
    @RequestMapping(value = "/gLinkCallBackImAdjustment")
    public Response<String> gLinkCallBackImAdjustment(@RequestBody GLinkImAdjustmentReceiptVo gLinkImAdjustmentReceipt) {
        mLogger.info( "action--gLinkCallBackImAdjustment>>库存调整glink传入请求参数:{}", JSON.toJSONString( gLinkImAdjustmentReceipt ) );
        Response<String> response = new Response<>();
        response.setOwnerCode( ownerCode );//公司编号
        if (null == gLinkImAdjustmentReceipt) {
            response.setCode( 1 );
            response.setMessage( "请求不能为空" );
            return response;
        }


        InventoryReportRequestDto requestDto = new InventoryReportRequestDto();
        requestDto.setData( gLinkImAdjustmentReceipt.getData() );

        ProviderRequestDto<InventoryReportRequestDto> providerDto = new ProviderRequestDto<>();
        providerDto.setApiMethod( "PushOrderState" );
        providerDto.setPayload( requestDto );

        com.busi.common.resp.Response kiddResponse = (com.busi.common.resp.Response) kiddFacadeService.send( providerDto );


        response.setCode( kiddResponse.isSuccess() ? 0 : Integer.parseInt( kiddResponse.getCode() ) );
        response.setMessage( kiddResponse.getErrorMessage() );

        response.setCode( 0 );
        return response;
    }

    @RequestMapping(value = "/testCall")
    public Response<String> testCall() {
        Response<String> response = new Response<>();
        mLogger.info( "action------------------http://118.112.177.36:58080/sc-glink-web/testCall--------------------------------------testCall" );

        return response;
    }


}
