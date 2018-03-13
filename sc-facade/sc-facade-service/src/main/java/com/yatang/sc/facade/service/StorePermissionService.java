package com.yatang.sc.facade.service;

import java.util.List;
import com.yatang.sc.facade.domain.QueryStorePermissionPo;
import com.yatang.sc.facade.domain.StorePermissionPo;

public interface StorePermissionService {

    int deleteByPrimaryKey(Integer id);

    boolean insert(StorePermissionPo record);

    boolean insertSelective(StorePermissionPo record);

    StorePermissionPo selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(StorePermissionPo record);

    boolean updateByPrimaryKey(StorePermissionPo record);

    StorePermissionPo queryStorePermissionByStoreId(String storeId);

    List<StorePermissionPo> queryStorePermissionPage(QueryStorePermissionPo queryStorePermissionPo);

    Long queryStorePermissionTotal(QueryStorePermissionPo queryStorePermissionPo);

    StorePermissionPo queryStorePermissionByStoreIdAndId(StorePermissionPo storePermissionPo);
}
