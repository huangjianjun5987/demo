package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.SupplierSaleRegionPo;

public interface SupplierSaleRegionDao {

	int deleteByPrimaryKey(String id);


	int insertSelective(SupplierSaleRegionPo record);


	int updateByPrimaryKeySelective(SupplierSaleRegionPo record);


	SupplierSaleRegionPo selectByPrimaryKey(String id);

}