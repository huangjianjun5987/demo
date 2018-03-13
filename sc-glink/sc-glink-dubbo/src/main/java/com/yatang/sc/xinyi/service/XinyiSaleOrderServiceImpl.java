package com.yatang.sc.xinyi.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.yatang.sc.kidd.dto.orderNotify.KiddOrderNoticeInfoDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmOrderLinesDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderMqDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.logistics.LogisticsDictionary;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderCargoDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmDeliveryInfoDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmOrderInfoDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderJYCKConfirmRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderJYCKPackageDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderReceiverInfoDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderResponseDto;
import com.yatang.sc.xinyi.service.kidd.XinyiSaleOrderProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 心怡销售订单服务实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:46
 * @版本: v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class XinyiSaleOrderServiceImpl {

	private final KiddFacadeService				kiddFacadeService;

	private final XinyiSaleOrderProxyService	xinyiSaleOrderProxyService;

	@Resource(name = "returnOrderMQProducer")
	private SimpleMQProducer					returnOrderMQProducer;		// 退货换货单
	@Resource(name = "orderMessageSender")
	private OrderMessageSender					saleOrderMQProducer;		// 销售订单mq

	@Resource(name = "purcharseRefundMQProducer")
	private SimpleMQProducer					purchaseRefundMQProducer;	// 采购退货mq



	@EventListener
	public Response<String> add(KiddSaleOrderDto saleOrderDto) {

		if (null == saleOrderDto || null == saleOrderDto.getDeliveryOrder()
				|| StringUtils.isEmpty(saleOrderDto.getDeliveryOrder().getWarehouseCode())) {
			log.error("service--add>>请求参数出错：{}", JSON.toJSONString(saleOrderDto));
			throw new RuntimeException("service--add>>请求参数出错" + JSON.toJSONString(saleOrderDto));
		}
		KiddUtils.checkXinyiListening(
				kiddFacadeService.warehouseInterfaceProvider(saleOrderDto.getDeliveryOrder().getWarehouseCode()));
		log.info("service----add>>心怡处理新增销售订单--请求参数:{}", JSON.toJSONString(saleOrderDto));
		// 数据转换

		SaleOrderRequestDto saleOrderRequestDto = BeanConvertUtils.convert(saleOrderDto, SaleOrderRequestDto.class);
		SaleOrderResponseDto responseDto;
		if ("DSXS".equals(saleOrderDto.getDeliveryOrder().getScOrderType())
				|| "XCC".equals(saleOrderDto.getDeliveryOrder().getScOrderType())) {

			saleOrderRequestDto = kiddFacadeService.setSaleOrderRequestDto(saleOrderDto);
			log.info("deliveryorder.create,param{}", JSON.toJSONString(saleOrderRequestDto));
			responseDto = xinyiSaleOrderProxyService.commonAdd(saleOrderRequestDto);
		} else {
			saleOrderRequestDto.getDeliveryOrder().setOrderType(KiddOrderLogisticsType.XY_PTCK);// 心怡特有 PTCK=普通出库单
			SaleOrderReceiverInfoDto receiverInfo = saleOrderRequestDto.getDeliveryOrder().getReceiverInfo();
			receiverInfo.setCompany(saleOrderDto.getDeliveryOrder().getRemarkAddress());//传加盟商名称，规则如下:雅堂小超_No.000999(凯华丽景便利店)

			// 设置心怡itemPrice
			for (SaleOrderCargoDto saleOrderCargoDto : saleOrderRequestDto.getOrderLines()) {
				saleOrderCargoDto.setItemPrice(saleOrderCargoDto.getRetailPrice());
			}

			responseDto = xinyiSaleOrderProxyService.add(saleOrderRequestDto);
		}

		log.info("service----add>>心怡处理新增销售订单返回结果：{}", JSON.toJSONString(responseDto));
		Response<String> response = new Response<>();
		if (Objects.equal(responseDto.getFlag(), ResponseDto.SUCCESS)) {
			response.setSuccess(true);

			// 成功后直接已下发
			KiddOrderNoticeInfoDto requestDto = new KiddOrderNoticeInfoDto();
			requestDto.setOrderCode( saleOrderRequestDto.getDeliveryOrder().getDeliveryOrderCode() );
			requestDto.setCurrentStatus( KiddOrderLogisticsType.KIDD_ACCEPT );

			OrderMessage saleOrderMessage = new OrderMessage();
			saleOrderMessage.setMssageType( OrderMessageType.KIDD_ORDER_STATUS_NOTIFY );
			saleOrderMessage.setOrderId( requestDto.getOrderCode() );
			saleOrderMessage.setBody( JSON.toJSONString( requestDto ) );
			saleOrderMQProducer.sendMsg( saleOrderMessage );

		} else {
			response.setCode(responseDto.getCode());
			response.setSuccess(false);
			response.setErrorMessage(responseDto.getMessage());
		}
		return response;
	}



	/***
	 * 销售出库单订单确认
	 * 
	 * @param confirmRequestDto
	 * @return
	 */
	@EventListener
	public Response<Void> saleOrderConfirm(SaleOrderConfirmRequestDto confirmRequestDto) {
		Response<Void> responseDto = new Response();
		log.info("service----add>>心怡处理销售订单出库确认--请求参数:{}", JSON.toJSONString(confirmRequestDto));
		try {
			if (KiddOrderLogisticsType.XY_PTCK.equals(confirmRequestDto.getDeliveryOrder().getOrderType())) {
				String status = confirmRequestDto.getDeliveryOrder().getStatus();// 获取订单状态
				if (KiddOrderLogisticsType.KIDD_DELIVERED.equals(status)) {// 出库确认==>待提货
					if (confirmRequestDto.getDeliveryOrder().getDeliveryOrderCode().contains("H")) {
						// 设置发送换货单发货状态确认MQ
						KiddReturnOrderMqDto kiddReturnOrderMqDto = new KiddReturnOrderMqDto();
						String orderCode = confirmRequestDto.getDeliveryOrder().getDeliveryOrderCode();
						kiddReturnOrderMqDto.setReturnOrderCode(orderCode.substring(0, orderCode.length() - 1));// 退货单编码
						kiddReturnOrderMqDto.setStatus(status);// 设置业务状态
						kiddReturnOrderMqDto.setOrderType(KiddOrderLogisticsType.KIDD_HHCK);// 设置业务类型
						kiddReturnOrderMqDto.setWarehouseCode(confirmRequestDto.getDeliveryOrder().getWarehouseCode());// 仓库编码
						log.info("心怡处理换货订单出库发送mq参数 kiddReturnOrderMqDto {}" , JSON.toJSONString(kiddReturnOrderMqDto));
						returnOrderMQProducer.sendMsg(kiddReturnOrderMqDto);
					}else {
						// 设置mq发送Kidd
						OrderMessage saleOrderMessage = new OrderMessage();
						saleOrderMessage.setMssageType(OrderMessageType.KIDD_ORDER_STATUS_NOTIFY);
						saleOrderMessage.setOrderId(confirmRequestDto.getDeliveryOrder().getDeliveryOrderCode());
						KiddOrderNoticeInfoDto kiddOrderNoticeInfoDto = new KiddOrderNoticeInfoDto();

						// 补充其中的物流信息
						kiddOrderNoticeInfoDto.setExpressCode(confirmRequestDto.getDeliveryOrder().getExpressCode());
						kiddOrderNoticeInfoDto.setLogisticsCode(confirmRequestDto.getDeliveryOrder().getLogisticsCode());
						kiddOrderNoticeInfoDto.setLogisticsName(confirmRequestDto.getDeliveryOrder().getLogisticsName());

						kiddOrderNoticeInfoDto.setCurrentStatus(KiddOrderLogisticsType.KIDD_READY);// 待提货
						kiddOrderNoticeInfoDto.setOrderCode(confirmRequestDto.getDeliveryOrder().getDeliveryOrderCode());
						saleOrderMessage.setBody(JSON.toJSONString(kiddOrderNoticeInfoDto));
						saleOrderMQProducer.sendMsg(saleOrderMessage);
					}
					responseDto.setCode("001");
					responseDto.setSuccess(true);
					responseDto.setErrorMessage("销售订单出库确认成功");
				} else {
					// 出库单确认失败idd
					responseDto.setCode("002");
					responseDto.setSuccess(false);
					responseDto.setErrorMessage("销售订单出库确认失败");
				}
			} else if (KiddOrderLogisticsType.KIDD_CGTH.equals(confirmRequestDto.getDeliveryOrder().getOrderType())) {
				// 采购退货出库
				KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = SaleOrderConfirm2KiddOrderConfirm(
						confirmRequestDto);
				purchaseRefundMQProducer.sendMsg(kiddPurchaseOrderConfirmDto);
				responseDto.setCode("001");
				responseDto.setSuccess(true);
				responseDto.setErrorMessage("采购退货单出库确认成功");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			responseDto.setCode("002");
			responseDto.setSuccess(false);
			responseDto.setErrorMessage("销售订单出库确认失败" + e.getMessage());
		}
		return responseDto;
	}



	/**
	 * 发货确认
	 *
	 * @param saleOrderDeliveryConfirmRequestDto
	 * @return
	 */
	@EventListener
	public Response<Void> deliveryOrderConfirm(SaleOrderJYCKConfirmRequestDto saleOrderDeliveryConfirmRequestDto) {
		Response<Void> responseDto = new Response<>();
		log.info("service----deliveryOrderConfirm>>心怡处理订单发货确认--请求参数:{}",
				JSON.toJSONString(saleOrderDeliveryConfirmRequestDto));
		try {
			String status = saleOrderDeliveryConfirmRequestDto.getDeliveryOrder().getStatus();// 获取订单状态
			if (KiddOrderLogisticsType.KIDD_DELIVERED.equals(status)) {// 出库确认==>待提货
				// 设置mq发送Kidd
				OrderMessage saleOrderMessage = new OrderMessage();
				saleOrderMessage.setMssageType(OrderMessageType.KIDD_ORDER_STATUS_NOTIFY);
				saleOrderMessage
						.setOrderId(saleOrderDeliveryConfirmRequestDto.getDeliveryOrder().getDeliveryOrderCode());
				KiddOrderNoticeInfoDto kiddOrderNoticeInfoDto = new KiddOrderNoticeInfoDto();
				if (CollectionUtils.isEmpty(saleOrderDeliveryConfirmRequestDto.getPackages())) {
					log.error("service----deliveryOrderConfirm>>心怡处理订单发货确认--物流信息获取失败");
					// 出库单确认失败idd
					responseDto.setCode("002");
					responseDto.setSuccess(false);
					responseDto.setErrorMessage("销售订单发货确认失败，物流信息获取失败");
				}
				// 补充其中的物流信息
				StringBuilder expressCode = new StringBuilder();
				for (SaleOrderJYCKPackageDto packageDto : saleOrderDeliveryConfirmRequestDto.getPackages()) {
					if (packageDto != null && !StringUtils.isEmpty(packageDto.getExpressCode())) {
						if (expressCode.length() > 0) {
							expressCode.append(";");
						}
						expressCode.append(packageDto.getExpressCode());
					}
				}
				kiddOrderNoticeInfoDto.setExpressCode(expressCode.toString());
				String logisticsCode = saleOrderDeliveryConfirmRequestDto.getPackages().get(0).getLogisticsCode();
				String logisticsName = saleOrderDeliveryConfirmRequestDto.getPackages().get(0).getLogisticsName();
				if (StringUtils.isEmpty(logisticsName)) {
					logisticsName = LogisticsDictionary.getlogisticsName(logisticsCode);
				}
				kiddOrderNoticeInfoDto.setLogisticsCode(logisticsCode);
				kiddOrderNoticeInfoDto.setLogisticsName(logisticsName);

				kiddOrderNoticeInfoDto.setCurrentStatus(KiddOrderLogisticsType.KIDD_READY);// 待提货
				kiddOrderNoticeInfoDto
						.setOrderCode(saleOrderDeliveryConfirmRequestDto.getDeliveryOrder().getDeliveryOrderCode());
				saleOrderMessage.setBody(JSON.toJSONString(kiddOrderNoticeInfoDto));
				log.info("deliveryOrderConfirm,mq{}", JSON.toJSONString(saleOrderMessage));
				saleOrderMQProducer.sendMsg(saleOrderMessage);
				responseDto.setCode("001");
				responseDto.setSuccess(true);
				responseDto.setErrorMessage("销售订单发货确认成功");
			} else {
				// 出库单确认失败idd
				responseDto.setCode("002");
				responseDto.setSuccess(false);
				responseDto.setErrorMessage("销售订单发货确认失败");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			responseDto.setCode("002");
			responseDto.setSuccess(false);
			responseDto.setErrorMessage("销售订单发货确认失败" + e.getMessage());
		}

		return responseDto;
	}



	/**
	 * 将心怡出库确认接口传过来的参数转成 我们自己mq的入参对象
	 * 
	 * @param saleOrderConfirmRequestDto
	 * @author yinyuxin
	 * @return
	 */
	public KiddPurchaseOrderConfirmDto SaleOrderConfirm2KiddOrderConfirm(
			SaleOrderConfirmRequestDto saleOrderConfirmRequestDto) {
		KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = new KiddPurchaseOrderConfirmDto();
		KiddConfirmEntryOrderDto kiddConfirmEntryOrderDto = new KiddConfirmEntryOrderDto();
		List<KiddConfirmOrderLinesDto> kiddConfirmOrderLinesDtos = new ArrayList<>();

		SaleOrderConfirmDeliveryInfoDto saleOrderConfirmDeliveryInfoDto = saleOrderConfirmRequestDto.getDeliveryOrder();
		List<SaleOrderConfirmOrderInfoDto> saleOrderConfirmOrderInfoDtos = saleOrderConfirmRequestDto.getOrderLines();

		if (saleOrderConfirmDeliveryInfoDto != null) {
			kiddConfirmEntryOrderDto.setEntryOrderCode(saleOrderConfirmDeliveryInfoDto.getDeliveryOrderCode());
			kiddConfirmEntryOrderDto.setWarehouseCode(saleOrderConfirmDeliveryInfoDto.getWarehouseCode());
			kiddConfirmEntryOrderDto.setEntryOrderId(saleOrderConfirmDeliveryInfoDto.getDeliveryOrderId());
			kiddConfirmEntryOrderDto.setEntryOrderType(saleOrderConfirmDeliveryInfoDto.getOrderType());
			kiddConfirmEntryOrderDto.setStatus(saleOrderConfirmDeliveryInfoDto.getStatus());
			kiddConfirmEntryOrderDto.setOutBizCode(saleOrderConfirmDeliveryInfoDto.getOutBizCode());
			kiddConfirmEntryOrderDto.setOperateTime(saleOrderConfirmDeliveryInfoDto.getOrderConfirmTime());
			// 交货单号第三方暂时没提供（2017.12.15）
//			kiddConfirmEntryOrderDto.setDeliveryVoucherNo(saleOrderConfirmDeliveryInfoDto.getDeliveryVoucherNo());
		}

		if (saleOrderConfirmOrderInfoDtos != null && saleOrderConfirmOrderInfoDtos.size() > 0) {
			for (SaleOrderConfirmOrderInfoDto saleOrderConfirmOrderInfoDto : saleOrderConfirmOrderInfoDtos) {
				KiddConfirmOrderLinesDto kiddConfirmOrderLinesDto = new KiddConfirmOrderLinesDto();

				kiddConfirmOrderLinesDto.setOrderLineNo(saleOrderConfirmOrderInfoDto.getOrderLineNo());
				kiddConfirmOrderLinesDto.setItemCode(saleOrderConfirmOrderInfoDto.getItemCode());
				kiddConfirmOrderLinesDto.setItemName(saleOrderConfirmOrderInfoDto.getItemName());
				kiddConfirmOrderLinesDto.setInventoryType(saleOrderConfirmOrderInfoDto.getInventoryType());
				kiddConfirmOrderLinesDto.setActualQty(saleOrderConfirmOrderInfoDto.getActualQty());
				kiddConfirmOrderLinesDto.setBatchCode(saleOrderConfirmOrderInfoDto.getBatchCode());
				kiddConfirmOrderLinesDto.setProductDate(saleOrderConfirmOrderInfoDto.getProductDate());
				kiddConfirmOrderLinesDto.setExpireDate(saleOrderConfirmOrderInfoDto.getExpireDate());
				kiddConfirmOrderLinesDto.setProduceCode(saleOrderConfirmOrderInfoDto.getProduceCode());

				kiddConfirmOrderLinesDtos.add(kiddConfirmOrderLinesDto);
			}
		}

		kiddPurchaseOrderConfirmDto.setEntryOrder(kiddConfirmEntryOrderDto);
		kiddPurchaseOrderConfirmDto.setOrderLines(kiddConfirmOrderLinesDtos);
		return kiddPurchaseOrderConfirmDto;
	}

}
