package com.yatang.sc.inventory.dubboservice.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.im.InventoryQueryDto;

/**
 * @描述:同步wms库存
 * @类名:InventorySynFlowService
 * @作者: lvheping
 * @创建时间: 2017/9/5 20:08
 * @版本: v1.0
 */

public interface InventorySynFlowService {

    /**
     * 根据返回数据做相关的业务操作
     *
     * @param imAdjuestmentParamentDto
     * @return
     */
    Response<Void> adjustmentInventoryItem(InventoryQueryDto imAdjuestmentParamentDto);
}
