package com.yatang.sc.kidd.service;

import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundStockoutCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnConfirmDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnDto;
import com.yatang.sc.kidd.dto.purchase.kiddPurchaseRefundConfirmResultDto;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/11/8 19:49
 * @version: v1.0
 */
@Service("kiddPurchaseReturnService")
public class KiddPurchaseReturnServiceImpl implements KiddPurchaseReturnService {

	@Autowired
	private ResultEventPublisher publisher;



	@Override
	public Response<String> createPurchaseRefund(KiddPurchaseRefundStockoutCreateDto kiddPurchaseRefundStockoutCreateDto) {
		Response<String> response = (Response<String>) publisher.publishEvent(kiddPurchaseRefundStockoutCreateDto);
		return response;
	}



	@Override
	public Response<kiddPurchaseRefundConfirmResultDto> confirmPurchaseRefund(
			KiddPurchaseReturnConfirmDto kiddPurchaseReturnConfirmDto) {
		Response<kiddPurchaseRefundConfirmResultDto> response = (Response<kiddPurchaseRefundConfirmResultDto>) publisher
				.publishEvent(kiddPurchaseReturnConfirmDto);
		return response;
	}
}
