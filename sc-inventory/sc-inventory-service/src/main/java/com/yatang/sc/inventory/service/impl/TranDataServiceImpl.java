package com.yatang.sc.inventory.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatang.sc.inventory.dao.TranDataDao;
import com.yatang.sc.inventory.domain.TranData;
import com.yatang.sc.inventory.service.TranDataService;

/**
 * Created by xiangyonghong on 2017/7/27.
 */
@Service
public class TranDataServiceImpl implements TranDataService {

    @Autowired
    private TranDataDao tranDataDao;

    @Override
    public void save(TranData tranData) {
        tranDataDao.insert(tranData);
    }

    @Override
    public void update(TranData tranData) {
        tranDataDao.updateByPrimaryKey(tranData);
    }

    @Override
    public Long getTranDataUnitsByProductIdAndDate(String productId, String warehouseCode, String code, Date startDate) {
        return tranDataDao.getTranDataUnitsByProductIdAndDate(productId,warehouseCode,code, startDate);
    }

	@Override
	public void saveList(List<TranData> tranDataList) {
		for (TranData tranData : tranDataList) {
			tranDataDao.insert(tranData);
		}
		
	}
}
