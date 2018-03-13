package com.yatang.sc.facade.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.PmPurchaseQueryParamPo;
import com.yatang.sc.facade.domain.PmSupplierPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmSupplierPurchaseOrderQueryParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderInfoPo;

public interface PmPurchaseOrderDao {
	int deleteByPrimaryKey(Long id);



	int insert(PmPurchaseOrderPo record);



	int insertSelective(PmPurchaseOrderPo record);



	PmPurchaseOrderPo selectByPrimaryKey(Long id);



	int updateByPrimaryKeySelective(PmPurchaseOrderPo record);



	int updateByPrimaryKey(PmPurchaseOrderPo record);



	/**
	 * 根据传入参数查询采购单打印管理列表详细信息
	 * 
	 * @param convert
	 * @return
	 */
	List<PmPurchaseOrderInfoPo> queryPurchaseOrderListInfo(PmPurchaseQueryParamPo convert);



	/**
	 * 根据传入参数查询采购订单管理列表
	 * 
	 * @param convert
	 * @return
	 */
	List<PmPurchaseOrderPo> queryPurchaseOrderList(PmPurchaseQueryParamPo convert);



	/**
	 * 根据采购单id查询采购单详情
	 * 
	 * @param id
	 * @return
	 */
	PmPurchaseOrderInfoPo getPurchaseOrderInfoById(Long id);



	/**
	 *
	 * <method description>根据ID列表批量更新
	 * 
	 * @author zhoubaiyun
	 * @param updateListId
	 * @param modifyTime-必须
	 * @param modifyUserId-非必须（系统关闭）
	 * @return
	 */
	int closePurchaseListByPrimaryKey(@Param("updateListId") List<Long> updateListId,
			@Param("modifyTime") Date modifyTime, @Param("modifyUserId") String modifyUserId);



	/**
	 * 
	 * <method description>根据ID单个更新
	 * 
	 * @author zhoubaiyun
	 * @param id
	 * @param modifyTime-必须
	 * @param modifyUserId-非必须（系统关闭）
	 * @return
	 */
	int closePurchaseByPrimaryKey(@Param("id") Long id, @Param("modifyTime") Date modifyTime,
			@Param("modifyUserId") String modifyUserId,@Param("failedReason") String failedReason);



	/**
	 * 批量删除(草稿状态的商品,物理删除 草稿状态)
	 * 
	 * @param pmPurchaseOrderIdList
	 * @return
	 */
	int batchDeletePmPurchaseOrderByIds(List<String> pmPurchaseOrderIdList);



	int deletePmPurchaseOrderById(Long orderById);



	/**
	 * 更新条形码url
	 * 
	 * @param mainId
	 *            主键id
	 * @param barCodeUrl
	 *            条码url
	 * @author yangshuang
	 * @return
	 */
	int updateBarCodeUrlById(@Param("id") Long mainId, @Param("barCodeUrl") String barCodeUrl);



	/**
	 * 根据采购订单编号查询采购订单详情
	 * 
	 * @param purchaseOrderNo
	 * @return
	 */
	PmPurchaseOrderInfoPo getPurchaseOrderInfoByOrderNo(String purchaseOrderNo);



	/**
	 * 
	 * <method description>根据采购收货单编码查询采购订单
	 * 
	 * @author zhoubaiyun
	 * @param purchaseReceiptNo
	 * @return
	 */
	PmPurchaseOrderPo selectPmPurchaseOrderByPurchaseReceiptNo(String purchaseReceiptNo);



	Double selectPurchasePriceByPurchaseReceiptItemId(Long id);



	/**
	 * 
	 * <method description>查询需要关闭的订单
	 *
	 * @param status
	 * @param timeout
	 * @return
	 */
	List<PmPurchaseOrderPo> selectCloseablePurchaseOrder(@Param("status") int status, @Param("timeout") int timeout);

	/**
	 * 查询统采 至 当前子公司 的采购单列表
	 * @author liuxiaokun
	 * @param pmPurchaseQueryParamPo
	 * @return
	 */
    List<PmPurchaseOrderPo> queryGeneralPurchaseOrders(PmPurchaseQueryParamPo pmPurchaseQueryParamPo);

	/**
	 * 查询统采 至 当前子公司 的采购单打印列表
	 * @author liuxiaokun
	 * @param pmPurchaseQueryParamPo
	 * @return
	 */
	List<PmPurchaseOrderInfoPo> queryGeneralPurchasePrintOrders(PmPurchaseQueryParamPo pmPurchaseQueryParamPo);



	/**
	 * @Description: 根据单号更新采购单状态
	 * @author huangjianjun
	 * @date 2017年12月11日下午4:12:11
	 * @param orderNo
	 * @param status
	 */
	int updateStatus(@Param("orderNo")String orderNo, @Param("status")Integer status);


	/**
	 * @Description: 根据条件分页查询供应商采购单
	 * @author kangdong
	 * @date 2018年1月10日上午9:11:01
	 * @param convert
	 * @return
	 */
    List<PmSupplierPurchaseOrderItemPo> querySupplierPurchaseOrderList(PmSupplierPurchaseOrderQueryParamPo convert);



	/**
	 * 
	 * <method description>根据销售订单ID查询采购订单
	 * 
	 * @author zhoubaiyun
	 * @param saleOrderId
	 * @return
	 */
	PmPurchaseOrderPo selectBySaleOrderId(String saleOrderId);
}