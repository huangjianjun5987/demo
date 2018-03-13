package com.yatang.sc.facade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.WarehouseLogicInfoDao;
import com.yatang.sc.facade.domain.WarehouseLogicInfoPo;
import com.yatang.sc.facade.service.WarehouseLogicService;

@Service
@Transactional
public class WarehouseLogicServiceImpl implements WarehouseLogicService {
	@Autowired
	private WarehouseLogicInfoDao warehouseLogicInfoPoDao;



	@Override
	public PageInfo<WarehouseLogicInfoPo> getWarehouseByOrCondition(WarehouseLogicInfoPo warehouseLogicInfoPo,
                                                                    Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<WarehouseLogicInfoPo> list = warehouseLogicInfoPoDao.selectByOrCondition(warehouseLogicInfoPo);
		return new PageInfo<>(list);
	}



	@Override
	public WarehouseLogicInfoPo selectWarehouseLogic(Integer warehouseLogicId) {
		return warehouseLogicInfoPoDao.selectByPrimaryKey(warehouseLogicId);
	}



	@Override
	public WarehouseLogicInfoPo selectByWarehouseLogicCode(String warehouseLogicCode) {
		return warehouseLogicInfoPoDao.selectByWarehouseLogicCode(warehouseLogicCode);
	}



	@Override
	public List<WarehouseLogicInfoPo> queryWarehouseLogicList() {
		return warehouseLogicInfoPoDao.queryWarehouseLogicList();
	}



	@Override
	public List<WarehouseLogicInfoPo> getWarehouseByProviceName(String proviceName) {
		return warehouseLogicInfoPoDao.getWarehouseByProviceName(proviceName);
	}

	@Override
	public List<WarehouseLogicInfoPo> getWarehouseByBranchCompanyId(String companyId) {

		return warehouseLogicInfoPoDao.getWarehouseByBranchCompanyId(companyId);
	}
}
