package com.yatang.sc.order.service;

import java.util.List;

import com.yatang.sc.order.domain.returned.AppQueryReturnRequestItemPo;
import com.yatang.sc.order.domain.returned.ReturnRequestItemPo;

/**
 * @描述: 退换货商品项service接口定义
 * @类名: ReturnRequestItemService
 * @作者: kangdong
 * @创建时间: 2017/10/17 11:21
 * @版本: v1.0
 */
public interface ReturnRequestItemService {

    /**
     * 按returnId查询退货单商品项集合
     * @param returnId 销售退换货单表
     * @return
     */
    List<ReturnRequestItemPo> queryReturnRequestItem(String returnId);

    /**
     * 按returnId查询退货单商品项集合
     * @param returnId 销售退换货单表
     * @return
     */
    List<AppQueryReturnRequestItemPo> queryReturnRequestItemByReturnId(String returnId);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    ReturnRequestItemPo queryById(Long id);
}
