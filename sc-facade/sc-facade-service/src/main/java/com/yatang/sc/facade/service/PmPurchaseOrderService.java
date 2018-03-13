package com.yatang.sc.facade.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.PmPurchaseQueryParamPo;
import com.yatang.sc.facade.domain.PmSupplierPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmSupplierPurchaseOrderQueryParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderExtPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderItemQueryParamPo;

/**
 * @描述: 商品采购单service接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:31
 * @版本: v1.0
 */
public interface PmPurchaseOrderService {
	/**
	 *
	 * <method description>更新，需要主键
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(PmPurchaseOrderPo record);



	/**
	 *
	 * <method description>根据主键查询
	 *
	 * @param id
	 * @return
	 */
	PmPurchaseOrderPo selectByPrimaryKey(Long id);



	/**
	 * 新增商品采购订单
	 *
	 * @param pmPurchaseOrderExtPo
	 * @return
	 */
	Long addPmPurchaseOrder(PmPurchaseOrderExtPo pmPurchaseOrderExtPo);



	/**
	 * 批量删除(草稿状态的商品,物理删除 草稿状态)
	 *
	 * @param pmPurchaseOrderIdList
	 *            订单集合
	 * @return
	 */
	boolean batchDeletePmPurchaseOrderByIds(List<String> pmPurchaseOrderIdList);



	/**
	 * 更新商品采购订单
	 *
	 * @param pmPurchaseOrderExtPo
	 * @return
	 */
	boolean updatePmPurchaseOrder(PmPurchaseOrderExtPo pmPurchaseOrderExtPo);



	/**
	 * 根据传入参数查询采购单打印管理列表详细信息
	 *
	 * @param convert
	 * @return
	 */
	PageInfo<PmPurchaseOrderInfoPo> queryPurchaseOrderListInfo(PmPurchaseQueryParamPo convert);



	/**
	 * 根据传入参数查询采购单管理列表
	 *
	 * @param convert
	 * @return
	 */
	PageInfo<PmPurchaseOrderPo> queryPurchaseOrderList(PmPurchaseQueryParamPo convert);



	/**
	 *
	 * <method description>根据条件查询所有
	 *
	 * @param pmPurchaseOrderPo
	 * @return
	 */
	List<PmPurchaseOrderPo> selectListByCondition(PmPurchaseQueryParamPo pmPurchaseOrderPo);



	/**
	 * 根据采购单id查询采购单详情
	 *
	 * @param id
	 * @return
	 */
	PmPurchaseOrderInfoPo getPurchaseOrderInfoById(Long id);



	/**
	 * 审核采购单列表
	 *
	 * @param convert1
	 * @return
	 */
	boolean auditPurchaseOrderInfo(PmPurchaseOrderPo convert1);



	/**
	 *
	 * <method description>批量关闭采购单，关闭时间为当前时间，关闭原因为超期关闭
	 *
	 * @author zhoubaiyun
	 * @param ids
	 * @param modifyUserId-非必须（系统关闭）
	 * @return
	 */
	int closePurchaseListByPrimaryKey(List<Long> ids, String modifyUserId);



	/**
	 *
	 * <method description>关闭采购单，关闭时间为当前时间，关闭原因为超期关闭
	 *
	 * @author zhoubaiyun
	 * @param id
	 * @param modifyUserId-非必须（系统关闭）
	 * @return
	 */
	int closePurchaseByPrimaryKey(Long id, String modifyUserId);



	/**
	 * 批量删除(草稿状态的商品,物理删除 草稿状态)
	 *
	 * @param orderId
	 * @return
	 */
	boolean deletePmPurchaseOrderById(Long orderId);



	/**
	 * 
	 * 根据采购单明细表的id查询item（收货单需要使用）
	 *
	 * @param id
	 * @return
	 */
	PmPurchaseOrderItemPo selectBypurchaseOrderIdAndPrimaryKey(Long purchaseOrderId, Long id,String itemCode,long receivedNum);



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
	boolean updateBarCodeUrlById(Long mainId, String barCodeUrl);



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



	Double selectPurchasePriceByPurchaseReceiptItemId(Long orderLineNo);



	PmPurchaseOrderItemPo selectPmPurchaseOrderItemByPurchaseReceiptItemId(Long id);



	List<PmPurchaseOrderPo> selectCloseablePurchaseOrder(int status, int timeout);



	/**
	 * 根据采购单号和品牌名称查询品牌值清单
	 * 
	 * @param pmPurchaseOrderItemQueryParamPo
	 * @author yinyuxin
	 * @return
	 */
	PageInfo<PmPurchaseOrderItemPo> selectBrandsByOrderNo(
			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo);



	/**
	 * 根据采购单号和商品编号或者条码或者名称查询商品清单
	 * 
	 * @author yinyuxin
	 * @param pmPurchaseOrderItemQueryParamPo
	 * @return
	 */
	PageInfo<PmPurchaseOrderItemPo> selectProductByOrderNo(
			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo);



	/**
	 * 根据采购单号、商品code、品牌id查询采购商品清单
	 * 
	 * @author yinyuxin
	 * @param pmPurchaseOrderItemQueryParamPo
	 * @return
	 */
	List<PmPurchaseOrderItemPo> addRefundProducts(PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo);

	PmPurchaseOrderItemPo queryItemById(Long valueOf);
	
	/**
	 * @Description: 根据单号更新采购单状态
	 * @author huangjianjun
	 * @date 2017年12月11日下午4:11:01
	 * @param orderNo
	 * @param status
	 */
	Boolean updateStatus(String orderNo,Integer status);


	/**
	 * @Description: 根据条件查询供应商采购单
	 * @author kangdong
	 * @date 2018年1月10日上午9:11:01
	 * @param supplierPurchaseOrderQueryParamPo
	 * @return
	 */
	PageInfo<PmSupplierPurchaseOrderItemPo> querySupplierPurchaseOrderList(PmSupplierPurchaseOrderQueryParamPo supplierPurchaseOrderQueryParamPo);



	PmPurchaseOrderItemPo selectByPurchaseOrderIdAndProductCode(Long purchaseOrderId, String itemCode);



	/**
	 * 
	 * <method description>根据销售订单ID查询采购订单
	 * 
	 * @author zhoubaiyun
	 * @param parseLong
	 * @return
	 */
	PmPurchaseOrderPo selectBySaleOrderId(String saleOrderId);

	/**
	 * 根据采购单id关闭采购单（直送关闭）
	 * @author lvheping
	 * @param purchaseOrderId
	 * @param userName
	 */
	 void closePmPurchaseOrder(Long purchaseOrderId,String userName);
}
