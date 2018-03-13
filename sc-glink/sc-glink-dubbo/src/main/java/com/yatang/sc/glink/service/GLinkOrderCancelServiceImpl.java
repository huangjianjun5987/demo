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
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.glink.dto.CancelAllOrdersDto;
import com.yatang.sc.glink.dto.CancelAllOrdersRepDto;
import com.yatang.sc.glink.dto.CancelOrdersDto;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.service.kidd.GLinkOrderCancelProxyService;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述:际链单据取消
 * @类名:GLinkOrderCancelServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/26 14:33
 * @版本: v1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GLinkOrderCancelServiceImpl {

	@Autowired
	private GLinkOrderCancelProxyService	gLinkOrderCancelProxyService;

	private final KiddFacadeService			kiddFacadeService;



	/**
	 * 取消单据
	 * 
	 * @param kiddCancelAllOrdersDto
	 * @return
	 */
	@EventListener
	public Response<String> cancel(KiddCancelAllOrdersDto kiddCancelAllOrdersDto) {
		log.info("service----cancel>>订单取消--请求参数:{}", JSON.toJSONString(kiddCancelAllOrdersDto));
		// 根据仓库的标识决定走心怡或者际链
		if (null == kiddCancelAllOrdersDto || null == kiddCancelAllOrdersDto.getOrders()
				|| StringUtils.isBlank(kiddCancelAllOrdersDto.getOrders().get(0).getWarehouseCode())) {
			throw new RuntimeException("service----cancel>>传递参数出错");
		}
		KiddUtils.checkGlinkListening(kiddFacadeService
				.warehouseInterfaceProvider(kiddCancelAllOrdersDto.getOrders().get(0).getWarehouseCode()));

		// 逻辑判断
		Response<String> response = new Response<>();
		try {
			CancelAllOrdersDto cancelAllOrdersDto = BeanConvertUtils.convert(kiddCancelAllOrdersDto,
					CancelAllOrdersDto.class);
			List<CancelOrdersDto> lists = BeanConvertUtils.convertList(kiddCancelAllOrdersDto.getOrders(),
					CancelOrdersDto.class);
			// 订单取消操作
			if (KiddOrderLogisticsType.KIDD_XSCK.equals(kiddCancelAllOrdersDto.getOrderType())) {// 自定义类型--
																									// kidd销售出库glink单取消==>销售单取消操作
				cancelAllOrdersDto.setType(2);// 重复订单跳过(skip);
				for (CancelOrdersDto cancelOrdersDto : lists) {
					cancelOrdersDto.setOrderType(KiddOrderLogisticsType.JL_FHCK);// XSCK=销售出库
				}
			} else if (KiddOrderLogisticsType.KIDD_CGTH.equalsIgnoreCase(lists.get(0).getOrderType())) {
				cancelAllOrdersDto.setType(2);// 重复订单跳过(skip);
				lists.get(0).setOrderType(KiddOrderLogisticsType.KIDD_CGTHCK);// CGTH=采购退货
			}
			cancelAllOrdersDto.setOrders(lists);
			GlinkResponseDto<CancelAllOrdersRepDto> close = gLinkOrderCancelProxyService.cancel(cancelAllOrdersDto);
			log.info("service----cancel>>际链处理订单取消--返回:{}", JSON.toJSONString(close));
			if (null == close || close.getCode() != 0) {
				response.setCode(CommonsEnum.RESPONSE_20051.getCode());
				response.setSuccess(false);
				response.setErrorMessage(close.getMessage());
			} else {
				response.setSuccess(true);
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
