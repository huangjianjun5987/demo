package com.yatang.sc.xinyi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundStockoutCreateDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiPurchaseRefundOrderLinesDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiPurchaseRefundStockoutCreateDto;
import com.yatang.sc.xinyi.service.kidd.XinyiPurchaseReturnProxyService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/11/8 20:14
 * @version: v1.0
 */
@Service
public class XinyiPurchaseReturnServiceImpl {

	private static final Logger LOGGER= LoggerFactory.getLogger(XinyiPurchaseReturnServiceImpl.class);

	/**
	 * 退货单心怡代理接口服务
	 */
	@Autowired
	private XinyiPurchaseReturnProxyService xinyiPurchaseReturnProxyService;

	@Autowired
	private KiddFacadeService kiddFacadeService;

	@Resource(name = "purcharseRefundMQProducer")
	private SimpleMQProducer purchaseRefundMQProducer;	// 采购退货mq



	/**
	 * 下发采购退货单（心怡）
	 * @param kiddPurchaseRefundStockoutCreateDto
	 * @return
	 */
	@EventListener
	public Response<String> createPurchaseReturn(KiddPurchaseRefundStockoutCreateDto kiddPurchaseRefundStockoutCreateDto){
		LOGGER.info("下发采购退货单,参数:{}", JSONObject.toJSONString(kiddPurchaseRefundStockoutCreateDto));

		if (null == kiddPurchaseRefundStockoutCreateDto.getDeliveryOrder() || StringUtils.isEmpty(kiddPurchaseRefundStockoutCreateDto.getDeliveryOrder().getWarehouseCode())) {
			LOGGER.error("下发采购退货单,请求参数出错：{}", JSON.toJSONString(kiddPurchaseRefundStockoutCreateDto));
			throw new RuntimeException("下发采购退货单,请求参数出错");
		}
		KiddUtils.checkXinyiListening(kiddFacadeService.warehouseInterfaceProvider(kiddPurchaseRefundStockoutCreateDto.getDeliveryOrder().getWarehouseCode()));
		Response<String> response=new Response<>();
		try {
			XinyiPurchaseRefundStockoutCreateDto xinyiPurchaseRefundStockoutCreateDto= BeanConvertUtils.convert(kiddPurchaseRefundStockoutCreateDto,XinyiPurchaseRefundStockoutCreateDto.class);
			xinyiPurchaseRefundStockoutCreateDto.setOrderLines(BeanConvertUtils.convertList(kiddPurchaseRefundStockoutCreateDto.getOrderLines(),XinyiPurchaseRefundOrderLinesDto.class));
			LOGGER.info("推送采购退货单调用心怡接口参数：xinyiPurchaseRefundStockoutCreateDto{} ", JSON.toJSONString(xinyiPurchaseRefundStockoutCreateDto));
			ResponseDto responseDto = xinyiPurchaseReturnProxyService
					.createPurchaseReturn(xinyiPurchaseRefundStockoutCreateDto);
			if (Objects.equal(responseDto.getFlag(),ResponseDto.SUCCESS)){
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);

				//成功则发送mq
				KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto=new KiddPurchaseOrderConfirmDto();
				KiddConfirmEntryOrderDto entryOrder=new KiddConfirmEntryOrderDto();
				entryOrder.setEntryOrderCode(kiddPurchaseRefundStockoutCreateDto.getDeliveryOrder().getDeliveryOrderCode());
				entryOrder.setStatus("ACCEPT");
				kiddPurchaseOrderConfirmDto.setEntryOrder(entryOrder);
				LOGGER.info("采购退货单下发仓库结果mq：kiddPurchaseOrderConfirmDto{} ", JSON.toJSONString(kiddPurchaseOrderConfirmDto));
				purchaseRefundMQProducer.sendMsg(kiddPurchaseOrderConfirmDto);
			}else {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage(responseDto.getMessage());
			}
		}catch (Exception e){
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			LOGGER.error("推送采购退货单调用心怡接口失败："+ExceptionUtils.getFullStackTrace(e));
			response.setSuccess(false);
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
		}
			return response;
	}



	///**
	// * 采购退货出库确认（心怡）
	// * @param kiddPurchaseReturnConfirmDto
	// * @return
	// */
	//@EventListener
	//public Response<kiddPurchaseRefundConfirmResultDto> confirmPurchaseRefund(
	//		KiddPurchaseReturnConfirmDto kiddPurchaseReturnConfirmDto) {
	//	LOGGER.info("采购退货单出库确认,参数:{}", JSONObject.toJSONString(kiddPurchaseReturnConfirmDto));
	//	if (null == kiddPurchaseReturnConfirmDto || StringUtils.isEmpty(kiddPurchaseReturnConfirmDto.getWarehouseCode())) {
	//		LOGGER.error("下发采购退货单,请求参数出错：{}", JSON.toJSONString(kiddPurchaseReturnConfirmDto));
	//		throw new RuntimeException("下发采购退货单,请求参数出错：{}" + JSON.toJSONString(kiddPurchaseReturnConfirmDto));
	//	}
	//	KiddUtils.checkXinyiListening(kiddFacadeService.warehouseInterfaceProvider(kiddPurchaseReturnConfirmDto.getWarehouseCode()));
	//	Response<String> response=new Response<>();
	//
	//}

}
