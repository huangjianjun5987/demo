package com.yatang.sc.juban.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.juban.dto.purchase.JubanPurchaseRefundStockoutCreateDto;
import com.yatang.sc.juban.dto.ResponseDto;

/**
 * @description: 采购退货单service（桔瓣）
 * @author: yinyuxin
 * @date: 2017/11/8 20:00
 * @version: v1.0
 */
@Service("jubanPurchaseReturnProxyService")
public class JubanPurchaseReturnProxyService {

	@KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "stockout.create", callType = CallTypeEnum.SYNC_CALL)
	public ResponseDto createPurchaseReturn(JubanPurchaseRefundStockoutCreateDto jubanPurchaseRefundStockoutCreateDto) {
		return null;
	}
}
