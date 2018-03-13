package com.yatang.sc.juban.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.juban.dto.ResponseDto;
import com.yatang.sc.juban.dto.orderCancel.JubanOrderCancelRequestDto;
import com.yatang.sc.juban.dto.orderNotice.JubanOrderNoticeInfoDto;
import com.yatang.sc.juban.dto.orderNotice.JubanOrderNoticeRequestInfoDto;
import com.yatang.sc.juban.dto.orderNotice.JubanOrderProcessInfoDto;
import com.yatang.sc.juban.service.kidd.JubanOrderCancelProxyService;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelOrdersDto;
import com.yatang.sc.kidd.dto.orderNotify.KiddOrderNoticeInfoDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderMqDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @描述:桔瓣订单服务(取消>>采购和销售,订单通知>>销售和采购)
 * @类名:JubanOrderServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/26 13:53
 * @版本: v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JubanOrderServiceImpl {


    @Resource(name = "orderMessageSender")
    private OrderMessageSender saleOrderMQProducer;//销售订单mq
    @Resource(name = "entryOrderMQProducer")
    private SimpleMQProducer entryOrderMQProducer;//采购订单
    @Resource(name = "returnOrderMQProducer")
    private SimpleMQProducer returnOrderMQProducer;//退货换货单

    private final JubanOrderCancelProxyService jubanOrderCancelProxyService;

    private final KiddFacadeService kiddFacadeService;

    /**
     * 取消订单(采购或者销售)
     *
     * @param kiddCancelAllOrdersDto
     * @return
     */
    @EventListener
    public Response<String> cancel(KiddCancelAllOrdersDto kiddCancelAllOrdersDto) {

        log.info( "service----cancel>>订单取消--请求参数:{}", JSON.toJSONString( kiddCancelAllOrdersDto ) );
        // 根据仓库的标识决定走心怡或者际链
        if (null == kiddCancelAllOrdersDto || 0 == kiddCancelAllOrdersDto.getOrders().size() || StringUtils.isBlank( kiddCancelAllOrdersDto.getOrders().get( 0 ).getWarehouseCode() )) {
            log.error( "service----cancel>>订单取消--请求参数出错：{}", JSON.toJSONString( kiddCancelAllOrdersDto ) );
            throw new RuntimeException( "service----cancel>>订单取消--请求参数出错" + JSON.toJSONString( kiddCancelAllOrdersDto ) );
        }

        KiddUtils.checkJubanListening( kiddFacadeService.warehouseInterfaceProvider( kiddCancelAllOrdersDto.getOrders().get( 0 ).getWarehouseCode() ) );
        Response<String> response = new Response<>();
        try {
            JubanOrderCancelRequestDto orderCancelRequestDto = new JubanOrderCancelRequestDto();
            String orderType = kiddCancelAllOrdersDto.getOrderType();//订单类型(区分销售订单和采购单)
            List<KiddCancelOrdersDto> orders = kiddCancelAllOrdersDto.getOrders();
            if (KiddOrderLogisticsType.KIDD_CGRK.equals(orderType)) {//取消采购单
                for (KiddCancelOrdersDto kiddCancelOrdersDto : orders) {
                    //数据转换
                	dataConvert(orderCancelRequestDto, kiddCancelOrdersDto);
                    orderCancelRequestDto.setOrderType(kiddCancelOrdersDto.getOrderType());//类型
                    orderCancelRequestDto.setCancelReason("超时关闭");//取消原因
                    ResponseDto responseDto = jubanOrderCancelProxyService.cancel( orderCancelRequestDto );
                    log.info("service----cancel>>桔瓣处理采购订单取消--返回:{}", JSON.toJSONString( responseDto ) );
                    if (Objects.equal( responseDto.getFlag(), ResponseDto.SUCCESS )) {//请求成功
                        KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = new KiddPurchaseOrderConfirmDto();
                        KiddConfirmEntryOrderDto kiddConfirmEntryOrderDto = new KiddConfirmEntryOrderDto();//采购单基本信息
                        kiddConfirmEntryOrderDto.setStatus( KiddOrderLogisticsType.KIDD_CANCELED );//取消成功
                        kiddConfirmEntryOrderDto.setEntryOrderCode( kiddCancelOrdersDto.getOrderCode() );//收货单据编号
                        kiddConfirmEntryOrderDto.setEntryOrderType( kiddCancelOrdersDto.getOrderType() );//单据业务类型
                        kiddConfirmEntryOrderDto.setWarehouseCode( kiddCancelOrdersDto.getWarehouseCode() );//逻辑仓库编码
                        kiddConfirmEntryOrderDto.setOwnerCode( kiddCancelOrdersDto.getOrgCode() );//货主编码
                        kiddPurchaseOrderConfirmDto.setEntryOrder( kiddConfirmEntryOrderDto );
                        //同时发送mq
                        entryOrderMQProducer.sendMsg( kiddPurchaseOrderConfirmDto );
                        response.setResultObject( JSON.toJSONString( response.getResultObject() ) + kiddCancelOrdersDto.getOrderCode() + true );
                    } else {
                        log.error( "service----cancel>>桔瓣处理采购订单取消失败响应码:{},失败原因:{}", responseDto.getCode(), responseDto.getMessage() );
                        response.setResultObject( JSON.toJSONString( response.getResultObject() ) + kiddCancelOrdersDto.getOrderCode() + false );
                    }
                }
            }else if(KiddOrderLogisticsType.KIDD_CGTH.equals(orderType)){//采购退货
				log.info("service----cancel>>采购退货单取消--start---");
				KiddCancelOrdersDto kiddCancelOrdersDto = orders.get(0);
				dataConvert(orderCancelRequestDto, kiddCancelOrdersDto);
				orderCancelRequestDto.setOrderType(KiddOrderLogisticsType.KIDD_CGTH);//类型
				//请求心怡取消动作
                ResponseDto responseDto = jubanOrderCancelProxyService.cancel(orderCancelRequestDto);
                log.info("service----cancel>>心怡处理采购退货单取消--返回:{}", JSON.toJSONString(responseDto));
                if (Objects.equal(responseDto.getFlag(), ResponseDto.SUCCESS)) {//请求成功
                	response.setCode(CommonsEnum.RESPONSE_200.getCode());
                    response.setSuccess(true);
                    response.setResultObject(JSON.toJSONString(responseDto));
                } else {
                    log.error("service----cancel>>心怡处理采购退货单单取消失败响应码:{},失败原因:{}", responseDto.getCode(), responseDto.getMessage());
                    response.setSuccess(false);
                    response.setCode(CommonsEnum.RESPONSE_20123.getCode());
                    response.setErrorMessage(responseDto.getMessage());
                }
				log.info("service----cancel>>采购退货单取消--end---");
				return response;
            } else {
                KiddCancelOrdersDto kiddCancelOrdersDto = orders.get(0);
                dataConvert(orderCancelRequestDto, kiddCancelOrdersDto);
                //KIDDXSCK
                if (KiddOrderLogisticsType.KIDD_XSCK.equals( kiddCancelAllOrdersDto.getOrderType() )) {//自定义类型 kidd销售出库glink单取消==>销售单取消操作
                    orderCancelRequestDto.setOrderType( KiddOrderLogisticsType.XY_PTCK );//类型
                }
                //请求心怡取消动作
                ResponseDto responseDto = jubanOrderCancelProxyService.cancel( orderCancelRequestDto );
                log.info( "service----cancel>>桔瓣处理销售订单取消--返回:{}", JSON.toJSONString( responseDto ) );
                //设置mq发送
                OrderMessage saleOrderMessage = new OrderMessage();
                saleOrderMessage.setMssageType( OrderMessageType.KIDD_ORDER_STATUS_NOTIFY );
                saleOrderMessage.setOrderId( kiddCancelOrdersDto.getOrderCode() );
                KiddOrderNoticeInfoDto kiddOrderNoticeInfoDto = new KiddOrderNoticeInfoDto();
                kiddOrderNoticeInfoDto.setOrderCode( kiddCancelOrdersDto.getOrderCode() );
                if (Objects.equal( responseDto.getFlag(), ResponseDto.SUCCESS )) {//请求成功
                    kiddOrderNoticeInfoDto.setCurrentStatus(KiddOrderLogisticsType.KIDD_CANCELED );//取消成功
                    response.setSuccess( true );
                } else {
                    log.error( "service----cancel>>桔瓣处理销售订单取消失败响应码:{},失败原因:{}", responseDto.getCode(), responseDto.getMessage() );
                    response.setSuccess( false );
                    response.setCode( CommonsEnum.RESPONSE_20051.getCode() );
                    response.setErrorMessage( responseDto.getMessage() );
                    kiddOrderNoticeInfoDto.setRemark( responseDto.getMessage() );
                    kiddOrderNoticeInfoDto.setCurrentStatus( KiddOrderLogisticsType.KIDD_CANCELEDFAIL);//取消失败
                }
                //同时发送mq给
                saleOrderMessage.setBody( JSON.toJSONString( kiddOrderNoticeInfoDto ) );
                saleOrderMQProducer.sendMsg( saleOrderMessage );
                return response;
            }
        } catch (Exception e) {
            log.error( ExceptionUtils.getFullStackTrace( e ) );
            response.setSuccess( false );
            response.setCode( CommonsEnum.RESPONSE_500.getCode() );
            response.setErrorMessage( Throwables.getRootCause( e ).getMessage() );
        }
        return response;
    }


	private void dataConvert(JubanOrderCancelRequestDto orderCancelRequestDto, KiddCancelOrdersDto kiddCancelOrdersDto) {
		orderCancelRequestDto.setOrderId(kiddCancelOrdersDto.getOrderCode());// 单据编码
		orderCancelRequestDto.setOwnerCode(kiddCancelOrdersDto.getOrgCode());// 子公司编码
		orderCancelRequestDto.setWarehouseCode(kiddCancelOrdersDto.getWarehouseCode());//逻辑仓编码
		orderCancelRequestDto.setOrderCode(kiddCancelOrdersDto.getOrderCode());
	}


    /**
     * 订单通知(销售订单和采购订单)
     *
     * @param noticeRequestInfoDto
     * @return
     */
    @EventListener
    public Response<Void> getOrderNotice(JubanOrderNoticeRequestInfoDto noticeRequestInfoDto) {
        log.info( "service--getOrderNotice>>桔瓣订单通知分发信息:{}", JSON.toJSONString( noticeRequestInfoDto ) );
        Response<Void> responseDto = new Response<>();
        try {
            String processStatus = noticeRequestInfoDto.getProcess().getProcessStatus();//订单状态

            String orderType = noticeRequestInfoDto.getOrder().getOrderType();//判定销售和采购
            //通知mq信息拼接
            KiddOrderNoticeInfoDto requestDto = new KiddOrderNoticeInfoDto();

            requestDto.setOrderCode( noticeRequestInfoDto.getOrder().getOrderCode() );
            //具体需要发送的数据 finished
            if (KiddOrderLogisticsType.KIDD_CGRK.equals(orderType)) {//发送采购mq
                log.info( "service--getOrderNotice>>桔瓣订单通知分发信息编号:{},发送信息:{},走采购订单mq发送", noticeRequestInfoDto.getOrder().getOrderCode(), JSON.toJSONString( requestDto ) );
                //  entryOrderMQProducer.sendOrderlyMsg(noticeRequestInfoDto.getOrder().getOrderCode(), JSON.toJSONString(requestDto));
                JubanOrderNoticeInfoDto order = noticeRequestInfoDto.getOrder();
                JubanOrderProcessInfoDto process = noticeRequestInfoDto.getProcess();
                KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = new KiddPurchaseOrderConfirmDto();
                KiddConfirmEntryOrderDto kiddConfirmEntryOrderDto = new KiddConfirmEntryOrderDto();//采购单基本信息
                kiddConfirmEntryOrderDto.setEntryOrderCode( order.getOrderCode() );//单据编号
                kiddConfirmEntryOrderDto.setEntryOrderId( order.getOrderId() );//仓储系统单据号
                kiddConfirmEntryOrderDto.setEntryOrderType( order.getOrderType() );//单据类型
                kiddConfirmEntryOrderDto.setWarehouseCode( order.getWarehouseCode() );//仓库编码
                kiddConfirmEntryOrderDto.setStatus( process.getProcessStatus() );//单据状态
                kiddConfirmEntryOrderDto.setOperateTime( process.getOperateTime() );//当前状态操作时间
                kiddConfirmEntryOrderDto.setRemark( process.getRemark() );//备注
                kiddPurchaseOrderConfirmDto.setEntryOrder( kiddConfirmEntryOrderDto );
                entryOrderMQProducer.sendMsg( kiddPurchaseOrderConfirmDto );
            } else {//发送销售mq 走kidd
                log.info( "service--getOrderNotice>>桔瓣订单通知分发信息编号:{},发送信息:{},走销售订单mq发送", noticeRequestInfoDto.getOrder().getOrderCode(), JSON.toJSONString( requestDto ) );
                if (KiddOrderLogisticsType.XY_SIGN.equals(processStatus)) {
                    noticeRequestInfoDto.getProcess().setProcessStatus( KiddOrderLogisticsType.KIDD_SIGNED );//更改状态
                    if (noticeRequestInfoDto.getOrder().getOrderCode().contains( "H" )) {//换货单的出库签收完成状态
                        KiddReturnOrderMqDto kiddReturnOrderMqDto = new KiddReturnOrderMqDto();
                        String orderCode = noticeRequestInfoDto.getOrder().getOrderCode();
                        kiddReturnOrderMqDto.setReturnOrderCode( orderCode.substring( 0, orderCode.length() - 1 ) );//退货单编码
                        kiddReturnOrderMqDto.setStatus( KiddOrderLogisticsType.KIDD_SIGNED );//正常签收状态
                        kiddReturnOrderMqDto.setOrderType( KiddOrderLogisticsType.KIDD_HHCK );//业务类型
                        returnOrderMQProducer.sendMsg( kiddReturnOrderMqDto );
                    }
                }
                if (!noticeRequestInfoDto.getOrder().getOrderCode().contains( "H" )) {//表示不是换货状态
                    requestDto.setCurrentStatus( noticeRequestInfoDto.getProcess().getProcessStatus() );
                    OrderMessage saleOrderMessage = new OrderMessage();
                    saleOrderMessage.setMssageType( OrderMessageType.KIDD_ORDER_STATUS_NOTIFY );
                    saleOrderMessage.setOrderId( noticeRequestInfoDto.getOrder().getOrderCode() );
                    saleOrderMessage.setBody( JSON.toJSONString( requestDto ) );
                    saleOrderMQProducer.sendMsg( saleOrderMessage );
                }
            }
            responseDto.setCode( "001" );
            responseDto.setSuccess( true );
            responseDto.setErrorMessage( "订单通知接收成功" );

        } catch (Exception e) {
            log.error( ExceptionUtils.getFullStackTrace( e ) );
            responseDto.setCode( "002" );
            responseDto.setSuccess( false );
            responseDto.setErrorMessage( "订单接收失败" );
        }
        return responseDto;
    }
}
