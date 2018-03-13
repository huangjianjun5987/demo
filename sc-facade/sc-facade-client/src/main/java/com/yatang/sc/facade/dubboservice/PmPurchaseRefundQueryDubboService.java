package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.*;

/**
 * @description: 采购退货单query dubbo
 * @author: yinyuxin
 * @date: 2017/10/17 15:01
 * @version: v1.0
 */
public interface PmPurchaseRefundQueryDubboService {
	/**
	 * 
	 * <method description>根据条件查询退货单列表
	 * 
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundQueryListDto
	 * @return
	 */
	Response<PageResult<PmPurchaseRefundDto>> queryPmPurchaseRefund(
			PmPurchaseRefundQueryListDto pmPurchaseRefundQueryListDto);



	/**
	 * @Description: 查询采购退货详情
	 * @author tankejia
	 * @date 2017/10/18- 11:38
	 * @param id
	 */
	Response<PmPurchaseRefundDto> queryRefundDetailById(Long id);



	/**
	 * 
	 * <method description>查询退货单审批列表
	 * 
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundAuditQueryDto
	 * @return
	 */
	/*
	Response<PageResult<PmPurchaseRefundAuditResultDto>> queryPmPurchaseRefundAudit(
			PmPurchaseRefundAuditQueryDto pmPurchaseRefundAuditQueryDto);

	*/
	/**
	 * @Description: 查询供应商采购退货列表
	 * @author leixin
	 * @date 2018/01/09- 11:38
	 * @param pmPurchaseRefundQueryProviderDto
	 */
	Response<PageResult<PmPurchaseRefundQueryProviderDto>> queryPmPurchaseRefundProviderList(PmPurchaseRefundQueryProviderDto pmPurchaseRefundQueryProviderDto);

	/**
	 * @Description: 查询供应商采购退货详情
	 * @author leixin
	 * @date 2018/01/09- 11:38
	 * @param id
	 */
	Response<PmPurchaseRefundProviderDto> queryRefundDetailProviderById(Long id);
}
