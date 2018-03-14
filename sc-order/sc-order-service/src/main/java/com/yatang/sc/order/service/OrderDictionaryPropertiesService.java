package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.OrderDictionary;

/**
 * Created by liusongjie on 2017/7/10.
 */
public interface OrderDictionaryPropertiesService {

    OrderDictionary selectByPrimaryKey(String propertyName);
}
