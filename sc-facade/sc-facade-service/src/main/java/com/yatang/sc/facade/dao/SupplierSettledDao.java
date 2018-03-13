package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SupplierSettledPo;

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
	 * @param supplierSettledPo
	 * @return
	 */
	int insert(SupplierSettledPo supplierSettledPo);



	/**
	 * 
	 * <method description>插入操作基础类，不可修改
	 *
	 * @param supplierSettledPo
	 * @return
	 */
	int insertSelective(SupplierSettledPo supplierSettledPo);
}