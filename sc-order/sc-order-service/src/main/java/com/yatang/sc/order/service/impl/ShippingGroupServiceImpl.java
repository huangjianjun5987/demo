package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ShippingGroupDao;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.service.ShippingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/10 20:32
 * @版本:v1.0
 */
@Service
public class ShippingGroupServiceImpl implements ShippingGroupService {

    @Autowired
    private ShippingGroupDao shippingGroupDao;

    @Override
    public int deleteByPrimaryKey(String id) {
        return shippingGroupDao.deleteByPrimaryKey( id );
    }



    @Override
    public int insertSelective(ShippingGroup record) {
        return shippingGroupDao.insertSelective( record );
    }

    @Override
    public ShippingGroup selectByPrimaryKey(String id) {
        return shippingGroupDao.selectByPrimaryKey( id );
    }

    @Override
    public int updateByPrimaryKeySelective(ShippingGroup record) {
        return shippingGroupDao.updateByPrimaryKeySelective( record );
    }

    @Override
    public void save(ShippingGroup shippingGroup) {
        shippingGroupDao.insert(shippingGroup);
    }

    @Override
    public void update(ShippingGroup shippingGroup){

        shippingGroupDao.updateByPrimaryKey(shippingGroup);
    }
}
