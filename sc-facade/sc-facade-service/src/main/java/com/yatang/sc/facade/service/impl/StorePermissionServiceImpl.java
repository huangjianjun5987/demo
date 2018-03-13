package com.yatang.sc.facade.service.impl;

import java.util.List;
import com.yatang.sc.facade.dao.StorePermissionDao;
import com.yatang.sc.facade.domain.StorePermissionPo;
import com.yatang.sc.facade.domain.QueryStorePermissionPo;
import com.yatang.sc.facade.service.StorePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorePermissionServiceImpl implements StorePermissionService {
    @Autowired
    private StorePermissionDao storePermissionDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return storePermissionDao.deleteByPrimaryKey(id);
    }

    @Override
    public boolean insert(StorePermissionPo record) {
        return storePermissionDao.insert(record) > 0 ;
    }

    @Override
    public boolean insertSelective(StorePermissionPo record) {
        return storePermissionDao.insertSelective(record)>0;
    }

    @Override
    public StorePermissionPo selectByPrimaryKey(Integer id) {
        return storePermissionDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(StorePermissionPo record) {
        return storePermissionDao.updateByPrimaryKeySelective(record)>0;
    }

    @Override
    public boolean updateByPrimaryKey(StorePermissionPo record) {
        return storePermissionDao.updateByPrimaryKey(record)>0;
    }

    @Override
    public StorePermissionPo queryStorePermissionByStoreId(String storeId) {
        return storePermissionDao.queryStorePermissionByStoreId(storeId);
    }

    @Override
    public List<StorePermissionPo> queryStorePermissionPage(QueryStorePermissionPo queryStorePermissionPo) {
        return storePermissionDao.queryStorePermissionPage(queryStorePermissionPo);
    }

    @Override
    public Long queryStorePermissionTotal(QueryStorePermissionPo queryStorePermissionPo) {
        return storePermissionDao.queryStorePermissionTotal(queryStorePermissionPo);
    }

    @Override
    public StorePermissionPo queryStorePermissionByStoreIdAndId(StorePermissionPo storePermissionPo) {
        return storePermissionDao.queryStorePermissionByStoreIdAndId(storePermissionPo);
    }
}
