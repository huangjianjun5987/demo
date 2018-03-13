package com.yatang.sc.facade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yatang.sc.facade.dao.WarehousePhysicalInfoDao;
import com.yatang.sc.facade.domain.WarehousePhysicalInfoPo;
import com.yatang.sc.facade.service.WarehousePhysicalService;

@Service
@Transactional
public class WarehousePhysicalServiceImpl implements WarehousePhysicalService {
	@Autowired
	private WarehousePhysicalInfoDao warehousePhysicalInfoDao;



	@Override
	public WarehousePhysicalInfoPo getWarehousePhysicalByWarehouseLogicId(Integer warehouseLogicId) {
		return warehousePhysicalInfoDao.getWarehousePhysicalByWarehouseLogicId(warehouseLogicId);
	}



	@Override
	public List<WarehousePhysicalInfoPo> getWarehouseByProviceName(String proviceName) {
		return warehousePhysicalInfoDao.getWarehouseByProviceName(proviceName);
	}

}
