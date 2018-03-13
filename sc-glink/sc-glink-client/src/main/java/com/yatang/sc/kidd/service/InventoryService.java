package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.im.ImAdjustmentResultDto;
import com.yatang.sc.kidd.dto.im.InventoryQueryDto;

public interface InventoryService {

    /**
     * 商品库存查询
     *
     * @param inventoryQueryDto
     * @return
     */
    Response<ImAdjustmentResultDto> inventoryQuery(InventoryQueryDto inventoryQueryDto);

}
