package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.ShippingPriceAdj;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
public interface ShippingPriceAdjService {
    void save(ShippingPriceAdj shippingPriceAdj);

    List<ShippingPriceAdj> selectByShippingPriceId(Long id);

}
