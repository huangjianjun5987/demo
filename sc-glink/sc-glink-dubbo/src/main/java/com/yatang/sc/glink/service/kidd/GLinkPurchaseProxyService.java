package com.yatang.sc.glink.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderRepDto;
import com.yatang.sc.glink.dto.entryOrder.SelectEntryOrderDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmResultDto;
import com.yatang.sc.glink.dto.purchase.PurchaseReturnOrderDto;

/**
 * @描述:际链接口的代理调用
 * @类名:GLinkPurchaseProxyService
 * @作者: lvheping
 * @创建时间: 2017/9/25 10:43
 * @版本: v1.0
 */
@Service(value = "gLinkPurchaseProxyService")
public class GLinkPurchaseProxyService {
	/**
	 * 推送采购单到际链
	 * 
	 * @param entryOrderDto
	 * @return
	 */
	@KiddCaller(method = "g2matrix.entryorder.creatUnloadingInfor", callType = CallTypeEnum.SYNC_CALL)
	public GlinkResponseDto<String> create(EntryOrderDto entryOrderDto) {
		return null;
	}



	@KiddCaller(method = "g2matrix.entryorder.selectUnloadingInfor", callType = CallTypeEnum.SYNC_CALL)
	public GlinkResponseDto<EntryOrderRepDto> select(SelectEntryOrderDto selectEntryOrderDto) {
		return null;
	}



	@KiddCaller(method = "g2matrix.outboundorder.outBoundInfor", callType = CallTypeEnum.SYNC_CALL)
	public GlinkResponseDto<Object> send(PurchaseReturnOrderDto purchaseReturnOrderDto) {
		return null;
	}



	@KiddCaller(method = "g2matrix.outboundorder.getUnOutBoundOK", callType = CallTypeEnum.SYNC_CALL)
	public GlinkResponseDto<PurchaseRefundConfirmResultDto> confirm(PurchaseRefundConfirmDto purchaseReturnDto) {
		return null;
	}
}
