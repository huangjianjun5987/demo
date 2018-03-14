package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.ProviderShippingGroup;

public interface ProviderShippingGroupDao {
    int deleteByPrimaryKey(String id);

    int insert(ProviderShippingGroup record);

    int insertSelective(ProviderShippingGroup record);

    ProviderShippingGroup selectByPrimaryKey(String id);

    ProviderShippingGroup selectByShippingGroupId(String shippingGroupId);

    int updateByPrimaryKeySelective(ProviderShippingGroup record);

    int updateByPrimaryKey(ProviderShippingGroup record);
}
