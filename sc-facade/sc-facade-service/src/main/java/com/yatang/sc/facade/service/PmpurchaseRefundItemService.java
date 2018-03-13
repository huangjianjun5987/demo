package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.pm.PmPurchaseRefundItemPo;

import java.util.List;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月11日
 */
public interface PmpurchaseRefundItemService {
	int updateByPrimaryKey(PmPurchaseRefundItemPo record);

	/**
	 * 通过主键id查询
	 * @param refundItemId
	 * @return
	 */
	PmPurchaseRefundItemPo selectByPrimaryKey(Long refundItemId);

	/**
	 * 批量更新
	 * @param pmPurchaseRefundItemPos
	 */
	void batchUpdate(List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos);

	/**
	 * @Description: 根据采购退货单id查询item集合
	 * @author tankejia
	 * @date 2017/12/28- 11:18
	 * @param refundId
	 */
	List<PmPurchaseRefundItemPo> selectRefundItemsByRefundId(Long refundId);
}
