package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.CommerceItem;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
public interface CommerceItemService {
    CommerceItem getCommerceItemForId(Long id);

    void save(CommerceItem commerceItem);

    List<CommerceItem> getCommerceItemForOrderId(String orderId);

    void updateCommerceItem(CommerceItem commerceItem);

    List<CommerceItem> getCommerceItemAndPriceForOrderId(String orderId);

    void updateByPrimaryKeySelective(CommerceItem commerceItem);

    void updateState(List<Long> enoughStock,List<Long> lessStock);
}
