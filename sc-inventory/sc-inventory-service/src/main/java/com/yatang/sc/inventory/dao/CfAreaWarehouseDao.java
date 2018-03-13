package com.yatang.sc.inventory.dao;

import com.yatang.sc.inventory.domain.CfAreaWarehouse;


public interface CfAreaWarehouseDao {
    int deleteByPrimaryKey(Long id);

    int insert(CfAreaWarehouse record);

    int insertSelective(CfAreaWarehouse record);

    CfAreaWarehouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CfAreaWarehouse record);

    int updateByPrimaryKey(CfAreaWarehouse record);

    CfAreaWarehouse queryAreawarehouseByProvince(String province);
}