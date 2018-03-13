package com.yatang.sc.facade.dao;

import java.util.List;

import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;

public interface PmPurchaseReceiptItemsDao {
	int deleteByPrimaryKey(Long id);



	int insert(PmPurchaseReceiptItemsPo record);



	int insertSelective(PmPurchaseReceiptItemsPo record);



	PmPurchaseReceiptItemsPo selectByPrimaryKey(Long id);



	int updateByPrimaryKeySelective(PmPurchaseReceiptItemsPo record);



	int updateByPrimaryKey(PmPurchaseReceiptItemsPo record);



	/**
	 * 
	 * 根据收货单id查询详单
	 *
	 * @param purchaseReceiptId
	 * @return
	 */
	List<PmPurchaseReceiptItemsPo> queryItemsByReceiptId(String purchaseReceiptId);



	int updateByPurchaseOrderItemsId(PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo);





	PmPurchaseReceiptItemsPo selectByPurchaseOrderItemsId(String purchaseOrderItemsId);

}