package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.OrderRefundPo;

/**
 * @描述:操作退款单退货单关系service
 * @类名:OrderRefundService
 * @作者: lvheping
 * @创建时间: 2017/11/14 14:40
 * @版本: v1.0
 */

public interface OrderRefundService {
    /**
     * 根据退货单号查询退款记录条数
     * @param returnOrderId
     * @return
     */
    int selectByReturnOrderId(String returnOrderId);

    /**
     * 插入退款退货单关系记录
     * @param record
     * @return
     */
    void insert(OrderRefundPo record);

    /**
     * 通过退换货ID查询退款记录ID
     * @param id
     * @return
     */
    OrderRefundPo queryByReturnId(String id);
}
