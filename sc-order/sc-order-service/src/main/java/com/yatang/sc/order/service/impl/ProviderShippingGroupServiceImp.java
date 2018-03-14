package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ProviderShippingGroupDao;
import com.yatang.sc.order.domain.ProviderShippingGroup;
import com.yatang.sc.order.service.ProviderShippingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProviderShippingGroupServiceImp implements ProviderShippingGroupService {
    @Autowired
    private ProviderShippingGroupDao mProviderShippingGroupDao;

    @Override
    public int deleteByPrimaryKey(String id) {
        return mProviderShippingGroupDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ProviderShippingGroup record) {
        return mProviderShippingGroupDao.insert(record);
    }

    @Override
    public int insertSelective(ProviderShippingGroup record) {
        return mProviderShippingGroupDao.insertSelective(record);
    }

    @Override
    public ProviderShippingGroup selectByPrimaryKey(String id) {
        return mProviderShippingGroupDao.selectByPrimaryKey(id);
    }

    @Override
    public ProviderShippingGroup selectByShippingGroupId(String shippingGroupId) {
        return mProviderShippingGroupDao.selectByShippingGroupId(shippingGroupId);
    }

    @Override
    public int updateByPrimaryKeySelective(ProviderShippingGroup record) {
        return mProviderShippingGroupDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProviderShippingGroup record) {
        return mProviderShippingGroupDao.updateByPrimaryKey(record);
    }
}
