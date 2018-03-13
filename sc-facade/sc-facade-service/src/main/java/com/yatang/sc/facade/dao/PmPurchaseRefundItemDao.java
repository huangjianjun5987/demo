package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.pm.PmPurchaseRefundItemPo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PmPurchaseRefundItemDao {
	int deleteByPrimaryKey(Long id);
	
	/**
	 * 根据退货单删除退货商品信息
	 * @Description: 
	 * @author huangjianjun
	 * @date 2017年10月19日下午4:42:30
	 */
	int deleteByPurchaseRefundId(Long purchaseRefundId);

	int insert(PmPurchaseRefundItemPo record);

	int insertSelective(PmPurchaseRefundItemPo record);

	PmPurchaseRefundItemPo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(PmPurchaseRefundItemPo record);

	int updateByPrimaryKey(PmPurchaseRefundItemPo record);

	/**
	 * 根据采购退货单id查询清单id集合
	 *
	 * @param refundId
	 * @return
	 */
	List<Long> selectIdsByRefundId(long refundId);

	/**
	 * 根据id集合批量失效清单
	 *
	 * @param ids
	 * @return
	 */
	int invaliBatchByIds(Map<String,Object> params);

	List<PmPurchaseRefundItemPo> selectByRefundId(Long refundId);

	/**
	 * @Description: 批量删除退货单商品信息
	 * @author huangjianjun
	 * @date 2017年10月24日下午3:13:15
	 */
	int deleteBatchByPurchaseRefundId(@Param("purchaseRefundIds")Long[] purchaseRefundIds);
}