package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmPurchaseReceiptDao {
	int deleteByPrimaryKey(Long id);



	int insert(PmPurchaseReceiptPo record);



	int insertSelective(PmPurchaseReceiptPo record);



	PmPurchaseReceiptPo selectByPrimaryKey(Long id);



	int updateByPrimaryKeySelective(PmPurchaseReceiptPo record);



	int updateByPrimaryKey(PmPurchaseReceiptPo record);



	/**
	 * 
	 * 根据原型图上的查询条件查询列表
	 *
	 * @param paramPo
	 * @return
	 */
	List<PmPurchaseReceiptPo> queryListByParam(PmPurchaseReceiptParamPo paramPo);



	/**
	 * 
	 * <method description>根据收货单号更新
	 * 
	 * @author zhoubaiyun
	 * @param purchaseReceiptPo(purchaseReceiptNo字段必须有)
	 * @return
	 */
	int updateByPurchaseReceiptNoSelective(PmPurchaseReceiptPo purchaseReceiptPo);



	/**
	 * 
	 * <method description>根据采购订单ID查询采购收货单
	 * 
	 * @author zhoubaiyun
	 * @param purchaseOrderId
	 * @return
	 */
	PmPurchaseReceiptPo selectByPurchaseOrderId(Long purchaseOrderId);

	/**
	 * @Description: 根据状态查询收货单的记录数
	 * @author tankejia
	 * @date 2017/12/27- 15:07
	 * @param
	 */
	long getAllPurchaseReceiptCount(Integer status);

	/**
	 * @Description: 根据收货单号查询收货单
	 * @author tankejia
	 * @date 2017/12/28- 12:56
	 * @param purchaseReceiptNo
	 */
	PmPurchaseReceiptPo selectByPurchaseReceiptNo(String purchaseReceiptNo);


	/**
	 * 根据采购单id查询录入过asn的收货单list
	 * @param purchaseOrderId 采购单id
	 * @param  containAsn true:必须有asn,false:(不强制要求需要asn)
	 * @author yangshuang
	 * @return List<PmPurchaseReceiptPo>
	 */
    List<PmPurchaseReceiptPo> queryPurchaseReceiptListByPurchaseOrderId(@Param(value = "purchaseOrderId") String purchaseOrderId, @Param(value = "containAsn")Boolean containAsn);

	/**
	 * 根据采购单id查询是否有未完成的收货单
	 * @param purchaseOrderId
	 *  @author lvheping
	 * @return
	 */
	int selectIsUnfinishedByPurchaseOrderId(String purchaseOrderId);


	/**
	 * 根据采购单id查询未录入过asn的收货单list
	 * @param purchaseOrderId 采购单id
	 * asn为空或status=0
	 * @author kangdong
	 * @return List<PmPurchaseReceiptPo>
	 */
    List<PmPurchaseReceiptPo> queryPurchaseReceiptListByOrderIdAndStatus(String purchaseOrderId);
}