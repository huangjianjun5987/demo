package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;

/**
 * @描述: 订单中间层服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:28
 * @版本: v1.0
 */
public interface KiddSaleOrderService {

    /**
     * 新增销售订单
     * @param saleOrderDto
     * @return
     */
    Response<String> add(KiddSaleOrderDto saleOrderDto);

}
