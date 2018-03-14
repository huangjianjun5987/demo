package com.yatang.sc.settlement.dao;

import com.yatang.sc.settlement.domain.SupplierSettledPo;
import com.yatang.sc.settlement.domain.SupplierSettlementMultQueryPo;

import java.util.List;

/**
 * 
 * <class description>供应商结算表基础Dao
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年8月31日
 */
public interface SupplierSettledDao {
	/**
	 * 
	 * <method description>插入操作基础类，不可修改
	 *
	 * @param record
	 * @return
	 */
	int insert(SupplierSettledPo supplierSettledPo);



	/**
	 * 
	 * <method description>插入操作基础类，不可修改
	 *
	 * @param record
	 * @return
	 */
	int insertSelective(SupplierSettledPo supplierSettledPo);



	/**
	 * @Description: 根据条件查询供应商结算列表
	 * @author tankejia
	 * @date 2017/8/31- 11:13
	 * @param record
	 */
	List<SupplierSettledPo> listSupplierSettlementByMultParam(SupplierSettlementMultQueryPo record);
}