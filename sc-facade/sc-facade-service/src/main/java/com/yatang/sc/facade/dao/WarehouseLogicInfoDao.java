package com.yatang.sc.facade.dao;

import java.util.List;

import com.yatang.sc.facade.domain.WarehouseInfoPo;
import com.yatang.sc.facade.domain.WarehouseLogicInfoPo;

public interface WarehouseLogicInfoDao {
	int deleteByPrimaryKey(Integer id);



	int insert(WarehouseLogicInfoPo record);



	int insertSelective(WarehouseLogicInfoPo record);



	WarehouseLogicInfoPo selectByPrimaryKey(Integer id);



	WarehouseInfoPo selectWarehouseByPrimaryKey(Integer id);



	int updateByPrimaryKeySelective(WarehouseLogicInfoPo record);



	int updateByPrimaryKey(WarehouseLogicInfoPo record);



	/**
	 * 
	 * <method description>根据OR条件查询逻辑仓库
	 *
	 * @return
	 */
	List<WarehouseLogicInfoPo> selectByOrCondition(WarehouseLogicInfoPo warehouseLogicInfoPo);



	/**
	 * 根据逻辑仓库code查询物理仓库code
	 * 
	 * @param warehouseLogicCode
	 * @return
	 */
	WarehouseLogicInfoPo selectByWarehouseLogicCode(String warehouseLogicCode);



	/**
	 * 查询所有逻辑仓列表
	 * 
	 * @return
	 */
	List<WarehouseLogicInfoPo> queryWarehouseLogicList();



	/**
	 * 
	 * <method description>根据省份名称精确查询所有逻辑仓
	 *
	 * @param proviceName
	 * @return
	 */
	List<WarehouseLogicInfoPo> getWarehouseByProviceName(String proviceName);

	/**
	 * 根据分公司查询逻辑仓
	 * @param companyId
	 * @return
	 */
    List<WarehouseLogicInfoPo> getWarehouseByBranchCompanyId(String companyId);
}