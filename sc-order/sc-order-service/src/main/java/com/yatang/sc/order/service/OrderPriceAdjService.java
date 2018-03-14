package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.OrderPriceAdj;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
public interface OrderPriceAdjService {

    void save(OrderPriceAdj orderPriceAdj);

    List<OrderPriceAdj> selectByOrderPriceId(Long id);
}
