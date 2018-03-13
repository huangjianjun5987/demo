package com.yatang.sc.facade.dao;

import java.util.List;
import com.yatang.sc.facade.domain.StorePermissionPo;
import com.yatang.sc.facade.domain.QueryStorePermissionPo;

public interface StorePermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(StorePermissionPo record);

    int insertSelective(StorePermissionPo record);

    StorePermissionPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StorePermissionPo record);

    int updateByPrimaryKey(StorePermissionPo record);

    StorePermissionPo queryStorePermissionByStoreId(String storeId);

    List<StorePermissionPo> queryStorePermissionPage(QueryStorePermissionPo queryStorePermissionPo);

    Long queryStorePermissionTotal(QueryStorePermissionPo queryStorePermissionPo);

    StorePermissionPo queryStorePermissionByStoreIdAndId(StorePermissionPo storePermissionPo);
}