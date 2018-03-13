package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;

/**
 * @description: 采购退货单write dubbo
 * @author: yinyuxin
 * @date: 2017/10/17 15:00
 * @version: v1.0
 */
public interface PmPurchaseRefundWriteDubboService {

	/**
	 * 生成采购退货单单号
	 *
	 * @return
	 */
	Response<String> getRefundNo();



	/**
	 * 更新采购退货单状态
	 *
	 * @return
	 */
	Response<Boolean> updateStatus(PmPurchaseRefundDto param);



	/**
	 * 新增采购退货单以及清单
	 *
	 * @param pmPurchaseRefundDto
	 * @return
	 */
	Response<Boolean> saveRefundWithItems(PmPurchaseRefundDto pmPurchaseRefundDto);



	/**
	 * 批量删除采购退货单以及清单
	 *
	 * @param purchaseRefundIds
	 *            退货单ID组
	 * @return
	 */
	Response<Boolean> deleteBatchRefundOrder(Long[] purchaseRefundIds);



	///**
	// * 审批采购退货单
	// *
	// * @param processAuditLogParamDto
	// * @return
	// */
	//Response<Boolean> approveRefund(ProcessAuditLogParamDto processAuditLogParamDto);



	/**
	 * 退货成功回调，修改库存和退货单状态
	 * 
	 * @param pmPurchaseRefundDto
	 * @return
	 */
	Response<Boolean> refundSuccessCallBack(PmPurchaseRefundDto pmPurchaseRefundDto);



	/**
	 *
	 * <method description>更新退货单状态为待退货
	 *
	 * @author zhoubaiyun
	 * @param entryOrderCode
	 * @return
	 */
	Response<Boolean> updateDTHStatus(String entryOrderCode);



	/**
	 *
	 * <method description>更新退货单状态为取消失败
	 *
	 * @author zhoubaiyun
	 * @param entryOrderCode
	 * @return
	 */
	Response<Boolean> updateQXSBStatus(String entryOrderCode);



	/**
	 *
	 * <method description>更新退货单状态为异常
	 *
	 * @author zhoubaiyun
	 * @param entryOrderCode
	 * @return
	 */
	Response<Boolean> updateYCStatus(String entryOrderCode);



	/**
	 *
	 * <method description>更新退货单状态为已取消
	 *
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundDto
	 * @return
	 */
	Response<Boolean> updateYQXStatus(PmPurchaseRefundDto pmPurchaseRefundDto);

	/**
	 * 审批流回调接口
	 * @param keyId 采购退货单主键id
	 * @param auditorId 审核人id
	 * @param auditorName 审核人姓名
	 * @param auditorResult 审核结果 true 同意  false 拒绝
	 * @return
	 */
	Response<Boolean> workFlowCallBack(Long keyId,String auditorId,String auditorName,Boolean auditorResult);

	/**
	 * @Description: 推送采购退货结算数据
	 * @author tankejia
	 * @date 2017/12/27- 11:46
	 * @param refundDto
	 */
	Response<Void> pushPurchaseRefundSettlementMsg(PmPurchaseRefundDto refundDto);
}
