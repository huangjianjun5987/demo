package com.yatang.sc.facade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderItemQueryParamPo;

public interface PmPurchaseOrderItemDao {
	int deleteByPrimaryKey(Long id);



	int insert(PmPurchaseOrderItemPo record);



	int insertSelective(PmPurchaseOrderItemPo record);



	PmPurchaseOrderItemPo selectByPrimaryKey(Long id);



	int updateByPrimaryKeySelective(PmPurchaseOrderItemPo record);



	int updateByPrimaryKey(PmPurchaseOrderItemPo record);



	/**
	 * 批量插入订单item
	 * 
	 * @param pmPurchaseOrderItems
	 * @return
	 */
	int batchInsertPmPurchaseOrderItem(List<PmPurchaseOrderItemPo> pmPurchaseOrderItems);



	/**
	 * 批量删除(草稿状态的商品子项,物理删除 草稿状态)
	 * 
	 * @param pmPurchaseOrderIdList
	 *            id集合
	 * @return
	 */
	int batchDeletePmPurchaseOrderItemsByIds(List<String> pmPurchaseOrderIdList);



	/**
	 * 根据订单id获取子订单的id的set集合
	 * 
	 * @param orderId
	 *            订单id
	 * @return
	 */
	List<Long> getItemsSetByOrderId(Long orderId);



	/**
	 * 根据子项批量删除(物理删除 草稿状态)
	 * 
	 * @param itemIds
	 * @return
	 */
	int batchDeletePmPurchaseOrderItems(List<Long> itemIds);



	/**
	 * 批量更新
	 * 
	 * @param updatePmPurchaseOrderItems
	 * @return
	 */
	int batchUpdatePmPurchaseOrderItems(List<PmPurchaseOrderItemPo> updatePmPurchaseOrderItems);



	int deletePmPurchaseOrderItemById(String orderId);



	PmPurchaseOrderItemPo selectPmPurchaseOrderItemByPurchaseReceiptItemId(Long id);



	/**
	 * 批量查询子表信息（子查询）
	 * 
	 * @param id
	 * @return
	 */
	List<PmPurchaseOrderItemPo> selectItemsInfo(Long id);



	/**
	 * 根据采购单号和品牌名称查询品牌值清单
	 * 
	 * @param pmPurchaseOrderItemQueryParamPo
	 * @author yinyuxin
	 * @return
	 */
	List<PmPurchaseOrderItemPo> selectBrandsByOrderNo(PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo);



	/**
	 * 根据采购单号和商品编号或者条码或者名称查询商品清单
	 * 
	 * @author yinyuxin
	 * @param pmPurchaseOrderItemQueryParamPo
	 * @return
	 */
	List<PmPurchaseOrderItemPo> selectProductByOrderNo(PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo);



	/**
	 * 根据采购单号、商品code、品牌id查询采购商品清单
	 * 
	 * @author yinyuxin
	 * @param pmPurchaseOrderItemQueryParamPo
	 * @return
	 */
	List<PmPurchaseOrderItemPo> addRefundProducts(PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo);



	/**
	 * 
	 * <method description>
	 *
	 * @param purchaseOrderId
	 * @param id
	 * @return
	 */
	PmPurchaseOrderItemPo selectBypurchaseOrderIdAndPrimaryKey(@Param("purchaseOrderId") Long purchaseOrderId,
			@Param("id") Long id,@Param("itemCode")String itemCode,@Param("receivedNum")long receivedNum);



	PmPurchaseOrderItemPo selectByPurchaseOrderIdAndProductCode(@Param("purchaseOrderId")Long purchaseOrderId, @Param("itemCode")String itemCode);
}