package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.ReturnOrderLogPo;

/**
 * @描述:记录退货单操作日志
 * @类名:ReturnOrderLogService
 * @作者: lvheping
 * @创建时间: 2017/11/27 16:28
 * @版本: v1.0
 */

public interface ReturnOrderLogService {

    void save(ReturnOrderLogPo returnOrderLogPo);
}
