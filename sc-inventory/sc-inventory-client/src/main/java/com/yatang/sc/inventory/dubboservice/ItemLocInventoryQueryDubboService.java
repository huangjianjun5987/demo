package com.yatang.sc.inventory.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.*;

import java.util.List;
import java.util.Map;


/**
 * xiangyonghong on 2017/11/15.
 */
public interface ItemLocInventoryQueryDubboService {


    /**
     * 批量库存移动加权平均成本信息
     */
    Response<List<AvcostResultDto>> queryBatchItemLocInventory(List<AvcostConditionDto> param);


    /**
     * 第三方下单库存查询
     * @param
     * @return
     */
    Response<Map<String,Long>> queryAvailableInventoryForDS(String province, String city, List<String> productCodes);

}
