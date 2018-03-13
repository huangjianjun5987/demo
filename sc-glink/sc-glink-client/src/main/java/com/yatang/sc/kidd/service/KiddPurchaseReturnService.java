package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundStockoutCreateDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnConfirmDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnDto;
import com.yatang.sc.kidd.dto.purchase.kiddPurchaseRefundConfirmResultDto;

/**
 * @description: 采购退货单service （心怡际链公共用）
 * @author: yinyuxin
 * @date: 2017/11/8 19:46
 * @version: v1.0
 */
public interface KiddPurchaseReturnService {

	/**
	 * 采购退货单下发接口
	 * 
	 * @param kiddPurchaseReturnDto
	 * @return
	 */
	Response<String> createPurchaseRefund(KiddPurchaseRefundStockoutCreateDto kiddPurchaseRefundStockoutCreateDto);



	/**
	 * 采购退货下发确认接口
	 * 
	 * @return
	 */
	Response<kiddPurchaseRefundConfirmResultDto> confirmPurchaseRefund(
			KiddPurchaseReturnConfirmDto kiddPurchaseReturnConfirmDto);
}
