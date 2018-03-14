package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.ShippingMethod;

import java.util.List;

/**
 * 物流方式
 * Created by xiangyonghong on 2017/7/31.
 */
public interface ShippingMethodService {

    List<ShippingMethod> getAllShippingMethod();
}
