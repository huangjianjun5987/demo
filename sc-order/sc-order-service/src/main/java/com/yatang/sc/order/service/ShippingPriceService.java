package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.ShippingPrice;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
public interface ShippingPriceService {
    void save(ShippingPrice shippingPrice);
    ShippingPrice selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(ShippingPrice record);
}
