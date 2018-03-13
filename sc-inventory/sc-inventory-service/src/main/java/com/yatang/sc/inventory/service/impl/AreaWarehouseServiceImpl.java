package com.yatang.sc.inventory.service.impl;

import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.inventory.dao.CfAreaWarehouseDao;
import com.yatang.sc.inventory.domain.CfAreaWarehouse;
import com.yatang.sc.inventory.service.AreaWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("areaWarehouseService")
public class AreaWarehouseServiceImpl implements AreaWarehouseService {

    @Autowired
    CfAreaWarehouseDao cfAreaWarehouseDao;

    @Override
    @LocalCache(group = "areaWarehouse", maximumSize = 1000,expireAfterWrite = 20)
    public CfAreaWarehouse queryAreawarehouseByProvince(String province) {
        return cfAreaWarehouseDao.queryAreawarehouseByProvince(province);
    }
}
