package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.pm.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmPurchaseRefundDao {
	int deleteByPrimaryKey(Long id);



	int insert(PmPurchaseRefundPo record);



	int insertSelective(PmPurchaseRefundPo record);



	PmPurchaseRefundPo selectByPrimaryKey(Long id);



	int updateByPrimaryKeySelective(PmPurchaseRefundPo record);
	
	
	int updateStatus(PmPurchaseRefundPo record);



	int updateByPrimaryKey(PmPurchaseRefundPo record);



	/**
	 * 
	 * <method description>根据每个条件AND精确查询列表
	 * 
	 * @author zhoubaiyun
	 * @param PmPurchaseRefundPo
	 * @return
	 */
	List<PmPurchaseRefundPo> selectSelective(PmPurchaseRefundPo PmPurchaseRefundPo);



	/**
	 * 
	 * <method description>根据条件查询退货列表
	 * 
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundExtPo
	 * @return
	 */
	List<PmPurchaseRefundPo> queryPmPurchaseRefund(PmPurchaseRefundExtPo pmPurchaseRefundExtPo);

	/**
	 *
	 * <method description>根据条件查询供应商退货列表
	 *
	 * @author leixin
	 * @param pmPurchaseRefundProviderExtPo
	 * @return
	 */
	List<PmPurchaseReturnProviderPo> queryPmPurchaseRefundProvider(PmPurchaseRefundProviderExtPo pmPurchaseRefundProviderExtPo);

	/**
	 *
	 * <method description>根据退货单ID获取关联采购单号
	 *
	 * @author leixin
	 * @param id
	 * @return
	 */
	List<Long> queryPmPurchaseRefundProviderId(Long id);
	/**
	 * 批量删除退货单
	 * 
	 * @Description:
	 * @author huangjianjun
	 * @date 2017年10月19日下午4:06:10
	 */
	int deleteByBatch(@Param("ids") Long[] ids);



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
	 * @Description: 供应商采购退货商品详情
	 * @author leixin
	 * @param id
	 */
	List<PmPurchaseRefundProviderItemPo> selectRefundDetailProviderItemById(Long id);


	/**
	 * @Description:查询退货单号组是否包含有非制单状态的退货单
	 * @author huangjianjun
	 * @date 2017年10月19日下午5:27:01
	 */
	int queryContainUndraftRefundOrder(@Param("purchaseRefundIds") Long[] purchaseRefundIds);



	/**
	 * 
	 * <method description>根据审批列表条件查询审批列表
	 * 
	 * @author zhoubaiyun
	 * @param pmPurchaseRefundAuditQueryPo
	 * @return
	 */
	List<PmPurchaseRefundWithProcessNodeNamePo> queryPurchaseRefundAuditList(
			PmPurchaseRefundAuditQueryPo pmPurchaseRefundAuditQueryPo);



	/**
	 * 结束审批流程
	 * 
	 * @param id
	 * @return
	 */
	int endApproveProcess(@Param("id")Long id,@Param("auditUserId")String auditUserId,@Param("status")Integer status);



	/**
	 * 
	 * <method description>根据采购退货单编码查询采购退货单
	 * 
	 * @author zhoubaiyun
	 * @param entryOrderCode
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
	int updatePurchaseRefundStatusByPurchaseRefundNo(@Param("purchaseRefundNo") String purchaseRefundNo,
			@Param("status") Integer status);

	/**
	 * @Description: 根据状态查询退货单的记录数
	 * @author tankejia
	 * @date 2017/12/27- 15:07
	 * @param
	 */
	long getAllPurchaseRefundCount(Integer status);
}