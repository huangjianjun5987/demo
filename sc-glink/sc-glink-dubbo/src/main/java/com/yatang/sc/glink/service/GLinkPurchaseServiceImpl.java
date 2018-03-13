package com.yatang.sc.glink.service;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.OrderLinesDto;
import com.yatang.sc.glink.dto.SenderInfoDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderDto;
import com.yatang.sc.glink.dto.entryOrder.GetInfoDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmResultDto;
import com.yatang.sc.glink.dto.purchase.PurchaseReturnOrderDto;
import com.yatang.sc.glink.service.kidd.GLinkPurchaseProxyService;
import com.yatang.sc.kidd.dto.purchase.KiddEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundStockoutCreateDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnConfirmDto;
import com.yatang.sc.kidd.dto.purchase.kiddPurchaseRefundConfirmResultDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述:
 * @类名:GLinkPurchaseServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/25 10:10
 * @版本: v1.0
 */
@Service
@Slf4j
public class GLinkPurchaseServiceImpl {
	@Autowired
	private GLinkPurchaseProxyService	gLinkPurchaseProxyService;
	@Autowired
	private KiddFacadeService			kiddFacadeService;



	@EventListener
	public Response<String> add(KiddEntryOrderDto kiddEntryOrderDto) {
		log.info("推送采购单：KiddEntryOrderDto " + JSON.toJSONString(kiddEntryOrderDto));

		if (null == kiddEntryOrderDto || StringUtils.isBlank(kiddEntryOrderDto.getWareHouseCode())) {
			log.error("service----add>>传递参数出错仓库编号不存在:{}", JSON.toJSONString(kiddEntryOrderDto));
			throw new RuntimeException("service----add>>传递参数出错仓库编号不存在");
		}
		KiddUtils.checkGlinkListening(
				kiddFacadeService.warehouseInterfaceProvider(kiddEntryOrderDto.getWareHouseCode()));
		// 逻辑判断
		Response<String> response = new Response<>();
		try {
			EntryOrderDto entryOrderDto = BeanConvertUtils.convert(kiddEntryOrderDto, EntryOrderDto.class);
			SenderInfoDto convert = BeanConvertUtils.convert(kiddEntryOrderDto.getSenderInfoDto(), SenderInfoDto.class);
			GetInfoDto getInfoDto = BeanConvertUtils.convert(kiddEntryOrderDto.getGetInfoDto(), GetInfoDto.class);
			List<OrderLinesDto> orderLinesDtos = BeanConvertUtils.convertList(kiddEntryOrderDto.getOrderLines(),
					OrderLinesDto.class);
			entryOrderDto.setOrderLines(orderLinesDtos);
			entryOrderDto.setGetInfoDto(getInfoDto);
			entryOrderDto.setSenderInfoDto(convert);

			GlinkResponseDto<String> stringGlinkResponseDto = gLinkPurchaseProxyService.create(entryOrderDto);
			if (stringGlinkResponseDto.getCode() == 0) {
				response.setSuccess(true);
			} else {
				response.setSuccess(false);
				response.setErrorMessage(stringGlinkResponseDto.getMessage());
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
		}
		return response;
	}



	/**
	 * 
	 * <method description>下发采购退货单
	 * 
	 * @author zhoubaiyun
	 * @param kiddPurchaseRefundStockoutCreateDto
	 * @return
	 */
	@EventListener
	public Response<Object> send(KiddPurchaseRefundStockoutCreateDto kiddPurchaseRefundStockoutCreateDto) {
		log.info("下发采购退货单：KiddEntryOrderDto " + JSON.toJSONString(kiddPurchaseRefundStockoutCreateDto));

		if (null == kiddPurchaseRefundStockoutCreateDto
				|| StringUtils.isBlank(kiddPurchaseRefundStockoutCreateDto.getOrder().getWarehouseCode())) {
			log.error("service----send>>传递参数出错仓库编号不存在:{}", JSON.toJSONString(kiddPurchaseRefundStockoutCreateDto));
			throw new RuntimeException("service----send>>传递参数出错仓库编号不存在");
		}
		KiddUtils.checkGlinkListening(kiddFacadeService
				.warehouseInterfaceProvider(kiddPurchaseRefundStockoutCreateDto.getOrder().getWarehouseCode()));
		// 逻辑判断
		Response<Object> response = new Response<>();
		try {
			PurchaseReturnOrderDto purchaseReturnOrderDto = BeanConvertUtils
					.convert(kiddPurchaseRefundStockoutCreateDto.getOrder(), PurchaseReturnOrderDto.class);
			purchaseReturnOrderDto.setOrderType(KiddOrderLogisticsType.KIDD_CGTHCK);
			purchaseReturnOrderDto.setLogisticsCode(KiddOrderLogisticsType.KIDD_OTHER);// 必须设置
			GlinkResponseDto<Object> stringGlinkResponseDto = gLinkPurchaseProxyService.send(purchaseReturnOrderDto);
			log.info("gLinkPurchaseProxyService.send(purchaseReturnDto),参数为：{}，返回结果为：{}",
					JSON.toJSONString(purchaseReturnOrderDto), JSON.toJSONString(stringGlinkResponseDto));
			if (stringGlinkResponseDto.getCode() == 0) {
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
			} else {
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setErrorMessage(stringGlinkResponseDto.getMessage());
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
		}
		return response;
	}



	/**
	 * 
	 * <method description>确认采购退货单
	 * 
	 * @author zhoubaiyun
	 * @param
	 * @return
	 */
	@EventListener
	public Response<kiddPurchaseRefundConfirmResultDto> confirm(
			KiddPurchaseReturnConfirmDto kiddPurchaseReturnConfirmDto) {
		log.info("确认采购退货单：kiddPurchaseReturnConfirmDto " + JSON.toJSONString(kiddPurchaseReturnConfirmDto));

		if (null == kiddPurchaseReturnConfirmDto
				|| StringUtils.isBlank(kiddPurchaseReturnConfirmDto.getWarehouseCode())) {
			log.error("service----confirm>>传递参数出错仓库编号不存在:{}", JSON.toJSONString(kiddPurchaseReturnConfirmDto));
			throw new RuntimeException("confirm----confirm>>传递参数出错仓库编号不存在");
		}
		KiddUtils.checkGlinkListening(
				kiddFacadeService.warehouseInterfaceProvider(kiddPurchaseReturnConfirmDto.getWarehouseCode()));
		// 逻辑判断
		Response<kiddPurchaseRefundConfirmResultDto> response = new Response<>();
		try {
			PurchaseRefundConfirmDto purchaseRefundConfirmDto = BeanConvertUtils.convert(kiddPurchaseReturnConfirmDto,
					PurchaseRefundConfirmDto.class);

			GlinkResponseDto<PurchaseRefundConfirmResultDto> glinkResponseDto = gLinkPurchaseProxyService
					.confirm(purchaseRefundConfirmDto);

			log.info("gLinkPurchaseProxyService.confirm(kiddPurchaseReturnConfirmDto),参数为：{}，返回结果为：{}",
					JSON.toJSONString(purchaseRefundConfirmDto), JSON.toJSONString(glinkResponseDto));
			if (glinkResponseDto.getCode() == 0) {
				kiddPurchaseRefundConfirmResultDto kiddPurchaseRefundConfirmResultDto = BeanConvertUtils
						.convert(glinkResponseDto.getData(), kiddPurchaseRefundConfirmResultDto.class);
				response.setSuccess(true);
				response.setResultObject(kiddPurchaseRefundConfirmResultDto);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
			} else {
				response.setSuccess(false);
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setErrorMessage(glinkResponseDto.getMessage());
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
		}
		return response;
	}
}
