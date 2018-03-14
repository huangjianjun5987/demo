package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.ShippingGroup;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/10 20:32
 * @版本:v1.0
 */
public interface ShippingGroupService {

    int deleteByPrimaryKey(String id);

    int insertSelective(ShippingGroup record);

    ShippingGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ShippingGroup record);

    void save(ShippingGroup shippingGroup);

    void update(ShippingGroup shippingGroup);
}
