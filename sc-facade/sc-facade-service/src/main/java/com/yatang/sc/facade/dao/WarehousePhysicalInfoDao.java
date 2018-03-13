package com.yatang.sc.facade.dao;

import java.util.List;

import com.yatang.sc.facade.domain.WarehousePhysicalInfoPo;

public interface WarehousePhysicalInfoDao {
	int deleteByPrimaryKey(Integer id);



	int insert(WarehousePhysicalInfoPo record);



	int insertSelective(WarehousePhysicalInfoPo record);



	WarehousePhysicalInfoPo selectByPrimaryKey(Integer id);



	int updateByPrimaryKeySelective(WarehousePhysicalInfoPo record);



	int updateByPrimaryKey(WarehousePhysicalInfoPo record);



	WarehousePhysicalInfoPo getWarehousePhysicalByWarehouseLogicId(Integer warehouseLogicId);



	List<WarehousePhysicalInfoPo> getWarehouseByProviceName(String proviceName);
}