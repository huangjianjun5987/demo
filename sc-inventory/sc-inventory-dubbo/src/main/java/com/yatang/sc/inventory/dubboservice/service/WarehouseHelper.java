package com.yatang.sc.inventory.dubboservice.service;

import com.yatang.sc.facade.dto.LogicWarehouseDto;

/**
 * Created by qiugang on 11/1/2017.
 */
public interface WarehouseHelper {

    public LogicWarehouseDto queryLogicWarehouseByArea(String state, String city);

    public LogicWarehouseDto findLogicWarehouseByCode(String logicWarehouseCode);
}
