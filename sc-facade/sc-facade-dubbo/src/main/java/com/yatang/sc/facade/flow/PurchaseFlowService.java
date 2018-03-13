package com.yatang.sc.facade.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.UpdateReceiptYQXStatusDto;
import com.yatang.sc.facade.dto.UpdateReceiptYSHStatusDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PurchaseOrderConfirmDto;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年8月7日
 */
public interface PurchaseFlowService {
	/**
	 * 
	 * <method description>更新为已收货状态
	 *
	 * @param PurchaseOrderConfirmDto
	 * @return
	 */
	Response<Boolean> updateYSHStatus(PurchaseOrderConfirmDto purchaseOrderConfirmDto);



	/**
	 * 
	 * <method description>更新为已取消状态
	 *
	 * @param updateReceiptYQXStatusDto
	 * @return
	 */
	Response<Boolean> updateYQXStatus(UpdateReceiptYQXStatusDto updateReceiptYQXStatusDto);

	Response<String>  createPurchaseReceiptASN(PmPurchaseReceiptDetailDto detailDto);
}
