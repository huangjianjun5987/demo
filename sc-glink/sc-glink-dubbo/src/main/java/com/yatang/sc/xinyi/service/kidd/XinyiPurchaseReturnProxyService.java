package com.yatang.sc.xinyi.service.kidd;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiPurchaseRefundStockoutCreateDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiPurchaseReturnParmDto;
import com.yatang.sc.xinyi.dto.returnrequest.XinyiReturnOrderRequestDto;
import org.springframework.stereotype.Service;

/**
 * @description: 采购退货单service（心怡）
 * @author: yinyuxin
 * @date: 2017/11/8 20:00
 * @version: v1.0
 */
@Service("xinyiPurchaseReturnProxyService")
public class XinyiPurchaseReturnProxyService {

	@KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "stockout.create", callType = CallTypeEnum.SYNC_CALL)
	public ResponseDto createPurchaseReturn(XinyiPurchaseRefundStockoutCreateDto xinyiPurchaseRefundStockoutCreateDto) {
		return null;
	}
}
