package com.yatang.sc.inventory.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.dto.im.ImAdjustmentQueryListDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentQueryParamDto;

/**
 * @描述: 库存调整查询dubbo的Service接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午9:10
 * @版本: v1.0
 */
public interface ImAdjustmentQueryDubboService {
    /**
     * 根据传入参数分页查询采购订单
     * @param convert
     * @return
     */
    Response<PageResult<ImAdjustmentQueryListDto>> queryListImAdjustment(ImAdjustmentQueryParamDto convert);

    /**
     * 根据传入列表id查询库存调整详情
     * @param id
     * @return
     */
    Response<ImAdjustmentQueryListDto> getImAdjustment(Long id);


}
