package com.yatang.sc.facade.service;

import java.util.List;

import com.yatang.sc.facade.domain.WarehousePhysicalInfoPo;

/**
 * 
 * <class description>物理仓库Service接口
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月28日
 */
public interface WarehousePhysicalService {

	WarehousePhysicalInfoPo getWarehousePhysicalByWarehouseLogicId(Integer warehouseLogicId);



	/**
	 * 
	 * <method description>根据省份名称精确查询物理仓库
	 *
	 * @param proviceName
	 * @return
	 */
	List<WarehousePhysicalInfoPo> getWarehouseByProviceName(String proviceName);
}
