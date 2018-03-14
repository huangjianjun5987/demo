package com.yatang.sc.purchase.service.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.purchase.service.WarehouseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qiugang on 11/1/2017.
 */
@Service("warehouseHelper")
public class WarehouseHelperImpl implements WarehouseHelper {

    @Autowired
    private WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Override
    @LocalCache(group = "logicWarehouse", expireAfterWrite = 480, maximumSize = 200)
    public LogicWarehouseDto queryLogicWarehouseByArea(String province, String city) {

        Response<List<LogicWarehouseDto>>  queryResponse = warehouseLogicQueryDubboService.getWarehouseByProviceName(province);
        LogicWarehouseDto result = null;
        if(queryResponse.isSuccess() && queryResponse.getResultObject() != null){
            List<LogicWarehouseDto> logicWarehouseDtos = queryResponse.getResultObject();
            if(logicWarehouseDtos != null && logicWarehouseDtos.size() > 0){
                if(logicWarehouseDtos.size() == 1){
                    result = logicWarehouseDtos.get(0);
                } else {
                    for(LogicWarehouseDto lw : logicWarehouseDtos){
                        if(city.equals(lw.getPhysicalWarehouseDto().getCity())){
                            result = lw;
                        }
                    }
                    if(result == null){
                        result = logicWarehouseDtos.get(0);
                    }
                }
            }
        }
        return result;
    }

    @Override
    @LocalCache(group = "logicWarehouse", expireAfterWrite = 480, maximumSize = 200)
    public LogicWarehouseDto findLogicWarehouseByCode(String logicWarehouseCode){

        Response<LogicWarehouseDto> response = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(logicWarehouseCode);
        if(response != null && response.getResultObject() != null){
            return response.getResultObject();
        }
        return null;
    }
}
