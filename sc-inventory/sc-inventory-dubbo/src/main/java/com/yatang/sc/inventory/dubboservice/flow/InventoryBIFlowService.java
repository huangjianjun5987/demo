package com.yatang.sc.inventory.dubboservice.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.dto.ItemInventoryPageQueryParamDto;
import com.yatang.sc.inventory.dto.im.InventoryBIDto;

/**
 * 库存实时查询flow 接口定义
 */
public interface InventoryBIFlowService {


    /**
     * 分页查询实时库存
     * @param pageQueryParamDto
     * @return
     */
    Response<PageResult<InventoryBIDto>> queryInventoryBIByPageQueryParam(ItemInventoryPageQueryParamDto pageQueryParamDto);
}
