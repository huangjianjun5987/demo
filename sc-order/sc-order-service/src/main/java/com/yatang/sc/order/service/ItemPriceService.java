package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.ItemPrice;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
public interface ItemPriceService {
    ItemPrice getItemPriceForId(Long id);

    void save(ItemPrice itemPrice);

    int updateByPrimaryKeySelective(ItemPrice record);
}
