package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.pm.*;

/**
 * @description: 采购退货单service
 * @author: yinyuxin
 * @date: 2017/10/17 14:56
 * @version: v1.0
 */
public interface PmpurchaseRefundService {

	/**
	 * 新增采购退货单
	 * 
	 * @param pmPurchaseRefundPo
	 * @return
	 */
	boolean createRefundWithItems(PmPurchaseRefundPo pmPurchaseRefundPo);



	/**
	 * 修改采购退货单（不带清单修改）
	 *
	 * @param pmPurchaseRefundPo
	 * @return
	 */
	boolean updateRefund(PmPurchaseRefundPo pmPurchaseRefundPo);



	/**
	 * 修改采购退货单（带清单修改）(用于提交采购退货单)
	 * 
	 * @param pmPurchaseRefundPo
	 * @return
	 */
	boolean updateRefundWithItems(PmPurchaseRefundPo pmPurchaseRefundPo);

	/**
	 * 修改采购退货单（待清单修改）（按照id直接修改，会根据数据去修改库存）（用于仓库退货回调）
	 * @param pmPurchaseRefundPo
	 * @return
	 */
	boolean updateRefundDetail(PmPurchaseRefundPo pmPurchaseRefundPo);


	/**
	 * 
	 * <method description>根据条件查询退货单列表
	 * 
	 * @author zhoubaiyun
	 * @return
	 */
	PageInfo<PmPurchaseRefundPo> queryPmPurchaseRefund(PmPurchaseRefundExtPo pmPurchaseRefundExtPo);

	/**
	 *
	 * <method description>根据条件查询供应商退货单列表
	 *
	 * @author leixin
	 * @return
	 */
	PageInfo<PmPurchaseReturnProviderPo> queryPmPurchaseRefundProvider(PmPurchaseRefundProviderExtPo pmPurchaseRefundProviderExtPo);

	/**
	 * @Description: 查询采购退货详情
	 * @author tankejia
	 * @date 2017/10/18- 11:38
	 * @param id
	 */
	PmPurchaseRefundPo selectRefundDetailById(Long id);

	/**
	 * @Description: 查询供应商采购退货详情
	 * @author leixin
	 * @param id
	 */
    PmPurchaseRefundProviderPo selectRefundDetailProviderById(Long id);

	/**
	 * 查询采购退货单（不带清单）
	 * @param id
	 * @author yinyuxin
	 * @return
	 */
	PmPurchaseRefundPo selectRefundById(Long id);



	/**
	 * 
	 * <method description>根据审批列表条件查询审批列表
	 * 
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundAuditQueryPo
	 * @return
	 */
	/*
	PageInfo<PmPurchaseRefundWithProcessNodeNamePo> queryPurchaseRefundAuditList(
			PmPurchaseRefundAuditQueryPo pmPurchaseRefundAuditQueryPo);
	*/


	/**
	 * @Description:批量删除退货单
	 * @author huangjianjun
	 * @date 2017年10月19日下午5:27:01
	 */
	void deleteBatchRefundOrder(Long[] purchaseRefundIds);



	/**
	 * @Description:查询退货单号组是否包含有非制单状态的退货单
	 * @author huangjianjun
	 * @date 2017年10月19日下午5:27:01
	 */
	boolean queryContainUndraftRefundOrder(Long[] purchaseRefundIds);



	/**
	 * 结束审批流程
	 * 
	 * @param id
	 * @return
	 */
	/*
	boolean endApproveProcess(Long id,String auditUserId,Integer status);
	*/


	/**
	 * @Description: 更新退货单状态
	 * @author huangjianjun
	 * @date 2017年11月10日下午4:52:30
	 */
	boolean updateStatus(PmPurchaseRefundPo pmPurchaseRefundPo);



	/**
	 * 
	 * <method description>根据采购退货单编号查询采购退货单
	 * 
	 * @author zhoubaiyun
	 * @param purchaseRefundNo
	 * @return
	 */
	PmPurchaseRefundPo selectPurchaseRefundByPurchaseRefundNo(String purchaseRefundNo);



	/**
	 * 
	 * <method description>根据采购退货单单号更新采购退货单状态
	 * 
	 * @author zhoubaiyun
	 * @param purchaseRefundNo
	 * @param status
	 * @return
	 */
	int updatePurchaseRefundStatusByPurchaseRefundNo(String purchaseRefundNo, Integer status);


	/**
	 * @Description: 根据状态查询退货单的记录数
	 * @author tankejia
	 * @date 2017/12/27- 15:07
	 * @param
	 */
	long getAllPurchaseRefundCount(Integer status);

}
