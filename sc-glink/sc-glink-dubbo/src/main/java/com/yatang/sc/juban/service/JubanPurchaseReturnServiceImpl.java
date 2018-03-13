package com.yatang.sc.juban.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.juban.dto.ResponseDto;
import com.yatang.sc.juban.dto.purchase.JubanPurchaseRefundOrderLinesDto;
import com.yatang.sc.juban.dto.purchase.JubanPurchaseRefundStockoutCreateDto;
import com.yatang.sc.juban.dto.purchase.JubanPurchaseReturnParmDto;
import com.yatang.sc.juban.service.kidd.JubanPurchaseReturnProxyService;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundStockoutCreateDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
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
public class JubanPurchaseReturnServiceImpl {

	private static final Logger LOGGER= LoggerFactory.getLogger(JubanPurchaseReturnServiceImpl.class);

	/**
	 * 退货单心怡代理接口服务
	 */
	@Autowired
	private JubanPurchaseReturnProxyService jubanPurchaseReturnProxyService;

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
		KiddUtils.checkJubanListening(kiddFacadeService.warehouseInterfaceProvider(kiddPurchaseRefundStockoutCreateDto.getDeliveryOrder().getWarehouseCode()));
		Response<String> response=new Response<>();
		try {

			JubanPurchaseRefundStockoutCreateDto jubanPurchaseRefundStockoutCreateDto=new JubanPurchaseRefundStockoutCreateDto();

			jubanPurchaseRefundStockoutCreateDto.setOrderLines(BeanConvertUtils.convertList(kiddPurchaseRefundStockoutCreateDto.getOrderLines(), JubanPurchaseRefundOrderLinesDto.class));
			jubanPurchaseRefundStockoutCreateDto.setDeliveryOrder(	BeanConvertUtils.convert(kiddPurchaseRefundStockoutCreateDto.getDeliveryOrder(),JubanPurchaseReturnParmDto.class));
//		;
//			BeanConvertUtils.convert(kiddPurchaseRefundStockoutCreateDto,JubanPurchaseRefundStockoutCreateDto.class);
//
//			JubanPurchaseRefundStockoutCreateDto jubanPurchaseRefundStockoutCreateDto= BeanConvertUtils.convert(kiddPurchaseRefundStockoutCreateDto,JubanPurchaseRefundStockoutCreateDto.class);
//


			jubanPurchaseRefundStockoutCreateDto.setOrderLines(BeanConvertUtils.convertList(kiddPurchaseRefundStockoutCreateDto.getOrderLines(),JubanPurchaseRefundOrderLinesDto.class));
			LOGGER.info("推送采购退货单调用桔瓣接口参数：xinyiPurchaseRefundStockoutCreateDto{} ", JSON.toJSONString(jubanPurchaseRefundStockoutCreateDto));
			ResponseDto responseDto = jubanPurchaseReturnProxyService.createPurchaseReturn(jubanPurchaseRefundStockoutCreateDto);
			if (Objects.equal(responseDto.getFlag(), ResponseDto.SUCCESS)){
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);

				//成功则发送mq
				KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto=new KiddPurchaseOrderConfirmDto();
				KiddConfirmEntryOrderDto entryOrder=new KiddConfirmEntryOrderDto();
				entryOrder.setEntryOrderCode(kiddPurchaseRefundStockoutCreateDto.getDeliveryOrder().getDeliveryOrderCode());
				entryOrder.setStatus(KiddOrderLogisticsType.KIDD_ACCEPT);
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
			LOGGER.error("推送采购退货单调用桔瓣接口失败："+ExceptionUtils.getFullStackTrace(e));
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
