package com.yatang.sc.inventory.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.AreawarehouseDto;

public interface AreaWarehouseQueryDubboService {

    Response<AreawarehouseDto> queryAreawarehouseByProvince(String province);

}
