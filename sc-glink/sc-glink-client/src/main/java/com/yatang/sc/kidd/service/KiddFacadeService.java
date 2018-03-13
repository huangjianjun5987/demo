package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.kidd.dto.ProviderRequestDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderRequestDto;

import java.util.List;

public interface KiddFacadeService {

    /**
     * 发送心怡提供第三方接口服务
     *
     * @param providerDto
     * @return
     */
    Object send(ProviderRequestDto providerDto);

    /**
     * 获取仓库接口提供服务商类型
     *
     * @param warehouseCode
     * @return
     */
    Response<String> warehouseInterfaceProvider(String warehouseCode);

    /**
     * 根据接口提供服务商获取仓库编码列表
     *
     * @param interfaceProvider
     * @return
     */
    Response<List<LogicWarehouseDto>> queryLogicWarehousesBy(String interfaceProvider);

    /**
     * 用于封装SaleOrderRequestDto
     * @param saleOrderDto
     * @return
     */
    SaleOrderRequestDto setSaleOrderRequestDto(KiddSaleOrderDto saleOrderDto);

}
