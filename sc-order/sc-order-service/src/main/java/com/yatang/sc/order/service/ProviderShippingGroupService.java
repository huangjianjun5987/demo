package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.ProviderShippingGroup;

public interface ProviderShippingGroupService {
    int deleteByPrimaryKey(String id);

    int insert(ProviderShippingGroup record);

    int insertSelective(ProviderShippingGroup record);

    ProviderShippingGroup selectByPrimaryKey(String id);

    ProviderShippingGroup selectByShippingGroupId(String shippingGroupId);

    int updateByPrimaryKeySelective(ProviderShippingGroup record);

    int updateByPrimaryKey(ProviderShippingGroup record);
}
