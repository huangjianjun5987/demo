package com.yatang.sc.inventory.service;

import com.yatang.sc.inventory.domain.CfAreaWarehouse;

import java.util.Map;

public interface AreaWarehouseService {

    CfAreaWarehouse queryAreawarehouseByProvince(String province);
}
