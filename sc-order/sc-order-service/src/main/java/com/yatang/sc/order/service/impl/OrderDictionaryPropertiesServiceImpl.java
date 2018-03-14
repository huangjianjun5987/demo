package com.yatang.sc.order.service.impl;

import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.order.dao.OrderDictionaryDao;
import com.yatang.sc.order.domain.OrderDictionary;
import com.yatang.sc.order.service.OrderDictionaryPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
@Service
@Transactional
public class OrderDictionaryPropertiesServiceImpl implements OrderDictionaryPropertiesService {

    private Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderDictionaryDao orderDictionaryPropertiesDao;

    @Override
    @LocalCache(group = "orderDic", expireAfterWrite = 5, maximumSize = 100)
    public OrderDictionary selectByPrimaryKey(String propertyName) {
        return orderDictionaryPropertiesDao.selectByPrimaryKey(propertyName);
    }
}
