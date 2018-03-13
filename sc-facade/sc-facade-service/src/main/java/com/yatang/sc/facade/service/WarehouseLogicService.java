package com.yatang.sc.facade.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.WarehouseLogicInfoPo;

/**
 * 
 * <class description>逻辑仓库Service接口
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月28日
 */
public interface WarehouseLogicService {

	PageInfo<WarehouseLogicInfoPo> getWarehouseByOrCondition(WarehouseLogicInfoPo warehouseLogicInfoPo, Integer pageNum,
                                                             Integer pageSize);



	WarehouseLogicInfoPo selectWarehouseLogic(Integer warehouseLogicId);



	/**
	 * 
	 * <method description>根据Code查询
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
	 * <method description>根据省份名称精确查询逻辑仓库
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
