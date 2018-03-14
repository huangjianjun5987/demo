package com.yatang.sc.purchase.service;

import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.states.OrderFrom;

import java.util.List;

public interface SplitOrderHelper {

    /**
     * 通过实时库存拆单
     * @param order
     * @param groups
     * @return
     * @throws Exception
     */
    String splitOrderByInventory(Order order, SplitOrderRequestDto groups) throws Exception;
    String splitOrderIgnoreInventory(Order order) throws Exception;
    String getSubOrderId(String mainOrderId, List<Order> subOrders);

    /**
     * 手动拆单
     * @param order
     * @param groups
     * @return
     * @throws Exception
     */
    List<String> manualSplitOrderByInventory(Order order, SplitOrderRequestDto groups,OrderFrom pOrderFrom) throws Exception;

    /**
     * 更加配送模式生成拆单请求
     * @param mainOrder
     * @return
     * @throws Exception
     */
    SplitOrderRequestDto genSplitOrderRequestByShippingModes(Order mainOrder) throws Exception;

    /**
     * 直配订单拆单
     * @param order
     * @param groups
     * @return
     * @throws Exception
     */
    String splitOrderByASN(Order order, SplitOrderRequestDto groups) throws Exception;

    void addProviderShippingGroup(String prodPlaceId, ShippingGroup pShippingGroup) throws Exception;
}
